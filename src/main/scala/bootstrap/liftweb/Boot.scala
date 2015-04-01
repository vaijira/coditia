/*
 * Lift configuration
 *
 * Copyright (C) 2014-2015 Jorge Perez Burgos <jorge.perez*at*coditia.com>.
 *
 * This work is licensed under the terms of the Affero GNU GPL, version 3.
 * See the LICENSE file in the top-level directory.
 *
 */
package bootstrap.liftweb

import com.coditia.coditia.lib.{BalanceSheetConceptRest, SecCompanyRest}
import net.liftweb.http.{Html5Properties, LiftRules, Req}
import net.liftweb.sitemap.{Menu, SiteMap}
import net.liftweb.common.{Loggable, Full}
import net.liftweb.util.{Props, LiftFlowOfControlException}
import net.liftmodules.widgets.autocomplete.AutoComplete
import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import org.squeryl.internals.DatabaseAdapter


/**
  * A class that's instantiated early and run.  It allows the application
  * to modify lift's environment
  */
class Boot extends Loggable {

  def boot {
    // where to search snippet
    LiftRules.addToPackages("com.coditia.coditia")

    LiftRules.resourceNames = "Messages" :: Nil

    LiftRules.snippetDispatch.append {
      // For StatefulSnippets, return a *new instance*
      case "SearchCompany" => com.coditia.coditia.snippet.SearchCompany
      case "ShowCompany" => new com.coditia.coditia.snippet.ShowCompany
    }

    // Build SiteMap
    def sitemap(): SiteMap = SiteMap(
      Menu.i("Home") / "index"
    )

    // Use HTML5 for rendering
    LiftRules.htmlProperties.default.set((r: Req) =>
      new Html5Properties(r.userAgent))

    initDB()

    import net.liftweb.squerylrecord.RecordTypeMode._
    import net.liftweb.http.S
    import net.liftweb.util.LoanWrapper

    // Create a transaction around any http request
    S.addAround(new LoanWrapper {
      override def apply[T](f: => T): T = {

        val result = inTransaction {
          // If you want to enable logging everywhere:
          // import org.squeryl.Session
          // Session.currentSession.setLogger( s => logger.info(s) )
          try {
            Right(f)
          } catch {
            case e: LiftFlowOfControlException => Left(e)
          }
        }

        result match {
          case Right(r) => r
          case Left(exception) => throw exception
        }

      }
    })

    BalanceSheetConceptRest.init
    SecCompanyRest.init
    AutoComplete.init
  }

  /* init DB using Hikari Pool */
  private def initDB() {
    logger.info("Initializing database...")

    def getAdapter: DatabaseAdapter = {
      val adapterClass = Props.get("db.adapter") openOr "org.squeryl.adapters.H2Adapter"
      val adapter: DatabaseAdapter = Class.forName(adapterClass).newInstance().asInstanceOf[DatabaseAdapter]

      adapter
    }

    val config = new HikariConfig()
    Class.forName(Props.get("db.driver") openOr "org.h2.Driver")
    config.setJdbcUrl(Props.get("db.url") openOr "jdbc:h2:mem:db;DB_CLOSE_DELAY=-1")
    config.setUsername(Props.get("db.user") openOr "")
    config.setPassword(Props.get("db.password") openOr "")
    config.addDataSourceProperty("cachePrepStmts", "true")
    config.addDataSourceProperty("prepStmtCacheSize", "250")
    config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048")
    config.addDataSourceProperty("useServerPrepStmts", "true")

    val pool = new HikariDataSource(config)

    import org.squeryl.Session
    import net.liftweb.squerylrecord.SquerylRecord

    SquerylRecord.initWithSquerylSession(Session.create(pool.getConnection, getAdapter))

    LiftRules.unloadHooks append { () =>
      pool.shutdown() //should destroy the pool and it's associated threads
    }

  }
}
