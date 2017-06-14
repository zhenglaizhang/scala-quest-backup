import javafx.geometry.Side

import net.zhenglai.lib.fun.Attempt

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Try

/*
Exceptions are invisible to the type system, which can make them challenging to deal with.

It's easy to leave out the necessary error handling, which can result in unfortunate runtime errors.

In Scala, it is usually recommended to represent errors as part of the return type. Scala's standard library types such as Either and Try can be used for capturing errors in synchronous operations, while Future can be used for representing asynchronous operations. Furthermore, Scala programmers can also represent different outcomes as a custom type using sealed trait capabilities.
 */


def foo(i: Int): Try[String] = ???

// we're describing how the program should work in the happy path scenario
// This sequential composition style is called the Monadic style
def bar(): Try[String] =
for {
  x <- foo(1)
  y <- foo(2)
} yield x + y

def bar2(): Try[String] = foo(1).flatMap(x => foo(2).map(x +))

def bar3(): Try[String] = {
  val xf = foo(1)
  val yf = foo(2)

  // any error that foo(3) might produce will be completely silent.
  val zf = foo(3) // unused

  for {
    x <- xf
    y <- yf
  } yield x + y
}
/*
we've made multiple calls of foo, but we'll only capture at most one of the errors that might occur when we run the program.
This is because the composition will always end when it meets the first error.

If foo does have side effects (e.g. foo writes the input to the database), we most likely want to at least capture the error it might have produced rather than let it silently fail in the background.

Try, Either, and Future all have the method foreach, which will execute the given function only for successful results, but it will completely ignore the failure scenario
 */

Future {
  throw new RuntimeException("boom")
}.foreach(println)

/*
The problem we're seeing with Either, Try, and Future is that all of them are evaluated eagerly, and at the same time they can cause side effects.


These properties seem problematic together. What if our computations had only one of these properties?
 */


// Side effects with lazy evaluation
/*
Scala's Try values are evaluated eagerly. Let's create our own version of Try that is lazy evaluated.

When the value is examined, it produces a Scala Try as a result.
 */


def attemptPrint(s: String): Attempt[Unit] = Attempt(println(s))

def attemptOkExample: Attempt[Int] = {
  //  Since Attempt is lazy evaluated, the rogue print command will not be executed at all.
  val _ = attemptPrint("I won't be printed")
  for {
    x <- Attempt(1)
    _ <- attemptPrint(s"x = $x")
    y <- Attempt(2)
    _ <- attemptPrint(s"y = $y")
  } yield x + y
}

// ScalaZ.Task
// Cats.IO
// Monix.Task


// No side effects with eager evaluation
// follow the idiom of limiting the use of side effects
//    http://alvinalexander.com/scala/scala-idiom-methods-functions-no-side-effects
// The limitation here is of course that we now have no way to express effects within our computations.


// No side effects with lazy evaluation
/*
What if we were to eliminate both side effects and eager evaluation?

In this system, all the effects would be represented as values as opposed to side effects.
These values can then be composed together to create programs.
When we are ready to execute our program, we run it through an interpreter that translates our values into actual side effects.
 */


