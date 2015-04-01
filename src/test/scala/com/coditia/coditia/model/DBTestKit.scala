/*
 * DB auxiliary class for tests.
 *
 * Copyright (C) 2014-2015 Jorge Perez Burgos <jorge.perez*at*coditia.com>.
 *
 * This work is licensed under the terms of the Affero GNU GPL, version 3.
 * See the LICENSE file in the top-level directory.
 *
 */
package com.coditia.coditia.model

import java.sql.DriverManager

import org.squeryl.Session
import org.squeryl.adapters.H2Adapter

import net.liftweb.util.StringHelpers
import net.liftweb.common._
import net.liftweb.http.{S, Req, LiftSession }
import net.liftweb.squerylrecord.SquerylRecord
import net.liftweb.squerylrecord.RecordTypeMode._

/**
 * Provides a lift session for tests
 */
trait TestLiftSession {
  def session = new LiftSession("", StringHelpers.randomString(20), Empty)
  def inSession[T](a: => T): T = S.init(Req.nil, session) { a }
}

/**
 * Provides DB initialization for tests
 */
trait DBTestKit extends Loggable {

  Class.forName("org.h2.Driver")

  Logger.setup = Full(net.liftweb.util.LoggingAutoConfigurer())
  Logger.setup.foreach { _.apply() }

  def configureH2() = {
    SquerylRecord.initWithSquerylSession(
      Session.create(
        DriverManager.getConnection("jdbc:h2:mem:dbname;DB_CLOSE_DELAY=-1",
        "sa", ""),
        new H2Adapter)
    )
  }

  def createDb() {
    inTransaction {
      try {
        CoditiaSchema.drop
        CoditiaSchema.create
      } catch {
        case e : Throwable =>
          logger.error("DB Schema error", e)
          throw e
      }
    }
  }

}
