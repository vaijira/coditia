/*
 * Tests for SEC parser.
 *
 * Copyright (C) 2014-2015 Jorge Perez Burgos <jorge.perez*at*coditia.com>.
 *
 * This work is licensed under the terms of the Affero GNU GPL, version 3.
 * See the LICENSE file in the top-level directory.
 *
 */
package com.coditia.coditia.parser

import org.scalatest.FlatSpec
import com.coditia.coditia.model.{DBTestKit, TestLiftSession, CoditiaSchema}
import com.coditia.coditia.model.{BalanceSheetConcept, BalanceSheetConceptEnum, SecCompany}
import net.liftweb.common.Loggable
import net.liftweb.squerylrecord.RecordTypeMode._
import com.coditia.coditia.model.Company

/**
 * Test for SecCrawler
 */
class SecParserTest extends FlatSpec with DBTestKit with TestLiftSession with Loggable {
  override def withFixture(test: NoArgTest) = {
    configureH2
    createDb
    inSession {
      inTransaction {
        super.withFixture(test)
      }
    }
  }

  "SEC parser treating 10-K file" should "create new concepts" in {
    val company = Company.createRecord.name("Cme")
    CoditiaSchema.company.insert(company)

    val secCompany = SecCompany.createRecord.cik(123456).companyId(company.id)
    CoditiaSchema.secCompany.insert(secCompany)

    val doc = "http://www.sec.gov/Archives/edgar/data/1156375/000119312510043180/cme-20091231.xml"
    val parser = new Sec10KParser(secCompany, doc)

    parser.parseBalanceSheet

    val assetConcepts = from(CoditiaSchema.balanceSheetConcept )(c =>
      net.liftweb.squerylrecord.RecordTypeMode.where
      (c.kind === BalanceSheetConceptEnum.Asset and c.parentId.isNull)
      select (c))

    assert(assetConcepts.size == 1, "Assets Abstract must be the only parent")

    val assetConcept = assetConcepts.head

    assert(assetConcept.name._1  == "AssetsAbstract", "The first asset must be AssetsAbstract")
    assert(assetConcept.namespace._1 == "http://taxonomies.xbrl.us/us-gaap/2009/elts/us-gaap-2009-01-31.xsd",
        "Namespace must be a gaap schema reference")
    assert(assetConcept.isAbstract._1, "Concept should be abstract")

    val liabilityConcepts = from(CoditiaSchema.balanceSheetConcept )(c =>
      net.liftweb.squerylrecord.RecordTypeMode.where
      (c.kind === BalanceSheetConceptEnum.Liability and c.parentId.isNull)
      select (c))

    assert(liabilityConcepts.size == 1, "Liability And Equity Abstract must be the only parent")

    val liabilityConcept = liabilityConcepts.head

    assert(liabilityConcept.name._1  == "LiabilitiesAndStockholdersEquityAbstract",
        "The first liability must be LiabilitiesAndStockholdersEquityAbstract")
    assert(liabilityConcept.namespace._1 == "http://taxonomies.xbrl.us/us-gaap/2009/elts/us-gaap-2009-01-31.xsd",
        "Namespace must be a gaap schema reference")
    assert(liabilityConcept.isAbstract._1, "Concept should be abstract")
  }

}

