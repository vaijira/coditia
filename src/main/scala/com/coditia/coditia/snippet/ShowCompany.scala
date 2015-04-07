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
import scala.collection.mutable.Stack
import scala.collection.immutable.Queue
import com.coditia.coditia.model.BalanceSheetStatement

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

  private def getTableHeader(c: SecCompany): CssSel = {
    "@name *" #> Text(c.company .name._1)
    "th @year *" #> c.company.annualReports.map(r =>
           <a href={r.url._1}>{Text(r.date._1.get(Calendar.YEAR).toString)}</a>
           )
  }

  private def getTableBody(c: SecCompany): CssSel = {
    var bsVector = c.company.annualReports.map( r =>
      r.balanceSheet.statements.toVector.sortBy(_.idField._1)).toVector

    var result: Queue[Vector[String]] = Queue[Vector[String]]()

    val abstractStack = Stack[String]()

    var finish = false
    var index = 0

    while (!finish) {
      val concept = bsVector.minBy(_.size)(scala.math.Ordering.Int)(index)

      val i = bsVector.indexWhere( stmts => stmts(index).concept.get.name._1 != concept.concept.get.name._1)

      if (i == -1) {
        if (concept.concept.get.isAbstract._1) {
          abstractStack.push(concept.concept.get.name._1)
        } else {
          if (!abstractStack.isEmpty &&
              (abstractStack.top == (concept.concept.get.name._1 + "Abstract") ||
              abstractStack.top == concept.concept.get.name._1))
          {
            result = result :+ ("-------------------------------" +:
                bsVector.map( stmts => ""))
            abstractStack.pop
          }

          result = result :+ (concept.description._1.getOrElse("") +:
              bsVector.map( stmts => stmts(index).value._1.getOrElse(0.0).toString))
        }
        index += 1
        if (index == bsVector.head.size) {
          finish = true
        }
      } else {
        val missingConcept = bsVector(i)(index).concept.get.name._1
        result = result :+ (concept.description._1.getOrElse("") +:
          bsVector.map( stmts =>
            if (missingConcept ==  stmts(index).concept.get.name._1)
              stmts(index).value._1.getOrElse("").toString
            else
              "-"
              ))
        bsVector = c.company.annualReports.map( r =>
           r.balanceSheet.statements.
           filter(stmts => stmts.concept.get.name._1 != missingConcept).
           toVector.sortBy(_.idField._1)).toVector
      }

    }


   "tbody tr *" #> result.map(r => r.map(v => <td>{v}</td>))

  }

  def show: CssSel = {
    val company = companyVar.is
    company match {
      case Some(c) =>
        getTableHeader(c) &
        getTableBody(c)

      case None => {
        S.warning("warning", S.?("companyNotFoundMsg"))
        "#show_company" #> <span class="lift:embed?what=warning_box"></span>
      }
    }
  }
}
