import cats._
import cats.data._
import cats.implicits._


1 === 1

//1 === "1"
//Error:(8, 8) type mismatch;
// found   : String("1")
// required: Int
//1 === "1"
//      ^



//1 == "foo"
// warning: comparing values of types Int and String using `==' will always yield false
//       1 == "foo"
//         ^


// Instead of the standard ==, Eq enables === and =!= syntax by declaring eqv method. The main difference is that === would fail compilation if you tried to compare Int and String.


// given Eq[A], === is universally the opposite of =!=.
// Equivalence relationship could include “having the same birthday” whereas equality also requires substitution property.


// In mathematics, an equivalence relation is a binary relation that is at the same time a reflexive relation, a symmetric relation and a transitive relation.
//  = is just an instance of equivalence relation.

// https://www.linkedin.com/pulse/equality-equivalence-andrea-raimondi

trait Recurse {
  type Next <: Recurse

  type x[R <: Recurse] <: Int
}





// Order

1 > 2.0
//1 compare 2.0
1.0 compare 2.0
1.0 max 2.0
// Order enables compare syntax which returns Int: negative, zero, or positive. It also enables min and max operators. Similar to Eq, comparing Int and Double fails compilation.



// PartialOrder
1 tryCompare 2
1.0 tryCompare Double.NaN
// PartialOrder enables tryCompare syntax which returns Option[Int]. According to algebra, it’ll return None if operands are not comparable.
// It’s returning Some(-1) when comparing 1.0 and Double.NaN, so I’m not sure when things are incomparable.


def lt[A: PartialOrder](a1: A, a2: A): Boolean = a1 <= a2

//lt[Int](1, 2.0)
lt(1, 2.0)

// PartialOrder also enables >, >=, <, and <= operators, but these are tricky to use because if you’re not careful you could end up using the built-in comparison operators.


// Show

// At first, it might seem silly to define Show because Scala already has toString on Any. Any also means anything would match the criteria, so you lose type safety. The toString could be junk supplied by some parent class:

1.show
"hello".show
(new {}).toString
//(new {}).show
//Error:(73, 11) value show is not a member of AnyRef
//  (new {}).show
//^

case class Person(name: String)
case class Car(model: String)
implicit val personShow = Show.show[Person](_.name)
implicit val carShow = Show.fromToString[Car]
Person("Alice").show
Car("CR-V").show
