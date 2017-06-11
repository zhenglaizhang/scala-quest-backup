/*
A semigroup for some given type A has a single operation (which we will call combine), which takes two values of type A, and returns a value of type A. This operation must be guaranteed to be associative. That is to say that:

((a combine b) combine c)
must be the same as

(a combine (b combine c))
for all possible values of a,b,c.
 */

import cats.Semigroup
import cats.implicits._

Semigroup[Int].combine(1, 2)
Semigroup[String].combine("hello", "world")
Semigroup[List[Int]].combine(List(1, 2, 3), List(4, 5, 6))
Semigroup[Option[Int]].combine(Option(1), Option(2))
Semigroup[Option[Int]].combine(Option(1), None)
Semigroup[Option[Int]].combine(None, Some(2))
Semigroup[Option[Int]].combine(None, None)
// TODO: semigroup for funtion??
Semigroup[Int ⇒ Int].combine({ _ + 1 }, { _ * 10 }).apply(6)
Semigroup[Int => Int].combine(_ + 1, _ * 2).apply(3)

// since the first version uses the Semigroup's combine operation, it will merge the map's values with combine.
Map("foo" -> Map("bar" -> 5)).combine(Map("foo" -> Map("bar" -> 6), "baz" -> Map()))
Map("foo" -> List(1, 2)).combine(Map("foo" -> List(3, 4), "bar" -> List(42)))

Map("foo" -> Map("bar" -> 5)) ++ Map("foo" -> Map("bar" -> 6), "baz" -> Map())
Map("foo" -> List(1, 2)) ++ Map("foo" -> List(3, 4), "bar" -> List(42))

Semigroup[Map[String, Map[String, Int]]].combine(
  Map("foo" -> Map("bar" -> 12)),
  Map("foo" -> Map("bar" -> 13))
)

// there aren't type class instances for Some or None, but for Option.
Option(1) |+| Option(2)
Option(1) |+| None
(None: Option[Int]) |+| None


// Foldable's foldMap, which maps over values accumulating the results, using the available Monoid for the type mapped onto.
val f = List(1, 2, 3)
f.foldMap(identity)
f.foldMap(_.toString)
List().foldMap(_.toString)






