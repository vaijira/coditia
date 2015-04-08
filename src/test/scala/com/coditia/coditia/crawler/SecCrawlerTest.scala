/*
 * Tests for SEC crawler.
 *
 * Copyright (C) 2014-2015 Jorge Perez Burgos <jorge.perez*at*coditia.com>.
 *
 * This work is licensed under the terms of the Affero GNU GPL, version 3.
 * See the LICENSE file in the top-level directory.
 *
 */
package com.coditia.coditia.crawler

import com.coditia.coditia.model.{DBTestKit, TestLiftSession, CoditiaSchema}
import org.scalatest.FlatSpec
import net.liftweb.common.Loggable
import net.liftweb.squerylrecord.RecordTypeMode._
import com.coditia.coditia.model.BalanceSheetStatement
import com.coditia.coditia.model.BalanceSheetConcept

/**
 * Test for SecCrawler
 */
class SecCrawlerTest extends FlatSpec with DBTestKit with TestLiftSession with Loggable {
  val gaapNS = "http://xbrl.us/us-gaap/1.0/elts/us-gaap-2008-03-31.xsd"
  val hcpNS = "http://www.sec.gov/Archives/edgar/data/765880/000104746909001929/hcp-20081231.xsd"

  override def withFixture(test: NoArgTest) = {
    configureH2
    createDb
    inSession {
      inTransaction {
        super.withFixture(test)
      }
    }
  }

