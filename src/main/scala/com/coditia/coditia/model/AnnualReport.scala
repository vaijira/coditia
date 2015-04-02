/*
 * Annual report related database objects.
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
import net.liftweb.record.field.{DateTimeField, LongField, StringField}
import net.liftweb.record.field.{OptionalDecimalField, OptionalStringField}
import net.liftweb.common.Loggable
import java.math.MathContext

/**
 * Annual report information
 */
class AnnualReport extends Record[AnnualReport] with KeyedRecord[Long] {
  override def meta = AnnualReport

  @Column(name = "id")
  override val idField = new LongField(this)

  val date = new DateTimeField(this)

  val companyId = new LongField(this)

  val url = new StringField(this, 1024)

  lazy val balanceSheet = CoditiaSchema.annualReportToBalanceSheet.left(this).head
}

object AnnualReport extends AnnualReport with MetaRecord[AnnualReport] with Loggable {
  def createAnnualReport(report: AnnualReport) = {
    CoditiaSchema.annualReport.insert(report)
  }
}

/**
 * Balance sheet
 */
class BalanceSheet extends Record[BalanceSheet] with KeyedRecord[Long] {
  override def meta = BalanceSheet

  @Column(name = "id")
  override val idField = new LongField(this)

  val reportId = new LongField(this)
  lazy val statements = CoditiaSchema.balanceSheetToBalanceSheetStatement.left(this)
}

object BalanceSheet extends BalanceSheet with MetaRecord[BalanceSheet] with Loggable {
  def createBalanceSheet(sheet: BalanceSheet) = {
    CoditiaSchema.balanceSheet.insert(sheet)
  }
}

/**
 * Balance sheet statements
 */
class BalanceSheetStatement extends Record[BalanceSheetStatement] with KeyedRecord[Long] {
  override def meta = BalanceSheetStatement

  @Column(name = "id")
  override val idField = new LongField(this)

  val value = new OptionalDecimalField(this, MathContext.DECIMAL64, 2)
  val description = new OptionalStringField(this, 1024)
  val conceptId = new LongField(this)
  val balanceSheetId = new LongField(this)

  def concept = CoditiaSchema.balanceSheetConcept.lookup(conceptId._1)
}

object BalanceSheetStatement extends BalanceSheetStatement with MetaRecord[BalanceSheetStatement]
  with Loggable
{
  def createBalanceSheetStatement(statement: BalanceSheetStatement) = {
    CoditiaSchema.balanceSheetStatement.insert(statement)
  }
}

