package bootstrap.liftweb

import net.liftweb.http.{Html5Properties, LiftRules, Req}
import net.liftweb.sitemap.{Menu, SiteMap}
import net.liftweb.common.{Loggable, Full}
import net.liftweb.util.{Props, LiftFlowOfControlException}
import com.zaxxer.hikari.{HikariConfig, HikariDataSource}

/**
  * A class that's instantiated early and run.  It allows the application
  * to modify lift's environment
  */
class Boot extends Loggable {

  def boot {
    // where to search snippet
    LiftRules.addToPackages("com.coditia.coditia")

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


  }

  /* init DB using Hikari Pool */
  private def initDB() {
    def getAdapter = {
      import org.squeryl.adapters._
      Props.get("db.adapter") match  {
        case Full("postgresql") => new PostgreSqlAdapter
        case Full("h2") => new H2Adapter
        case _ => throw new IllegalStateException("Unable to create a database adapter for the application")
      }
    }
    val config = new HikariConfig()
    config.setJdbcUrl(Props.get("db.url") openOr "jdbc:h2:mem:db;AUTO_SERVER=TRUE")
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
