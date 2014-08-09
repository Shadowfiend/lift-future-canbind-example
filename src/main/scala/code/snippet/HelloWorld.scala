package code 
package snippet 

import java.util.Date

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import net.liftweb.actor.LAFuture
import net.liftweb.common._
import net.liftweb.http._
import net.liftweb.util._
  import Helpers._

import code.lib._

class HelloWorld {
  def date: Box[Date] = DependencyFactory.inject[Date] // inject the date

  def nowTime = {
    "#now *" #> date.map(_.toString)
  }
  def futureTime = {
    "#later" #> Future {
      Thread.sleep(10000)

      "time *" #> date.map(_.toString)
    }
  }
  def laFutureTime = {
    "#latest" #> LAFuture.build {
      Thread.sleep(20000)

      "time *" #> date.map(_.toString)
    }
  }

  // replace the contents of the element with id "time" with the date
  def howdy = {
    nowTime &
    futureTime &
    laFutureTime
  }
}

