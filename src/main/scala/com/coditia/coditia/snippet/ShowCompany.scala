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
import com.coditia.coditia.model.Company
import net.liftweb.util.CssSel
import scala.xml.Text

class ShowCompany extends StatefulSnippet with Loggable {
  object companyVar extends RequestVar[Option[Company]](
      Company.find(
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
        "@name *" #> Text(c.name._1)

      case None => {
        S.warning("warning", "Company not found")
        "@name" #> <span class="lift:embed?what=warning_box"></span>
      }
    }
  }
}
