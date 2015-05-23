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
  val cvxNS = "http://www.sec.gov/Archives/edgar/data/93410/000089161809000054/cvx-20081231.xsd"

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

      assert(rest.isEmpty)
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

      assert(rest.isEmpty)
    }

    def checkCvxStatements2007(statements: Iterable[(BalanceSheetStatement, BalanceSheetConcept)]): Unit = {
      var statement = statements.head

      assert(statement._2.name._1 == "CashAndCashEquivalentsAtCarryingValue", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 7362000000.00, "big decimal value mistmatch")

      var rest = statements.tail

      statement = rest.head

      assert(statement._2.name._1 == "MarketableSecuritiesCurrent", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 732000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "AccountsNotesAndLoansReceivableNetCurrent", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 22446000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "AllowanceForNotesAndLoansReceivableCurrent", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 165000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "InventoryFinishedGoods", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 4003000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "InventoryChemicals", "Unexpected concept")

      assert(statement._2.namespace._1 == cvxNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 290000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "OtherInventorySupplies", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 1017000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "InventoryNet", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 5310000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "PrepaidExpenseCurrent", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 3527000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "AssetsCurrent", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 39377000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "AccountsReceivableNetNoncurrent", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 2194000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "InvestmentsInAffiliatesSubsidiariesAssociatesAndJointVentures", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 20477000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "PropertyPlantAndEquipmentGross", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 154084000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "AccumulatedDepreciationDepletionAndAmortizationPropertyPlantAndEquipment", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 75474000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "PropertyPlantAndEquipmentNet", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 78610000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "OtherAssetsNoncurrent", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 3491000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "Goodwill", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 4637000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "AssetsHeldForSaleLongLived", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 0.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "Assets", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 148786000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "ShortTermBorrowings", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 1162000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "AccountsPayable", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 21756000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "AccruedLiabilities", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 5275000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "AccruedIncomeTaxesPayable", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 3972000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "AccrualForTaxesOtherThanIncomeTaxes", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 1633000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "LiabilitiesCurrent", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 33798000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "LongTermDebtNoncurrent", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 5664000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "CapitalLeaseObligationsNoncurrent", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 406000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "PensionAndOtherPostretirementDefinedBenefitPlansNoncurrentLiabilities", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 4449000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "DeferredRevenueAndCreditsNoncurrent", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 15007000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "DeferredTaxLiabilitiesNoncurrent", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 12170000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "Liabilities", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 71698000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "MinorityInterest", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 204000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "PreferredStockValue", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 0.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "CommonStockValue", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 1832000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "LiabilitiesAndStockholdersEquity", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 148786000000.00, "big decimal value mistmatch")

      rest = rest.tail

      assert(rest.isEmpty)
    }

    def checkCvxStatements2008(statements: Iterable[(BalanceSheetStatement, BalanceSheetConcept)]): Unit = {
      var statement = statements.head

      assert(statement._2.name._1 == "CashAndCashEquivalentsAtCarryingValue", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 9347000000.00, "big decimal value mistmatch")

      var rest = statements.tail

      statement = rest.head

      assert(statement._2.name._1 == "MarketableSecuritiesCurrent", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 213000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "AccountsNotesAndLoansReceivableNetCurrent", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 15856000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "AllowanceForNotesAndLoansReceivableCurrent", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 246000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "InventoryFinishedGoods", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 5175000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "InventoryChemicals", "Unexpected concept")

      assert(statement._2.namespace._1 == cvxNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 459000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "OtherInventorySupplies", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 1220000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "InventoryNet", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 6854000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "PrepaidExpenseCurrent", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 4200000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "AssetsCurrent", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 36470000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "AccountsReceivableNetNoncurrent", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 2413000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "InvestmentsInAffiliatesSubsidiariesAssociatesAndJointVentures", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 20920000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "PropertyPlantAndEquipmentGross", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 173299000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "AccumulatedDepreciationDepletionAndAmortizationPropertyPlantAndEquipment", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 81519000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "PropertyPlantAndEquipmentNet", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 91780000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "OtherAssetsNoncurrent", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 4711000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "Goodwill", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 4619000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "AssetsHeldForSaleLongLived", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 252000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "Assets", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 161165000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "ShortTermBorrowings", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 2818000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "AccountsPayable", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 16580000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "AccruedLiabilities", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 8077000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "AccruedIncomeTaxesPayable", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 3079000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "AccrualForTaxesOtherThanIncomeTaxes", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 1469000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "LiabilitiesCurrent", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 32023000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "LongTermDebtNoncurrent", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 5742000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "CapitalLeaseObligationsNoncurrent", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 341000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "PensionAndOtherPostretirementDefinedBenefitPlansNoncurrentLiabilities", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 6725000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "DeferredRevenueAndCreditsNoncurrent", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 17678000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "DeferredTaxLiabilitiesNoncurrent", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 11539000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "Liabilities", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 74517000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "MinorityInterest", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 469000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "PreferredStockValue", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 0.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "CommonStockValue", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 1832000000.00, "big decimal value mistmatch")

      rest = rest.tail

      statement = rest.head

      assert(statement._2.name._1 == "LiabilitiesAndStockholdersEquity", "Unexpected concept")

      assert(statement._2.namespace._1 == gaapNS, "Concept must belong to gaap namespace")

      assert(statement._1.value._1 == 161165000000.00, "big decimal value mistmatch")

      rest = rest.tail

      assert(rest.isEmpty)
    }

    def checkWhateverStatements2008(statements: Iterable[(BalanceSheetStatement, BalanceSheetConcept)]): Unit = {
      statements.foreach(st => print("name: " + st._2.name._1 + " value: " + st._1.value._1))
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

    val statementsCvx2007 =
      join(CoditiaSchema.balanceSheetStatement,
          CoditiaSchema.balanceSheetConcept) ((s, r) =>
        where(s.balanceSheetId  === 3 and r.isAbstract === false)
        select (s, r)
        orderBy(s.idField)
        on(s.conceptId === r.id )
      )

    checkCvxStatements2007(statementsCvx2007)

    val statementsCvx2008 =
      join(CoditiaSchema.balanceSheetStatement,
          CoditiaSchema.balanceSheetConcept) ((s, r) =>
        where(s.balanceSheetId  === 4 and r.isAbstract === false)
        select (s, r)
        orderBy(s.idField)
        on(s.conceptId === r.id )
      )

    checkCvxStatements2008(statementsCvx2008)


  }

}

