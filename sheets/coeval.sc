import scala.util.{ Failure, Random, Success, Try }

import monix.eval.Coeval
import monix.eval.Coeval.Error
import monix.eval.Coeval.Now

val coeval = Coeval {
  println("Effect!")
  1 + 1
}

// may trigger exceptions
coeval.value
coeval.value
coeval.value
coeval.value

// expose errors
coeval.runTry match {
  case Success(value) => println(value)
  case Failure(ex) => ???
}

/*
memoize effectively caches the result of the first value or runTry call:
  Coeval.evalOnce(f) <-> Coeval.eval(f).memoize
 */
val cm: Coeval[Int] = coeval.memoize
cm.value
cm.value
cm.value

// Coeval.raiseError can lift errors in the monadic context of Coeval:
val ce: Coeval[Int] = Coeval.raiseError[Int](new RuntimeException("boom!"))
// Coeval.Attempt, can work as a replacement for scala.util.Try
// although note that even if the values boxed by Now and Error are already evaluated, when invoking operators on them, like flatMap, the
// behavior is still lazy, which is the main difference between Attempt and Try
ce.runAttempt match {
  case Now(value) =>
  case Error(ex) => println(ex)
}

ce.runAttempt.map(identity)


/*
Coeval.eval is the equivalent of Function0, taking a function that will always be evaluated on invocation of value
 */
Coeval.eval(1 + 1).task


/*
Coeval.evalOnce is the equivalent of a lazy val, a type that cannot be precisely expressed in Scala

It also has guaranteed idempotency and thread-safety:
 */

val co = Coeval.evalOnce {
  println("only once")
  "hello"
}
// Result was memoized on the first run!
co.value
co.value
co.value
co.value

/*
Coeval.defer is about building a factory of coevals
 */

val cd = Coeval.defer {
  Coeval.now {println("Effect"); "hello"}
}
cd.value
cd.value
cd.value
cd.value

def fib(cycles: Int, a: BigInt, b: BigInt): Coeval[BigInt] = {
  if (cycles > 0)
    Coeval.defer(fib(cycles - 1, b, a + b))
  else
    Coeval.now(b)
}



def fib2(cycles: Int, a: BigInt, b: BigInt): Coeval[BigInt] =
  Coeval.eval(cycles > 0).flatMap {
    case true =>
      fib2(cycles - 1, b, a + b)
    case false =>
      Coeval.now(b)
  }


// Mutual Tail Recursion, ftw!!!
// Again, this is stack safe and uses a constant amount of memory.
{
  def odd(n: Int): Coeval[Boolean] =
    Coeval.eval(n == 0).flatMap {
      case true => Coeval.now(false)
      case false => even(n - 1)
    }

  def even(n: Int): Coeval[Boolean] =
    Coeval.eval(n == 0).flatMap {
      case true => Coeval.now(true)
      case false => odd(n - 1)
    }

  even(1000000)
}


// This gets transformed by the compiler into a batch of flatMap calls.
val aggregate = for {
  a <- Coeval.eval("a")
  b <- Coeval.eval("b")
  c <- Coeval.eval("c")
} yield a + b + c

aggregate.value


/*
Coeval is also an Applicative and hence it has utilities, such as zip2, zip3, up until zip6 (at the moment of writing) and also zipList
 */

val aggregate2 = Coeval.zip3(Coeval.eval("a"), Coeval.eval("b"), Coeval.eval("c")).map {
  case (a, b, c) => a + b + c
}

val aggregate3 = Coeval.zipMap3(Coeval.eval("a"), Coeval.eval("b"), Coeval.eval("c")) {
  (a, b, c) => a + b + c
}


val cl: Coeval[Seq[String]] = Coeval.sequence(Seq(Coeval("a"), Coeval("b")))

val randomEven = {
  Coeval.eval(Random.nextInt())
    .restartUntil(_ % 2 == 0)
}


/*
Clean-up Resources on Finish
 */

val cf = Coeval(1).doOnFinish {
  case None =>
    print("success")
    Coeval.unit
  case Some(ex) =>
    println(s"Failure: $ex")
    Coeval.unit
}
cf.value

val cg = Coeval(Random.nextInt).flatMap {
  case even if even % 2 == 0 =>
    Coeval.now(even)
  case odd =>
    throw new IllegalStateException(odd.toString)
}

cg.runTry
cg.runTry
cg.runTry
cg.runTry

val source = Coeval.raiseError[String](new IllegalStateException)

/*
Coeval.onErrorHandleWith and Coeval.onErrorRecoverWith are the equivalent of flatMap, only for errors.
 */
// demo purpose only
val recovered = source.onErrorHandleWith {
  case _: IllegalStateException =>
    // Oh, we know about illegal states, recover it
    Coeval.now("Recovered!")
  case other =>
    // We have no idea what happened, raise error!
    Coeval.raiseError(other)
}.onErrorRecoverWith {
  case _: IllegalStateException =>
    // Oh, we know about illegal states, recover it
    Coeval.now("Recovered!")
}.onErrorHandle { // In case we know or can evaluate a fallback result eagerly
  case _: IllegalStateException =>
    // Oh, we know about illegal states, recover it
    "Recovered!"
  case other =>
    throw other // Rethrowing
}.onErrorRecover {
  case _: IllegalStateException => "Recovered"
}.onErrorRestart(maxRetries = 4).onErrorRestartIf {
  case _: IllegalStateException => true
  case _ => false
}

recovered.runTry

val cs = Coeval.raiseError[Int](new IllegalStateException)
val materialized1: Try[Int] = coeval.runTry
val materialized: Coeval[Try[Int]] =
  coeval.materialize

// Now we can flatMap over both success and failure:
val ct = materialized.flatMap {
  case Success(value) => Coeval.now(value)
  case Failure(_) => Coeval.now(0)
}

cg.value

val ch: Try[Throwable] =  cs.failed.runTry
