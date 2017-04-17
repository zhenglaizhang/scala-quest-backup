import eu.timepit.refined._
import eu.timepit.refined.api.Refined
import eu.timepit.refined.auto._
import eu.timepit.refined.boolean.{ And, AnyOf, Not }
import eu.timepit.refined.char.{ Digit, Letter, Whitespace }
import eu.timepit.refined.collection.NonEmpty
import eu.timepit.refined.numeric._
import eu.timepit.refined.string.MatchesRegex
import eu.timepit.refined.boolean._
import eu.timepit.refined.char._
import eu.timepit.refined.collection._
import eu.timepit.refined.generic._
import eu.timepit.refined.string._
import shapeless.{ ::, HNil }
import shapeless.HNil

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




refineMV[NonEmpty]("hello")
//refineMV[NonEmpty]("")

type ZeroToOne = Not[Less[W.`0.0`.T]] And Not[Greater[W.`1.0`.T]]

//refineMV[ZeroToOne](1.8)
refineMV[ZeroToOne](0.2)

refineMV[AnyOf[Digit :: Letter :: Whitespace :: HNil]]('F')
//refineMV[MatchesRegex[W.`"[0-9]+"`.T]]("123.")

val d1: Char Refined Equal[W.`'3'`.T] = '3'

val d2: Char Refined Digit = d1

//val d3: Char Refined Letter = d1

val r1: String Refined Regex = "(a|b)"

//val r2: String Refined Regex = "(a|b"

//val u1: String Refined Url = "htp://example.com"
val u1: String Refined Url = "http://example.com"

val alwaysTrue: Boolean Refined True = true

type NonEmptyIntList = List[Int] Refined NonEmpty

// TODO: deep dive!!

