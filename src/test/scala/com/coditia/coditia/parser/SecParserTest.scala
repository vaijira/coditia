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
import com.coditia.coditia.model.{Company, BalanceSheetStatement}
import net.liftweb.common.Loggable
import net.liftweb.squerylrecord.RecordTypeMode._


/**
 * Test for SecCrawler
 */
class SecParserTest extends FlatSpec with DBTestKit with TestLiftSession with Loggable {
  val gaapNS = "http://taxonomies.xbrl.us/us-gaap/2009/elts/us-gaap-2009-01-31.xsd"
  val cmeNS = "http://www.sec.gov/Archives/edgar/data/1156375/000119312510043180/cme-20091231.xsd"

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
    def checkCmeStatements2009(statements: Iterable[(BalanceSheetStatement, BalanceSheetConcept)]): Unit = {

      var statement = statements.head

      assert(statement._2.name._1 == "CashAndCashEquivalentsAtCarryingValue", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 260600000.00, "big decimal value mistmatch")

      var rest = statements.tail

      statement = rest.head

      assert(statement._2.name._1 == "SecuritiesReceivedAsCollateral", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 0.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "MarketableSecuritiesCurrent", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 42600000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "AccountsReceivableNetCurrent", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 248300000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "OtherAssetsCurrent", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 165600000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "GoodFaithAndMarginDepositsWithBrokerDealers", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 5981900000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "AssetsCurrent", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 6699000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "PropertyPlantAndEquipmentNet", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 738500000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "OtherIndefiniteLivedIntangibleAssets", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 16982000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "IntangibleAssetsNetExcludingGoodwill", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 3246500000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "Goodwill", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 7549200000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "OtherAssetsNoncurrent", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 435800000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "Assets", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 35651000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "AccountsPayableCurrent", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 46700000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "ObligationToReturnSecuritiesReceivedAsCollateral", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 0.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "ShortTermNonBankLoansAndNotesPayable", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 299800000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "OtherLiabilitiesCurrent", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 195200000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "CashPerformanceBondsAndSecurityDepositLiability", "Unexpected concept")

      assert(statement._2.namespace._1 == cmeNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 5981900000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "LiabilitiesCurrent", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 6523600000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "UnsecuredLongTermDebt", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 2014700000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "DeferredTaxLiabilitiesNoncurrent", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 7645900000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "OtherLiabilitiesNoncurrent", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 165800000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "Liabilities", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 16350000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "AdditionalPaidInCapitalCommonStock", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 17186600000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "RetainedEarningsAccumulatedDeficit", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 2239900000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "AccumulatedOtherComprehensiveIncomeLossNetOfTax", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == -126200000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "StockholdersEquity", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 19301000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "PreferredStockValue", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 0.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "CommonStockValueOutstanding", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 700000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "LiabilitiesAndStockholdersEquity", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 35651000000.00, "big decimal value mistmatch")

      rest = rest.tail

      assert(rest.isEmpty)
    }

