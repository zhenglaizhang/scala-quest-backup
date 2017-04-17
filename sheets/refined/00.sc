import eu.timepit.refined._
import eu.timepit.refined.api.Refined
import eu.timepit.refined.auto._
import eu.timepit.refined.numeric._

val i1: Int Refined Positive = 5

//val i2: Int Refined Positive = -4

// explicit refineMV macro that can infer the base
// type from its parameter
refineMV[Positive](5)

/*
// Macros can only validate literals because their values are known at
// compile-time. To validate arbitrary (runtime) values we can use the
// refineV function:
 */

val x = 42 // x value is not known at compile time
refineV[Positive](x)
refineV[Positive](-x)

val a: Int Refined Greater[W.`5`.T] = 10

//val b: Int Refined Greater[W.`12`.T] = a
val b: Int Refined Greater[W.`4`.T] = a

/*
W is a shortcut for shapeless.Witness which provides syntax for literal-based singleton types.

 */
