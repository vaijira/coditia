/*
 * Tests for SEC parser.
 *
 * Copyright (C) 2014 Jorge Perez Burgos <jorge.perez*at*coditia.com>.
 *
 * This work is licensed under the terms of the Affero GNU GPL, version 3.
 * See the LICENSE file in the top-level directory.
 *
 */
package com.coditia.coditia.parser

import com.coditia.coditia.model.{DBTestKit, TestLiftSession, CoditiaSchema}
import com.coditia.coditia.model.{BalanceSheetConcept, BalanceSheetConceptEnum}
import org.scalatest.FlatSpec
import net.liftweb.common.Loggable
import net.liftweb.squerylrecord.RecordTypeMode._

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
    val parser = new Sec10KParser("http://www.sec.gov/Archives/edgar/data/1156375/000119312510043180/cme-20091231.xml")

    parser.parseBalanceSheet

    val assetConcepts = from(CoditiaSchema.balanceSheetConcept )(c =>
      where(c.kind === BalanceSheetConceptEnum.Asset and c.parentId.isNull)
      select (c))

    assert(assetConcepts.size == 1, "Assets Abstract must be the only parent")

    val assetConcept = assetConcepts.head

    assert(assetConcept.name._1  == "AssetsAbstract", "The first asset must be AssetsAbstract")
    assert(assetConcept.isAbstract._1, "Concept should be abstract")

    val liabilityConcepts = from(CoditiaSchema.balanceSheetConcept )(c =>
      where(c.kind === BalanceSheetConceptEnum.Liability and c.parentId.isNull)
      select (c))

    assert(liabilityConcepts.size == 1, "Liability And Equity Abstract must be the only parent")

    val liabilityConcept = liabilityConcepts.head

    assert(liabilityConcept.name._1  == "LiabilitiesAndStockholdersEquityAbstract",
        "The first liability must be LiabilitiesAndStockholdersEquityAbstract")
    assert(liabilityConcept.isAbstract._1, "Concept should be abstract")
  }

}

