/*
 * Show company snippet.
 *
 * Copyright (C) 2014-2015 Jorge Perez Burgos <jorge.perez*at*coditia.com>.
 *
 * This work is licensed under the terms of the Affero GNU GPL, version 3.
 * See the LICENSE file in the top-level directory.
 *
 */
package com.coditia.coditia.snippet


import net.liftweb.http.{RequestVar, StatefulSnippet, S}
import net.liftweb.util.Helpers._
import net.liftweb.common.Loggable
import com.coditia.coditia.model.SecCompany
import net.liftweb.util.CssSel
import scala.xml.Text
import java.util.Calendar

class ShowCompany extends StatefulSnippet with Loggable {
  object companyVar extends RequestVar[Option[SecCompany]](
      SecCompany.find(
        tryo {
          S.param("id").openOr("0").toLong
        }.openOr(0)
      )
    )

  val dispatch: DispatchIt = {
    case v => {
      logger.debug("method received: " + v)
      show
    }
  }


  def show: CssSel = {
    val company = companyVar.is
    company match {
      case Some(c) =>
        "@name *" #> Text(c.company .name._1) &
        "thead" #> c.company.annualReports.map(r =>
          "th @year a [href]" #> Text(r.url._1) &
          "th @year a *" #> Text(r.date._1.get(Calendar.YEAR).toString)) &
        "tbody" #>   c.company.annualReports.flatMap( r => 
            r.balanceSheet.statements.filter(b => !b.value.get.isEmpty).
            toVector.sortBy(b => b.idField._1).
            map( b =>
              "td @concept *" #> Text(b.concept.get.label._1) &
              "td @value *" #> Text(b.value._1.getOrElse(0).toString)
            )
        )

      case None => {
        S.warning("warning", S.?("companyNotFoundMsg"))
        "@name" #> <span class="lift:embed?what=warning_box"></span>
      }
    }
  }
}
