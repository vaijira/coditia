/*
 * Get SEC files from RSS
 *
 * Copyright (C) 2014 Jorge Perez Burgos <jorge.perez*at*coditia.com>.
 *
 * This work is licensed under the terms of the Affero GNU GPL, version 3.
 * See the LICENSE file in the top-level directory.
 *
 */
package com.coditia.coditia.crawler

import scala.xml.XML
import com.coditia.coditia.model.{CoditiaSchema, Company, SecCompany}
import net.liftweb.squerylrecord.RecordTypeMode._
import net.liftweb.common.Loggable
import com.coditia.coditia.parser.Sec10KParser


abstract class SecFiling(val kind: String) {
  def parse(url: String)
}

case object Filing10K extends SecFiling("10-K") with Loggable {
  override def parse(url: String) = {
    logger.info("Start to parse 10-K filing from url " + url)
    val parser = new Sec10KParser(url)

    parser.parseBalanceSheet
  }
}

/**
 * Crawler to process SEC filings
 */
class SecCrawler extends Loggable {

  /**
   * Process SEC RSS files
   */
  def parseRss(url: String, filing: SecFiling): Unit = {
    logger.info("Loading SEC RSS XML " + url)
    val rss = XML.load(url)

    val companies = (rss \\ "item").filter(item => (item \\ "formType").text == filing.kind)

    for (company <- companies) {
      val name = (company \\ "companyName").text
      val cik = ((company \\ "cikNumber").text).toInt
      logger.debug("processing 10-K file for company: " + name + " and CIK: " + cik)

      val secCompany = from(CoditiaSchema.secCompany)(c =>
          where(c.cik === cik) select (c))

      if (secCompany.isEmpty) {
        val companyRecord = Company.createRecord.name(name)
        CoditiaSchema.company.insert(companyRecord)

        val secCompanyRecord = SecCompany.createRecord.cik(cik).companyId(companyRecord.id)
        CoditiaSchema.secCompany.insert(secCompanyRecord)
        logger.debug("Created company " + name)
      }

      val xbrlFile = (company \\ "xbrlFile").filter(file =>
                        file.attributes.toString.contains("edgar:type=\"EX-100.INS\"")).head

      val filingUrl = xbrlFile.attributes.toString.split(" ").find(x => x.startsWith("edgar:url")).head
      val dstUrl = filingUrl.split("=")(1).substring(1)
      filing.parse(dstUrl.dropRight(1))
    }
  }
}