    def checkCmeStatements2008(statements: Iterable[(BalanceSheetStatement, BalanceSheetConcept)]): Unit = {

      var statement = statements.head

      assert(statement._2.name._1 == "CashAndCashEquivalentsAtCarryingValue", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 297900000.00, "big decimal value mistmatch")

      var rest = statements.tail

      statement = rest.head

      assert(statement._2.name._1 == "SecuritiesReceivedAsCollateral", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 426900000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "MarketableSecuritiesCurrent", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 310100000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "AccountsReceivableNetCurrent", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 234000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "OtherAssetsCurrent", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 189100000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "GoodFaithAndMarginDepositsWithBrokerDealers", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 17653500000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "AssetsCurrent", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 19111500000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "PropertyPlantAndEquipmentNet", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 707200000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "OtherIndefiniteLivedIntangibleAssets", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 16982000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "IntangibleAssetsNetExcludingGoodwill", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 3369400000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "Goodwill", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 7519200000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "OtherAssetsNoncurrent", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 469400000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "Assets", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 48158700000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "AccountsPayableCurrent", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 71000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "ObligationToReturnSecuritiesReceivedAsCollateral", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 456800000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "ShortTermNonBankLoansAndNotesPayable", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 249900000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "OtherLiabilitiesCurrent", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 211800000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "CashPerformanceBondsAndSecurityDepositLiability", "Unexpected concept")

      assert(statement._2.namespace._1 == cmeNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 17653500000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "LiabilitiesCurrent", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 18643000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "UnsecuredLongTermDebt", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 2966100000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "DeferredTaxLiabilitiesNoncurrent", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 7728300000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "OtherLiabilitiesNoncurrent", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 132700000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "Liabilities", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 29470100000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "AdditionalPaidInCapitalCommonStock", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 17128500000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "RetainedEarningsAccumulatedDeficit", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 1719700000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "AccumulatedOtherComprehensiveIncomeLossNetOfTax", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == -160300000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "StockholdersEquity", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 18688600000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "PreferredStockValue", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 0.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "CommonStockValueOutstanding", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 700000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "LiabilitiesAndStockholdersEquity", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 48158700000.00, "big decimal value mistmatch")

      rest = rest.tail

      assert(rest.isEmpty)
    }

    val company = Company.createRecord.name("Cme")
    CoditiaSchema.company.insert(company)

    val secCompany = SecCompany.createRecord.cik(123456).companyId(company.id)
    CoditiaSchema.secCompany.insert(secCompany)

    // 10-k parsed http://www.sec.gov/Archives/edgar/data/1156375/000119312510043180/d10k.htm
    val doc = "http://www.sec.gov/Archives/edgar/data/1156375/000119312510043180/cme-20091231.xml"
    val parser = new Sec10KParser(secCompany, doc, "")

    parser.parse

    val assetConcepts = from(CoditiaSchema.balanceSheetConcept )(c =>
      net.liftweb.squerylrecord.RecordTypeMode.where
      (c.kind === BalanceSheetConceptEnum.Asset and c.parentId.isNull)
      select (c))

    assert(assetConcepts.size == 1, "Assets Abstract must be the only parent")

    val assetConcept = assetConcepts.head

    assert(assetConcept.name._1  == "AssetsAbstract", "The first asset must be AssetsAbstract")
    assert(assetConcept.namespace._1 == gaapNS, "Namespace must be a gaap schema reference")
    assert(assetConcept.isAbstract._1, "Concept should be abstract")

    val liabilityConcepts = from(CoditiaSchema.balanceSheetConcept )(c =>
      net.liftweb.squerylrecord.RecordTypeMode.where
      (c.kind === BalanceSheetConceptEnum.Liability and c.parentId.isNull)
      select (c))

    assert(liabilityConcepts.size == 1, "Liability And Equity Abstract must be the only parent")

    val liabilityConcept = liabilityConcepts.head

    assert(liabilityConcept.name._1  == "LiabilitiesAndStockholdersEquityAbstract",
        "The first liability must be LiabilitiesAndStockholdersEquityAbstract")
    assert(liabilityConcept.namespace._1 == gaapNS, "Namespace must be a gaap schema reference")
    assert(liabilityConcept.isAbstract._1, "Concept should be abstract")

    val statementsCme2009 =
      join(CoditiaSchema.balanceSheetStatement,
          CoditiaSchema.balanceSheetConcept) ((s, r) =>
        where(s.balanceSheetId  === 2 and r.isAbstract === false)
        select (s, r)
        orderBy(s.idField)
        on(s.conceptId === r.id )
      )

    checkCmeStatements2009(statementsCme2009)

    val statementsCme2008 =
      join(CoditiaSchema.balanceSheetStatement,
          CoditiaSchema.balanceSheetConcept) ((s, r) =>
        where(s.balanceSheetId  === 1 and r.isAbstract === false)
        select (s, r)
        orderBy(s.idField)
        on(s.conceptId === r.id )
      )

    checkCmeStatements2008(statementsCme2008)
  }

}

