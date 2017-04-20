import cats._
import cats.data._
import cats.implicits._

/*
So far, when we were mapping functions over functors, we usually mapped functions that take only one parameter. But what happens when we
map a function like *, which takes two parameters, over a functor?
 */
val hs = Functor[List].map(List(1, 2, 3, 4))({(_: Int) * (_: Int)}.curried)
// List[Int => Int] = List(<function1>, <function1>, <function1>, <function1>)

Functor[List].map(hs)(_ (9))

/*
Cartesian defines product function, which produces a pair of (A, B) wrapped in effect F[_] out of F[A] and F[B]. The symbolic alias for
product is |@| also known as the applicative style.
 */

9.some
Some(9)
Option(9)
assert((Some(9): Option[Int]) == 9.some)
none[Int]


/*
With the Applicative type class, we can chain the use of the <*> function, thus enabling us to seamlessly operate on several applicative
values instead of just one.
 */

(3.some |@| 5.some) map {_ + _}
(none[Int] |@| 5.some) map {_ + _}
(3.some |@| none[Int]) map {_ + _}
/*
This shows that Option forms Cartesian.
 */

/*
Lists (actually the list type constructor, []) are applicative functors. What a surprise!
 */
(List(1, 2, 3) |@| List(4, 5, 6)) map {_ + _}

(List(1, 2, 3, 4) |@| List(4, 5, 6)) map {_ + _}


/*
Cartesian enables two operators, <* and *>, which are special cases of Apply[F].product:
 */
1.some <* 2.some
none[Int] <* 2.some
1.some *> 2.some
none[Int] *> 2.some
// If either side fails, we get None.

/*
Cartesian has a single law called associativity:
 */
