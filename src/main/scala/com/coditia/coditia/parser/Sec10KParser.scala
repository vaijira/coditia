/*
 * Populates SEC Concepts in database.
 *
 * Copyright (C) 2014-2015 Jorge Perez Burgos <jorge.perez*at*coditia.com>.
 *
 * This work is licensed under the terms of the Affero GNU GPL, version 3.
 * See the LICENSE file in the top-level directory.
 *
 */
package com.coditia.coditia.parser

import com.coditia.coditia.model._
import scala.collection.JavaConversions._
import net.liftweb.common.{Box, Empty, Full, Loggable}
import net.liftweb.util.Helpers.secureXML
import com.google.common.collect.{ArrayListMultimap, ListMultimap}
import org.xbrlapi.{Arc, Concept, Fact, Relationship, Item, SimpleNumericItem}
import org.xbrlapi.networks.{Network, Networks}
import org.xbrlapi.utilities.Constants
import org.xbrlapi.aspects.{AspectValue, Aspect, PeriodAspect, FactSet, FactSetImpl}
import org.xbrlapi.xdt.aspects.{DimensionalAspectModel, ExplicitDimensionAspectValue}
import org.xbrlapi.xdt.aspects.DimensionalAspectModelWithStoreCachingLabellers
import org.joda.time.DateTime
import java.util.Locale


class Sec10KParser(company: SecCompany, docUri: String, url: String) extends Loggable {
  private[this] val repository = new XbrlRepository
  private[this] val instance = repository.getInstance(docUri)
  private[this] val schema = repository.getSchema(instance)

  // Depending in which year we are to look for one namespace or other for gaap norms
  private[this] val gaapSchema = (for (l <- schema.getImports
      if (l.getAttribute("namespace").startsWith("http://fasb.org/us-gaap/") ||
          l.getAttribute("namespace").startsWith("http://xbrl.us/us-gaap")))
        yield l).head

  private val gaapNamespace = gaapSchema.getAttribute("namespace")

  private[this] val docMap = Map("http://xbrl.us/us-gaap/2009-01-31" ->
                         secureXML.load(getClass.getResource("us-gaap-doc-2009-01-31.xml").getPath()),
                                 "http://xbrl.us/us-gaap/2008-03-31" ->
                         secureXML.load(getClass.getResource("us-gaap-doc-2008-03-31.xml").getPath())
                         )

  private[this] val assetFactsTmp = instance.getFacts(gaapNamespace, "Assets")

  private[this] val assetFacts = if (assetFactsTmp.isEmpty())
        instance.getFacts("http://xbrl.us/us-gaap/2008-03-31", "Assets").
          sortWith((f1, f2) => {
            f1.asInstanceOf[Item].getContext().getPeriod().getInstant() >
            f1.asInstanceOf[Item].getContext().getPeriod().getInstant()
          })
      else
        assetFactsTmp.sortWith((f1, f2) => {
            f1.asInstanceOf[Item].getContext().getPeriod().getInstant() >
            f1.asInstanceOf[Item].getContext().getPeriod().getInstant()
          })

  private[this] var networks: Networks = null

  private[this] var reportInstant: String = null
  private[this] var period: java.util.Calendar = null

  private[this] var report: AnnualReport = null

  private[this] var balanceSheet: BalanceSheet = null

  trait BalanceSheetState

  case object AssetState              extends BalanceSheetState
  case object LiabilityAndEquityState extends BalanceSheetState
  case object UndefinedState          extends BalanceSheetState

  private def getDocFromConcept(concept: Concept) = {
    docMap.get(concept.getTargetNamespace) match {
      case Some(docElem) =>
        (docElem \\ "label" ).
          find(elem => elem.attributes.toString.contains("label=\"lab_" + concept.getName + "\""))
            match {
              case Some(elem) => elem.child.text
              case None => ""
            }
      case None          => ""
    }
  }

  private def createConcept(concept: Concept, ns: String, state: BalanceSheetState, parent: Long) = {
    logger.debug("Creating concept: " + concept.getName)
    val label = concept.getLabelsWithResourceRole(Constants.StandardLabelRole).get(0).getStringValue().trim()

    val doc = getDocFromConcept(concept)
    logger.debug("doc for concept: "+ doc)

    val parentId = if (parent == 0) None else Some(parent)

    val assetKind = if (state == AssetState) BalanceSheetConceptEnum.Asset
    else if (concept.getName.toLowerCase.contains("equity") &&
        concept.getName != "LiabilitiesAndStockholdersEquityAbstract" )
      BalanceSheetConceptEnum.Equity
    else BalanceSheetConceptEnum.Liability

    val balanceConcept = BalanceSheetConcept.createRecord.
      name(concept.getName).
      namespace(ns).
      label(label).
      docLabel(doc).
      kind(assetKind).
      parentId(parentId).
      isAbstract(concept.isAbstract)
    BalanceSheetConcept.createConcept(balanceConcept)
  }

