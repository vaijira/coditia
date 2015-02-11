/*
 * Schema relationships.
 *
 * Copyright (C) 2014 Jorge Perez Burgos <jorge.perez*at*coditia.com>.
 *
 * This work is licensed under the terms of the Affero GNU GPL, version 3.
 * See the LICENSE file in the top-level directory.
 *
 */
package com.coditia.coditia.model

import org.squeryl.Schema
import net.liftweb.squerylrecord.RecordTypeMode._

/**
 * Application DB schema
 */
object CoditiaSchema extends Schema {

  val company = table[Company]
  val secCompany = table[SecCompany]
  val balanceSheetConcept = table[BalanceSheetConcept]
  val annualReport = table[AnnualReport]
  val balanceSheet = table[BalanceSheet]
  val balanceSheetStatement = table[BalanceSheetStatement]

  val companyToSecCompany = oneToManyRelation(company, secCompany).
    via((c, sec) => c.id === sec.companyId)

  on(secCompany) { sec =>
    declare(sec.companyId defineAs indexed("company_idx"))
    declare(sec.companyId is unique)
    declare(sec.cik defineAs indexed("cik_idx"))
  }

  val parentConceptToChildren = oneToManyRelation(balanceSheetConcept, balanceSheetConcept).
    via((parent, child) => parent.id === child.parentId)

  val companyToAnnualReports = oneToManyRelation(company, annualReport).
    via((c, r) => c.id === r.companyId)

  on(annualReport) { r =>
    declare(r.companyId defineAs indexed("company_idx"))
  }

  val annualReportToBalanceSheet = oneToManyRelation(annualReport, balanceSheet).
    via((r, b) => r.id === b.reportId )

  on(balanceSheet) { b =>
    declare(b.reportId defineAs indexed("annualReport_idx"))
  }

  val balanceSheetToBalanceSheetStatement = oneToManyRelation(balanceSheet, balanceSheetStatement).
    via((b, s) => b.id === s.balanceSheetId )

  on(balanceSheetStatement) { s =>
    declare(s.balanceSheetId defineAs indexed("balanceSheet_idx"))
  }
}
