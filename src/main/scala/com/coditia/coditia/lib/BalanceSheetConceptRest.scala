package com.coditia.coditia.lib

import com.coditia.coditia.model.BalanceSheetConcept
import net.liftweb.http.rest.RestHelper
import net.liftweb.json.JsonAST.JValue
import net.liftweb.http.LiftRules
import scala.xml._

object BalanceSheetConceptRest extends RestHelper {
  def init = {
    LiftRules.statelessDispatch.append(BalanceSheetConceptRest)
  }

  serve( "balancesheet" / "concept" prefix {
    case Nil JsonGet _ => BalanceSheetConcept.findAll: JValue
  })
}