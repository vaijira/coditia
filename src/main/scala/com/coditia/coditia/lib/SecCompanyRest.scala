/*
 * Rest api for companies
 *
 * Copyright (C) 2014-2015 Jorge Perez Burgos <jorge.perez*at*coditia.com>.
 *
 * This work is licensed under the terms of the Affero GNU GPL, version 3.
 * See the LICENSE file in the top-level directory.
 *
 */
package com.coditia.coditia.lib

import com.coditia.coditia.model.SecCompany
import net.liftweb.http.rest.RestHelper
import net.liftweb.json.JsonAST.JValue
import net.liftweb.http.LiftRules
import net.liftweb.util.BasicTypesHelpers.AsLong
import scala.xml._

object SecCompanyRest extends RestHelper {
  def init = {
    LiftRules.statelessDispatch.append(SecCompanyRest)
  }

  serve( "api" / "seccompany" prefix {
    case "all" :: Nil JsonGet _ => SecCompany.findAll: JValue

    case AsLong(id) :: Nil JsonGet _ => SecCompany.find(id): JValue
  })
}