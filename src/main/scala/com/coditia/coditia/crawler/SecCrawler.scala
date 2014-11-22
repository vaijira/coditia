package com.coditia.coditia.crawler

import scala.xml.XML
import com.coditia.coditia.model.CoditiaSchema
import net.liftweb.squerylrecord.RecordTypeMode._
import net.liftweb.common.Loggable


abstract class SecFiling(val doc: String)
case object Filing10K extends SecFiling("10-K")


class SecCrawler extends Loggable {

  def proccessRss(url: String, filing: SecFiling): Unit = {
    logger.info("Loading XML " + url)
    val rss = XML.load(url)

    val companies = (rss \\ "item").filter(item => (item \\ "formType").text == filing.doc)

    for (company <- companies) {
      val name = (company \\ "companyName").text
      val cik = ((company \\ "cikNumber").text).toInt
      logger.debug("processing 10-K file for company: " + name + " and CIK: " + cik)

      val secCompany = from(CoditiaSchema.secCompany)(c =>
          where(c.cik === cik) select (c))

      if (secCompany.isEmpty) {
        val company = CoditiaSchema.Company.createRecord.name(name)
        CoditiaSchema.company.insert(company)

        val secCompany = CoditiaSchema.SecCompany.createRecord.cik(cik).companyId(company.id)
        CoditiaSchema.secCompany.insert(secCompany)
        logger.debug("Created company " + company)
      }

    }
  }
}
