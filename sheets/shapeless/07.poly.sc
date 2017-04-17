import cats._
import shapeless._

/*w
A polymorphic function is a function that is defined for various – possibly unrelated – input types. Its output type varies according to
the parameters types.
 */

// one polymorphic function of one argument
object MakeBigger extends Poly1 {
  // int => int
  implicit def intCase = at[Int](_ * 100)

  // string => string
  implicit def stringCase = at[String](_.toUpperCase)
}

// a bunch implicit defs that mimics pattern-matching on types

MakeBigger(42)
MakeBigger("foo")

// MakeBigger(true)
// MakeBigger(32: Any)
// a completely type-safe operation, but with a poor error reporting.

/*
scala> :type MakeBigger.intCase
MakeBigger.Case[Int]{type Result = Int}

scala> :type MakeBigger.stringCase
MakeBigger.Case[String]{type Result = String}
 */
MakeBigger.intCase.isInstanceOf[shapeless.poly.Case[MakeBigger.type, Int :: HNil]]


case class User(
  name: String
)

val demo = 42 :: "Hello" :: User("John") :: HNil
def map[A, B, C](h: Int :: String :: User :: HNil)(f0: Int => A, f1: String => B, f2: User => C) = ???
// unpractical

// f should be one polymorphic function
// def map[A, B, C](h: Int :: String :: User :: HNil)(f: (Int => A) & (String => B) & (User => C))

object plusOne extends Poly1 {
  implicit def caseInt = at[Int](_ + 1)

  implicit def caseString = at[String](_ + 1)

  implicit def caseUser = at[User] { case User(name) => User(name + 1) }
}

demo.map(plusOne)

object incomplete extends Poly1 {
  implicit def caseInt = at[Int](_ + 1)

  implicit def caseString = at[String](_ + 1)
}

//demo.map(incomplete) // compilation error. Our incomplete func does not handle User



implicit val stringShow = Show.fromToString[String]
implicit val intShow = Show.fromToString[Int]
implicit val userShow = Show.fromToString[User]

object show extends Poly1 {
  // polymorphic function can use implicit parameters
  implicit def showa[A](implicit s: Show[A]) = at[A] { a => "showing " + s.show(a) }
}

demo.map(show)

//import cats.implicits._
//"a".show


