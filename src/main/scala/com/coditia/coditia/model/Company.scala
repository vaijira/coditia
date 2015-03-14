/*
 * Companies related database objects.
 *
 * Copyright (C) 2014-2015 Jorge Perez Burgos <jorge.perez*at*coditia.com>.
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
import net.liftweb.json.JsonAST._
import scala.language.implicitConversions
import net.liftweb.common.Loggable

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
object Company extends Company with MetaRecord[Company] with Loggable {
  def findAll = from(CoditiaSchema.company )(c => select (c)).toSeq

  var cachedCompanies: Seq[Company] = Seq.empty

  def getCompanies: Seq[Company] = {
    if (cachedCompanies.isEmpty) {
      cachedCompanies = findAll
    }

    cachedCompanies.foreach(c => {c.idField._1; c.name._1})

    cachedCompanies
  }
}

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
object SecCompany extends SecCompany with MetaRecord[SecCompany] with Loggable {
  private implicit val formats =
    net.liftweb.json.DefaultFormats

  /**
   * Convert the Company to JSON format.  This is
   * implicit and in the companion object, so
   * an Company can be returned easily from a JSON call
   */
  implicit def toJson(company: SecCompany): JValue =
    company.asJValue

  implicit def toJson(company: Option[SecCompany]): JValue = {
    company match  {
      case Some(c) => toJson(c)
      case None => JString(null)
    }
  }
      /**
   * Convert a Seq[Company] to JSON format.  This is
   * implicit and in the companion object, so
   * an Company can be returned easily from a JSON call
   */
  implicit def toJson(companies: Seq[SecCompany]): JValue =
    JArray(companies.map{ _.asJValue}.toList)

  def findAll = from(CoditiaSchema.secCompany )(c => select (c)).toSeq

  def find(id: Long) = {
    logger.debug("Trying to find company with id: " + id)
    from(CoditiaSchema.secCompany )(c => where(c.id === id) select(c)).headOption
  }
}
