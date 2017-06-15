/*
Task is a data type for controlling possibly lazy & asynchronous computations,
  useful for controlling side-effects, avoiding nondeterminism and callback-hell.


In summary the Monix Task:
models lazy & asynchronous evaluation
models a producer pushing only one value to one or multiple consumers
allows fine-grained control over the execution model
doesn’t trigger the execution, or any effects until runAsync
doesn’t necessarily execute on another logical thread
allows for cancelling of a running computation
allows for controlling of side-effects, being just as potent as Haskell’s I/O ;-)
never blocks any threads in its implementation
does not expose any API calls that can block threads


“A Future represents a value, detached from time” — Viktor Klang

Scala’s Future to be about eager evaluation and certainly its design helps with that,
if you think about how it takes that implicit execution context whenever you call its operators, like map and flatMap.


But Task is different. Task is about lazy evaluation. Well, not always lazy, in fact Task allows for fine-tuning the execution model, as
you’ll see, but that’s the primary distinction between them. If Future is like a value, then Task is like a function. And in fact Task
can function as a “factory” of Future instances.

Another distinction is that Future is “memoized” by default, meaning that its result is going to be shared between multiple consumers if
needed. But the evaluation of a Task is not memoized by default. No, you have to want memoization to happen, you have to specify it
explicitly, as you’ll see.
 */

// TODO: vs ScalaZ Task  https://monix.io/docs/2x/eval/task.html#introduction 

import scala.concurrent.{ Await, Future }
import scala.util.{ Failure, Success }
import scala.concurrent.duration._

import monix.eval.{ Coeval, Task }
import monix.execution.Scheduler.Implicits.global
import monix.execution.CancelableFuture


val task = Task(1 + 1)

val cancellable = task.runOnComplete { result =>
  result match {
    case Success(value) => println(value)
    case Failure(ex) =>
  }
}

val future: CancelableFuture[Int] = task.runAsync
future.foreach(println)
future.foreach(println)
future.foreach(println)

future.cancel()

val f = Future(1)
f.foreach(println)
f.foreach(println)
f.foreach(println)
f.foreach(println)

/*
In terms of efficiency, Future having eager behavior, happens to be less efficient because whatever operation you’re doing on it, the
implementation will end up sending Runnable instances in the thread-pool and because the result is always memoized on each step, invoking
 that machinery (e.g. going into compare-and-set loops) whatever you’re doing. On the other hand Task can do execution in synchronous
 batches.
 */


val ta = Task.fork(Task.eval("Hello"))
// foreach
//val tco = for {r: String <- ta} {println("completed")}
//Await.result(tco, 3 seconds)


/*
by default the eval builder is executing things on the current thread,
unless an async boundary is forced by the underlying loop. So this code will always print “Got Lucky” ;-)
 */
val tb = Task.eval("Monix")
val tryingNow: Coeval[Either[CancelableFuture[String], String]] = tb.coeval
tryingNow.value match {
  case Left(future) =>
    future.foreach(r => println(s"Async: $r"))
  case Right(result) =>
    println(s"got lucky: $result")
}


/*
Task can replace functions accepting zero arguments, Scala by-name params and lazy val. And any Scala Future is convertible to Task.
 */


// Task.now lifts an already known value in the Task context,
// the equivalent of Future.successful or of Applicative.pure
val tc = Task.now {println("Effect"); "Now"}

// Task.eval is the equivalent of Function0, taking a function that will always be evaluated on runAsync, possibly on the same thread
// (depending on the chosen execution model)


/*
Task.evalOnce is the equivalent of a lazy val, a type that cannot be precisely expressed in Scala.
The evalOnce builder does memoization on the first run,
It also has guaranteed idempotency and thread-safety
 */
val td = Task.evalOnce {println("Effect"); "EvalOnce"}
td.runAsync.foreach(println)
td.runAsync.foreach(println)
td.runAsync.foreach(println)

/*
Task.defer is about building a factory of tasks.
 */

val te = Task.defer {
  Task.now {println("Effect"); "Hello!"}
}

te.foreach(println)
te.foreach(println)
te.foreach(println)
te.foreach(println)


val tf = Task.fromFuture(Future {println("Effect"); "fromFuture"})
tf.runAsync.foreach(println)
tf.runAsync.foreach(println)
tf.runAsync.foreach(println)

/*
fromFuture takes a strict argument and that may not be what you want. You might want a factory of Future. The design of Task however is
to have fine-grained control over the evaluation model, so in case you want a factory, you need to combine it with Task.defer
 */
val tg = Task.defer {
  Task.fromFuture(Future {println("Effect"); "fromFuture"})
}

tg.runAsync.foreach(println)
tg.runAsync.foreach(println)
tg.runAsync.foreach(println)
tg.runAsync.foreach(println)
