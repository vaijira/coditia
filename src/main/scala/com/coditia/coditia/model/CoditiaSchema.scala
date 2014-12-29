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

  val companyToSecCompany = oneToManyRelation(company, secCompany).
    via((c, sec) => c.id === sec.companyId)

  on(secCompany) { sec =>
    declare(sec.companyId defineAs indexed("company_idx"))
    declare(sec.companyId is unique)
    declare(sec.cik defineAs indexed("cik_idx"))
  }

  val parentConceptToChildren = oneToManyRelation(balanceSheetConcept, balanceSheetConcept).
    via((parent, child) => parent.id === child.parentId)

}
