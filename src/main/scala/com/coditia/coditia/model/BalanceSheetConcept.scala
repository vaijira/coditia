/*
 * GAAP concepts related database objects.
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
import net.liftweb.record.field.{StringField, LongField, TextareaField, EnumField, OptionalLongField, BooleanField}
import net.liftweb.common.Loggable

object BalanceSheetConceptEnum extends Enumeration {
  type BalanceSheetConceptEnum = Value
  val Asset, Liability, Equity = Value
}

/**
 * Concept information
 */
class BalanceSheetConcept extends Record[BalanceSheetConcept] with KeyedRecord[Long] {
  override def meta = BalanceSheetConcept

  @Column(name = "id")
  override val idField = new LongField(this)

  val name = new StringField(this, 255)

  val label = new StringField(this, 255)

  val docLabel = new TextareaField(this, 4096)

  val namespace = new StringField(this, 255)

  val kind = new EnumField(this, BalanceSheetConceptEnum)

  val isAbstract = new BooleanField(this)

  val parentId = new OptionalLongField(this)
  lazy val children = CoditiaSchema.parentConceptToChildren.left(this)

  override def toString = {
    "\nid: " + idField._1 +
    "\nnamespace: " + namespace._1 +
    "\nname: " + name._1 +
    "\nlabel: " + label._1 +
    "\ndocLabel: " + docLabel._1 +
    "\nkind: " + kind._1 +
    "\nisAbstract: " + isAbstract._1 +
    "\nparent id: " + parentId

  }
}

/**
 * Concept companion object
 */
object BalanceSheetConcept extends BalanceSheetConcept with MetaRecord[BalanceSheetConcept] with Loggable {
  def findConcept(name: String, ns: String) = from(CoditiaSchema.balanceSheetConcept)(c =>
          where(c.name === name and c.namespace === ns) select (c))

  def createConcept(concept: BalanceSheetConcept) = {
    CoditiaSchema.balanceSheetConcept.insert(concept)
  }

}