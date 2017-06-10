/*
Monoid extends the Semigroup type class, adding an empty method to semigroup's combine. The empty method must return a value that when combined with any other instance of that type returns the other instance, i.e.

(combine(x, empty) == combine(empty, x) == x)
For example, if we have a Monoid[String] with combine defined as string concatenation, then empty = "".

Having an empty defined allows us to combine all the elements of some potentially empty collection of T for which a Monoid[T] is defined and return a T, rather than an Option[T] as we have a sensible default to fall back to.
 */

import cats._
import cats.implicits._

Monoid[String].empty
Monoid[Int].empty
Monoid[Double].empty
Monoid[Int => Int].empty.apply(12)


Monoid[String].combineAll(List("a", "b", "c"))
Monoid[String].combineAll(List())


// we can compose monoids to allow us to operate on more complex types

Monoid[Map[String, Int]].combineAll(List(Map("a" -> 1, "b" -> 2), Map("a" -> 3)))
Monoid[Map[String, Int]].combineAll(List())

//import net.zhenglai.lib.fun._
val f = List(1, 2, 3, 4)
f.foldMap(i => (i, i.toString))
f.map(i => (i, i.toString)).foldMap(identity)
f.foldMap(_ * 2 + 1)
