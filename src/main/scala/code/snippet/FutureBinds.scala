package code
package snippet

import scala.concurrent.{Future,Await,ExecutionContext}
import scala.concurrent.duration.Duration
import scala.xml.NodeSeq

import net.liftweb.actor.LAFuture
import net.liftweb.http.S
import net.liftweb.util._
  import Helpers._

object FutureBinds {
  implicit def futureTransform[T](implicit innerTransform: CanBind[T], executionContext: ExecutionContext): CanBind[Future[T]] = new CanBind[Future[T]] {
    def apply(future: => Future[T])(ns: NodeSeq): Seq[NodeSeq] = {
      val concreteFuture = future
      val snippetId = s"lazySnippet${Helpers.nextNum}"

      S.mapSnippet(snippetId, { _: NodeSeq =>
        innerTransform(Await.result(future, Duration.Inf))(ns).flatten
      })

      <lift:lazy-load>{("^ [data-lift]" #> snippetId) apply ns}</lift:lazy-load>
    }
  }

  implicit def lafutureTransform[T](implicit innerTransform: CanBind[T]): CanBind[LAFuture[T]] = new CanBind[LAFuture[T]] {
    def apply(future: => LAFuture[T])(ns: NodeSeq): Seq[NodeSeq] = {
      val concreteFuture = future
      val snippetId = s"lazySnippet${Helpers.nextNum}"

      S.mapSnippet(snippetId, { _: NodeSeq =>
        innerTransform(future.get)(ns).flatten
      })

      <lift:lazy-load>{("^ [data-lift]" #> snippetId) apply ns}</lift:lazy-load>
    }
  }
}
