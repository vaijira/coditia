/*
 * Companies related database objects.
 *
 * Copyright (C) 2014 Jorge Perez Burgos <jorge.perez*at*coditia.com>.
 *
 * This work is licensed under the terms of the Affero GNU GPL, version 3.
 * See the LICENSE file in the top-level directory.
 *
 */
package com.coditia.coditia.model

import org.squeryl.annotations.Column
import net.liftweb.record.{MetaRecord, Record}
import net.liftweb.squerylrecord.KeyedRecord
import net.liftweb.squerylrecord.RecordTypeMode._
import net.liftweb.record.field.{StringField, LongField, IntField}

/**
 * Company information
 */
class Company extends Record[Company] with KeyedRecord[Long] {
  override def meta = Company

  @Column(name = "id")
  override val idField = new LongField(this)

  val name = new StringField(this, 255)

  lazy val annualReports = CoditiaSchema.companyToAnnualReports.left(this)
}

/**
 * Company companion object
 */
object Company extends Company with MetaRecord[Company]

/**
 * Specific information related with SEC companies
 */
class SecCompany extends Record[SecCompany] with KeyedRecord[Long] {
  override def meta = SecCompany

  @Column(name = "id")
  override val idField = new LongField(this)

  val cik = new IntField(this)

  val companyId = new LongField(this)
  lazy val company = CoditiaSchema.companyToSecCompany.right(this).head
}

/**
 * SEC Company companion object
 */
object SecCompany extends SecCompany with MetaRecord[SecCompany]