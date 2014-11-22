package com.coditia.coditia.crawler

import com.coditia.coditia.model.{DBTestKit, TestLiftSession, CoditiaSchema}
import org.scalatest.FlatSpec
import net.liftweb.common.Loggable
import net.liftweb.squerylrecord.RecordTypeMode._


class SecCrawlerTest extends FlatSpec with DBTestKit with TestLiftSession with Loggable {
  override def withFixture(test: NoArgTest) = {
    configureH2
    createDb
    inSession {
      inTransaction {
        super.withFixture(test)
      }
    }
  }

  "Sec Crawler" should "create a new company" in {
    val crawler = new SecCrawler
    //original data "http://www.sec.gov/Archives/edgar/monthly/xbrlrss-2006-02.xml"
    val url = getClass.getResource("/xbrlrss-2006-02.xml")
    logger.debug("loading file ..." + url.getPath)
    crawler.proccessRss(url.getPath, Filing10K)

    val companies = from(CoditiaSchema.secCompany)(c =>
          where(c.cik === 796343) select (c))

    assert(companies.size == 1, "Only one company must have been created")

    val company = companies.head

    assert(company.cik._1  == 796343, "Company CIK should be 796343 for ADOBE")
    assert(company.company.name._1 == "ADOBE SYSTEMS INC", "Company name must be ADOBE SYSTEMS INC")
  }

}

