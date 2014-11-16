package com.coditia.coditia.model

import org.squeryl.Schema
import org.squeryl.annotations.Column
import net.liftweb.record.{MetaRecord, Record}
import net.liftweb.squerylrecord.KeyedRecord
import net.liftweb.squerylrecord.RecordTypeMode._
import net.liftweb.record.field.{StringField, LongField, IntField}

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

  class Company extends Record[Company] with KeyedRecord[Long] {
    override def meta = Company

    @Column(name = "id")
    override val idField = new LongField(this)

    val name = new StringField(this, 255)
  }

  object Company extends Company with MetaRecord[Company]

  class SecCompany extends Record[SecCompany] with KeyedRecord[Long] {
    override def meta = SecCompany

    @Column(name = "id")
    override val idField = new LongField(this)

    val cik = new IntField(this)

    val companyId = new LongField(this)
    lazy val company = CoditiaSchema.companyToSecCompany.right(this)
  }

  object SecCompany extends SecCompany with MetaRecord[SecCompany]
}
