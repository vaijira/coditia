/*
 * Tests for SEC crawler.
 *
 * Copyright (C) 2014 Jorge Perez Burgos <jorge.perez*at*coditia.com>.
 *
 * This work is licensed under the terms of the Affero GNU GPL, version 3.
 * See the LICENSE file in the top-level directory.
 *
 */
package com.coditia.coditia.crawler

import com.coditia.coditia.model.{DBTestKit, TestLiftSession, CoditiaSchema}
import org.scalatest.FlatSpec
import net.liftweb.common.Loggable
import net.liftweb.squerylrecord.RecordTypeMode._

/**
 * Test for SecCrawler
 */
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

  "SEC Crawler crawling specific XBRL RSS for 10-K" should "create a new company" in {
    val crawler = new SecCrawler
    //original data "http://www.sec.gov/Archives/edgar/monthly/xbrlrss-2009-02.xml"
    val url = getClass.getResource("xbrlrss-2009-02.xml")
    logger.debug("loading file ..." + url.getPath)
    crawler.parseRss(url.getPath, Filing10K)

    val companies = from(CoditiaSchema.secCompany)(c =>
          where(c.cik === 765880) select (c))

    assert(companies.size == 1, "Only one company with cik 765880 been created")

    val company = companies.head

    assert(company.cik._1  == 765880, "Company CIK should be 765880 for HCP")
    assert(company.company.name._1 == "HCP, INC.", "Company name must be HCP, INC.")
  }

}

