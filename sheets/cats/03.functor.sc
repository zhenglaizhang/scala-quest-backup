import cats._
import cats.data._
import cats.implicits._

/*
Functor typeclass, which is basically for things that can be mapped over.



Functor Laws:
1) In order for something to be a functor, it should satisfy some laws. All functors are expected to exhibit certain kinds of
functor-like properties and behaviors. … The first functor law states that if we map the id function over a functor, the functor that we
get back should be the same as the original functor.

2) The second law says that composing two functions and then mapping the resulting function over a functor should be the same as first
mapping one function over the functor and then mapping the other one.

These are laws the implementer of the functors must abide, and not something the compiler can check for you.

 */
Functor[List].map(List(1, 2, 3)) {_ + 1}

// Either[A, B] as Functor
(Right(1): Either[String, Int]) map {_ + 1}

(Left("Boom"): Either[String, Int]) map {_ + 1}
/*
. Therefore, even though the operator syntax looks familiar, we should either avoid using it unless you’re sure that standard library
doesn’t implement the map or you’re using it from a polymorphic function. One workaround is to opt for the function syntax.
 */

val h = ((x: Int) => x + 1) map {_ * 7}
h(3)

val g = ((_: Int) + 1) compose ((x: Int) => x * 7)
g(3)

/*
Basically map gives us a way to compose functions, except the order is in reverse from f compose g. Another way of looking at Function1
is that it’s an infinite map from the domain to the range.
 */

// lifted the function {(_: Int) * 2} to List[Int] => List[Int].
val lifted = Functor[List].lift {(_: Int) * 2}
lifted(List(1, 2, 3, 4))

List(1, 2, 3).void
List(1, 2, 3) fproduct {(_: Int) * 2}
List(1, 2, 3) as "x"


// law 1
assert((Right(1) map identity) == Right(1))

// law 2
val f1 = {(_: Int) * 3}
val f2 = {(_: Int) + 1}
val x = List(1, 2, 3)
assert((x map (f1 map f2)) == (x map f1 map f2))

