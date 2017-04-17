import scala.concurrent.{ ExecutionContext, Future }
import scala.concurrent.ExecutionContext.Implicits.global


val asyncTask: Future[Any] = Future {
  val s: String = ???  // I/O, takes very long
  s
}.recover {
  case e => ??? //log.error("Could not run asyncTask.", e)
}

/*
Methods recover and recoverWith are used to return a
fallback value. Not to perform side effects (e.g. log).
To perform side-effecting actions there are dedicated
callbacks:
• onFailure – if future fails
• onSuccess – if future succeeds
• onComplete – both
• andThen – both, allows chaining, order guaranteed
 */

val f1 = Future(5) // needs ExecutionContext, schedules task
val f2 = Future {5} // same as f1
val f3 = Future.successful(5) // no ExecutionContext, no task
val f4 = Future.failed(new Exception)


/*
If future used only for side effects, do not use map (requires
execution context). There are callbacks for side effects:

val f: Future[Result] = ...
f.foreach(result => actor ! result)


If there is a blocking code inside future (I/O, await etc.), mark
with blocking and use separate execution context:
It documents, better avoids deadlocks, optimizes.
 */
import concurrent.blocking
def readFromFile(path: String): String = "wow"

implicit val blockEx: ExecutionContext = global // use `global` just for demo
def readAsync(path: String): Future[String] = Future {
  blocking {
    readFromFile(path)
  }
}
