/*
Functor typeclass, which is basically for things that can be mapped over


Scalaz defines Functor instances for Tuples.
 */

import scalaz._
import Scalaz._

(1, 2, 3) map {_ + 1}
// the operation is only applied to the last value in the Tuple


val f1: (Int) => Int = ((_: Int) + 1) map (_ + 1)

/*
Basically map gives us a way to compose functions, except the order is in reverse from f compose g. No wonder Scalaz provides ∘ as an
alias of map. Another way of looking at Function1 is that it’s an infinite map from the domain to the range.
 */

val f = (_: Int) + 10
val g = (_: Int) * 2
(f map g)


// TODO: fix it
import shapeless._
import syntax.std.tuple._
import shapeless.poly._

def inc(i: Int) = i + 1
//(1, 2, 3).map(inc _)

// Scalaz also defines Functor instance for Function1.


List(1, 2, 3).map{4*}

