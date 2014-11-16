package com.coditia.coditia.crawler

import scala.xml.XML
import com.coditia.coditia.model.CoditiaSchema
import net.liftweb.squerylrecord.RecordTypeMode._

class SecCrawler {

  val url = "http://www.sec.gov/Archives/edgar/monthly/xbrlrss-2006-02.xml"

  def proccessRss(url: String): Unit = {
    val rss = XML.load(url)

    val companies = (rss \\ "item").filter(item => (item \\ "formType").text == "10-K")

    for (company <- companies) {
      val name = (company \\ "companyName").text
      val cik = ((company \\ "cikNumber").text).toInt

      val secCompany = from(CoditiaSchema.secCompany)(c =>
          where(c.cik === cik) select (c))

      if (secCompany.isEmpty) {
        val company = CoditiaSchema.Company.createRecord.name(name)
        CoditiaSchema.company.insert(company)

        val secCompany = CoditiaSchema.SecCompany.createRecord.cik(cik).companyId(company.id)
        CoditiaSchema.secCompany.insert(secCompany)
      }

    }
  }
}
