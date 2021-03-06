/*
 * Search company snippet.
 *
 * Copyright (C) 2014-2015 Jorge Perez Burgos <jorge.perez*at*coditia.com>.
 *
 * This work is licensed under the terms of the Affero GNU GPL, version 3.
 * See the LICENSE file in the top-level directory.
 *
 */
package com.coditia.coditia.snippet


import net.liftweb.http.DispatchSnippet
import net.liftweb.http.S
import net.liftweb.util.Helpers._
import net.liftweb.common.Loggable
import net.liftmodules.widgets.autocomplete.AutoComplete
import com.coditia.coditia.model.Company
import net.liftweb.util.CssSel

object SearchCompany extends DispatchSnippet with Loggable {

  val dispatch: DispatchIt = {
    case _ => render
  }

  def default: List[String] = {
    Company.getCompanies.map( c => c.name._1).toList
  }

  def render: CssSel = {
    val default = ""

    def suggest(value: String, limit: Int): List[String] =
     Company.getCompanies.filter(_.name._1.toLowerCase.startsWith(value)).map(_.name._1).toList

    def submit(value: String): Unit = {
      val companyId =
        if (value != "")
          Company.getCompanies.filter(_.name._1.toLowerCase.startsWith(value.toLowerCase)).
            map(_.idField._1).headOption
        else None

      logger.debug("Value submitted: " + value + " company id: " + companyId.getOrElse(""))

      S.redirectTo("company?id=" + companyId.getOrElse(""))
    }

    "#autocomplete" #> AutoComplete(
        default,
        suggest _,
        v => submit(v),
        List(("minChars" -> "2"), ("max" -> "15")),
        ("class" -> "form-control"), ("placeholder" -> S.?("searchCompanyMsg")))
  }
}
