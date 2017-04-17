import shapeless.the

sealed trait Shape

final case class Rectangle(
  width: Double,
  height: Double
) extends Shape

final case class Circle(
  radius: Double
) extends Shape

val rect: Shape = Rectangle(3.0, 4.0)
val circ: Shape = Circle(1.0)

// The beauty of ADTs is that they are completely type safe. The compiler has
//complete knowledge of the algebras² we define,
// The word “algebra” meaning: the symbols we define, such as rectangle and circle; and the
//rules for manipulaঞng those symbols, encoded as methods.

// Warning:(20, 35) match may not be exhaustive.
//It would fail on the following input: Circle(_)
//def area(shape: Shape): Double = shape match {
//^

def area(shape: Shape): Double = shape match {
  case Rectangle(w, h) => w * h
  case Circle(r) => math.Pi * r * r
}

area(rect)


/*
Sealed traits and case classes are undoubtedly the most convenient encoding
of ADTs in Scala. However, they aren’t the only encoding. For example, the
Scala standard library provides generic products in the form of Tuples and
a generic coproduct in the form of Either.
 */
type Rectangle2 = (Double, Double)
type Circle2 = Double
type Shape2 = Either[Rectangle2, Circle2]

val rect2: Shape2 = Left((3.0, 4.0))
val circ2: Shape2 = Right(1.0)
//  We can sঞll write completely type safe operaঞons involving Shape2

def area2(shape: Shape2): Double = shape match {
  case Left((w, h)) => w * h
  case Right(r) => math.Pi * r * r
}

area2(rect2)

area2(circ2)

// Shape2 is a more generic encoding than Shape³. Any code that operates on a pair of Doubles will be able to operate on a Rectangle2 and
// vice


/*
Shapeless gives us the best of both worlds: we can use friendly semanঞc types
by default and switch to generic representaঞons when we want interoperability
(more on this later). However, instead of using Tuples and Either, shapeless
uses its own data types to represent generic products and coproducts.

Unit as 0-length tuples??
The least upper bound of Unit and Tuple2 is Any so a combinaঞon of the two is impractical.


shapeless uses a different generic encoding for product
types called heterogeneous lists or HLists⁴(it's a `Product`)

An HList is either the empty list HNil, or a pair ::[H, T] where H is an arbitrary
type and T is another HList
 */




