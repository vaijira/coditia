package com.coditia.coditia.model

import org.squeryl.Schema
import net.liftweb.squerylrecord.RecordTypeMode._

/**
 * Application DB schema
 */
object CoditiaSchema extends Schema {

  val company = table[Company]
  val secCompany = table[SecCompany]

  val companyToSecCompany = oneToManyRelation(company, secCompany).
    via((c,sec) => c.id === sec.companyId)

  on(secCompany) { sec =>
    declare(sec.companyId defineAs indexed("company_idx"))
    declare(sec.companyId is unique)
    declare(sec.cik defineAs indexed("cik_idx"))
  }

}
