package net.zhenglai.lib.fun

import scala.concurrent.Future

object converter {

  implicit class ScalaFuture[T](future: Future[T]) {
    def asTwitter = ???
  }

}
