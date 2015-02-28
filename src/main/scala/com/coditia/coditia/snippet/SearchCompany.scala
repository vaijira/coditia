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

import net.liftweb.util.Helpers._
import net.liftweb.common.Loggable
import net.liftmodules.widgets.autocomplete.AutoComplete
import com.coditia.coditia.model.SecCompany
import net.liftweb.util.CssSel

class SearchCompany extends Loggable {
  val companies: List[String] = SecCompany.findAll.map( c => c.company.name._1).toList

  def render: CssSel = {
    val default = ""

    def suggest(value: String, limit: Int): List[String] =
     companies.filter(_.toLowerCase.startsWith(value))

    def submit(value: String): Unit = logger.info("Value submitted: " + value)

    "#autocomplete" #> AutoComplete(default, suggest, submit)
  }
}
