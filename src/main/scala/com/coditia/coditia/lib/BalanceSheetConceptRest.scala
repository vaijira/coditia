/*
 * Rest api for balance sheet concepts
 *
 * Copyright (C) 2014-2015 Jorge Perez Burgos <jorge.perez*at*coditia.com>.
 *
 * This work is licensed under the terms of the Affero GNU GPL, version 3.
 * See the LICENSE file in the top-level directory.
 *
 */
package com.coditia.coditia.lib

import com.coditia.coditia.model.BalanceSheetConcept
import net.liftweb.http.rest.RestHelper
import net.liftweb.json.JsonAST.JValue
import net.liftweb.http.LiftRules
import net.liftweb.util.Helpers.secureXML

object BalanceSheetConceptRest extends RestHelper {
  def init = {
    LiftRules.statelessDispatch.append(BalanceSheetConceptRest)
  }

  serve( "api" / "balancesheet" prefix {
    case Nil JsonGet _ => BalanceSheetConcept.findAll: JValue
  })
}