  "SEC Crawler crawling specific XBRL RSS for 10-K" should "create a new company" in {
    def checkHcpStatements2008(statements: Iterable[(BalanceSheetStatement, BalanceSheetConcept)]): Unit = {
      var statement = statements.head

      assert(statement._2.name._1 == "InvestmentBuildingAndBuildingImprovements", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 7762217000.00, "big decimal value mistmatch")

      var rest = statements.tail

      statement = rest.head

      assert(statement._2.name._1 == "DevelopmentInProcess", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 224361000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "Land", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 1551168000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "RealEstateInvestmentPropertyAccumulatedDepreciation", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 827655000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "RealEstateInvestmentPropertyNet", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 8710091000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "NetInvestmentInDirectFinancingAndSalesTypeLeases", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 648234000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "LoansAndLeasesReceivableNetReportedAmount", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 1076392000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "InvestmentsInAffiliatesSubsidiariesAssociatesAndJointVentures", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 272929000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "AccountsReceivableNet", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 34211000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "CashAndCashEquivalentsAtCarryingValue", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 57562000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "RestrictedCashAndCashEquivalentsAtCarryingValue", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 35078000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "IntangibleAssetsNetExcludingGoodwill", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 507100000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "RealEstateHeldForSaleNet", "Unexpected concept")

      assert(statement._2.namespace._1 == hcpNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 15423000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "OtherAssets", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 492806000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "Assets", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 11849826000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "LineOfCredit", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 150000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "LoansPayableToBank", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 520000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "UnsecuredDebt", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 3523513000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "SecuredDebt", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 1641734000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "OtherBorrowings", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 102209000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "OffMarketLeaseUnfavorable", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 232654000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "AccountsPayableAndAccruedLiabilities", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 211691000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "DeferredRevenue", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 60185000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "Liabilities", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 6441986000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "MinorityInterestInJointVenturesHcp", "Unexpected concept")

      assert(statement._2.namespace._1 == hcpNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 12912000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "MinorityInterestInPreferredUnitHolders", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 193657000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "MinorityInterest", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 206569000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "CommitmentsAndContingencies", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 0.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "PreferredStockValue", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 285173000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "CommonStockValue", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 253601000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "AdditionalPaidInCapital", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 4873727000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "RetainedEarningsAccumulatedDeficit", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == -130068000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "AccumulatedOtherComprehensiveIncomeLossNetOfTax", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == -81162000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "StockholdersEquity", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 5201271000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "LiabilitiesAndStockholdersEquity", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 11849826000.00, "big decimal value mistmatch")

      rest = rest.tail
    }

    def checkHcpStatements2007(statements: Iterable[(BalanceSheetStatement, BalanceSheetConcept)]): Unit = {
      var statement = statements.head

      assert(statement._2.name._1 == "InvestmentBuildingAndBuildingImprovements", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 7493944000.00, "big decimal value mistmatch")

      var rest = statements.tail

      statement = rest.head

      assert(statement._2.name._1 == "DevelopmentInProcess", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 372527000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "Land", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 1564820000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "RealEstateInvestmentPropertyAccumulatedDepreciation", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 605881000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "RealEstateInvestmentPropertyNet", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 8825410000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "NetInvestmentInDirectFinancingAndSalesTypeLeases", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 640052000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "LoansAndLeasesReceivableNetReportedAmount", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 1065485000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "InvestmentsInAffiliatesSubsidiariesAssociatesAndJointVentures", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 248894000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "AccountsReceivableNet", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 44892000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "CashAndCashEquivalentsAtCarryingValue", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 96269000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "RestrictedCashAndCashEquivalentsAtCarryingValue", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 36427000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "IntangibleAssetsNetExcludingGoodwill", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 623073000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "RealEstateHeldForSaleNet", "Unexpected concept")

      assert(statement._2.namespace._1 == hcpNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 425137000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "OtherAssets", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 516133000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "Assets", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 12521772000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "LineOfCredit", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 951700000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "LoansPayableToBank", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 1350000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "UnsecuredDebt", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 3819950000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "SecuredDebt", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 1277291000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "LiabilitiesOfAssetsHeldForSale", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 3470000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "OtherBorrowings", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 108496000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "OffMarketLeaseUnfavorable", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 278143000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "AccountsPayableAndAccruedLiabilities", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 238093000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "DeferredRevenue", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 51649000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "Liabilities", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 8078792000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "MinorityInterestInJointVenturesHcp", "Unexpected concept")

      assert(statement._2.namespace._1 == hcpNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 33436000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "MinorityInterestInPreferredUnitHolders", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 305835000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "MinorityInterest", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 339271000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "CommitmentsAndContingencies", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 0.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "PreferredStockValue", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 285173000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "CommonStockValue", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 216819000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "AdditionalPaidInCapital", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 3724739000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "RetainedEarningsAccumulatedDeficit", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == -120920000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "AccumulatedOtherComprehensiveIncomeLossNetOfTax", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == -2102000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "StockholdersEquity", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 4103709000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "LiabilitiesAndStockholdersEquity", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 12521772000.00, "big decimal value mistmatch")

      rest = rest.tail
    }

    val crawler = new SecCrawler
    //original data "http://www.sec.gov/Archives/edgar/monthly/xbrlrss-2009-02.xml"
    val url = getClass.getResource("xbrlrss-2009-02.xml")
    logger.debug("loading file ..." + url.getPath)
    crawler.parseRss(url.getPath, Filing10K)

    val companies = from(CoditiaSchema.secCompany)(c =>
          where(c.cik === 765880) select (c))

    assert(companies.size == 1, "Only one company with cik 765880 been created")

    val company = companies.head

    assert(company.cik._1  == 765880, "Company CIK should be 765880 for HCP")
    assert(company.company.name._1 == "Hcp, inc.", "Company name must be Hcp, inc.")

    val statementsHcp2008 =
      join(CoditiaSchema.balanceSheetStatement,
          CoditiaSchema.balanceSheetConcept) ((s, r) =>
        where(s.balanceSheetId  === 1 and r.isAbstract === false)
        select (s, r)
        orderBy(s.idField)
        on(s.conceptId === r.id )
      )

    checkHcpStatements2008(statementsHcp2008)

    val statementsHcp2007 =
      join(CoditiaSchema.balanceSheetStatement,
          CoditiaSchema.balanceSheetConcept) ((s, r) =>
        where(s.balanceSheetId  === 2 and r.isAbstract === false)
        select (s, r)
        orderBy(s.idField)
        on(s.conceptId === r.id )
      )

    checkHcpStatements2007(statementsHcp2007)

  }

}