  private def createBalanceSheetStatement(concept: Concept, bsConcept: BalanceSheetConcept) = {
    val items = for {
      fact <- concept.getFacts
      f = fact.asInstanceOf[Item]
      if (f.getContext().getPeriod().getInstant() == reportInstant)
    } yield f

    if (!items.isEmpty || concept.isAbstract()) {
      logger.debug("Creating balance sheet statement")

      val values = for {
        item <- items
        if (item.isNumeric())
      } yield item.asInstanceOf[SimpleNumericItem].getValue()

      val value: Box[BigDecimal] = if (!values.isEmpty) {
        try {
          Full(BigDecimal(values.head))
        } catch {
          case e:
            Throwable => logger.error("wrong decimal setting it to 0: " + values.head)
            Full(BigDecimal(0))
        }
      } else {
        Empty
      }

      val label = concept.getLabelsWithResourceRole(Constants.StandardLabelRole).get(0).getStringValue().trim()

      val statement = BalanceSheetStatement.createRecord.
        conceptId(bsConcept.idField._1).
        balanceSheetId(balanceSheet.idField._1).
        description(label).
        value(value)

      BalanceSheetStatement.createBalanceSheetStatement(statement)
    }
  }

  private def recurseRelathionship(relationships: java.util.SortedSet[Relationship],
      network: Network, state: BalanceSheetState, parent: Long)
  {
    for (relationship <- relationships) {

      val concept = relationship.getTarget[Concept]

      val newstate = if (concept.getName == "AssetsAbstract") {
                       AssetState
                     } else if (concept.getName == "LiabilitiesAndStockholdersEquityAbstract") {
                       LiabilityAndEquityState
                     } else
                       state

      if (newstate != UndefinedState) {
        logger.debug("Trying to find concept: " + concept.getName + " with ns: " + relationship.getTargetURI)
        val conceptQuery = BalanceSheetConcept.findConcept(concept.getName, relationship.getTargetURI.toString)

        val bsConcept = if (conceptQuery.isEmpty) {
          createConcept(concept, relationship.getTargetURI.toString, newstate, parent)
        } else {
          conceptQuery.head
        }

        createBalanceSheetStatement(concept, bsConcept)

        recurseRelathionship(network.getActiveRelationshipsFrom(concept.getIndex),
                network, newstate, bsConcept.idField._1)
      } else {
        logger.debug("Recursing undefined concept: " + concept.getName)
        recurseRelathionship(network.getActiveRelationshipsFrom(concept.getIndex), network, newstate, parent)
      }
    }
  }

  def parse() = {

    for (fact <- assetFacts) {
      networks = repository.store.getMinimalNetworksWithArcrole(
        fact.getConcept, Constants.PresentationArcrole)

      reportInstant = fact.asInstanceOf[Item].getContext().getPeriod().getInstant()
      period = new DateTime(reportInstant).toCalendar(Locale.getDefault)

      report = AnnualReport.createAnnualReport(
          AnnualReport.createRecord.
          companyId(company.idField._1).
          date(period).
          url(url)
        )

      balanceSheet = BalanceSheet.createBalanceSheet(
          BalanceSheet.createRecord.reportId(report.idField._1)
        )

      parseBalanceSheet
    }

    repository.store.delete
  }

  private def parseBalanceSheet() {
    logger.debug("starting to parse balancesheet")

    for { network <- networks }{ logger.debug("linkrole: " + network.getLinkRole) }

    val netOption =
      if (networks.find( n => n.getLinkRole().endsWith("ConsolidatedBalanceSheets")).isEmpty) {
        if (networks.find( n => n.getLinkRole().endsWith("StatementOfFinancialPositionClassified")).isEmpty) {
          networks.find( n => n.getLinkRole().endsWith("BalanceSheets")).headOption
        } else {
          networks.find( n => n.getLinkRole().endsWith("StatementOfFinancialPositionClassified")).headOption
        }
      }
      else
        networks.find( n => n.getLinkRole().endsWith("ConsolidatedBalanceSheets")).headOption

   if (!netOption.isEmpty) {
    val network = netOption.get

    network.complete
    repository.storer.storeRelationships(network)

    for (i <- network.getRootFragmentIndices()) {
      val relationships = network.getActiveRelationshipsFrom(i)

      logger.debug("Start to recurse relationships, number of children: " + relationships.size())

      recurseRelathionship(relationships, network, UndefinedState, 0)
    }
   } else {
     logger.error("Couldn't find a valid network to parse balance sheet")
   }
  }
}

