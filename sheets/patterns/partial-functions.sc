/*
a block with a bunch of case inside is one way of defining an anonymous function.

case blocks define special functions called partial functions


A partial function on the other hand is defined only for a subset of the possible values of its arguments:

Some values not “making sense” as the argument of a function because they can’t yield a significant result.

The difference in behavior between collect and map, which is that collect expects a partial function. map expects a total function

If you define the partial function inline, the compiler knows that it’s a partial function and you avoid the explicit PartialFunction trait.

In mathematics, a partial function X => Y is a function X' → Y, where X' is a subset of X. It would be incorrect to name Set a
PartialFunction, because it is a total function - it is defined on every element.
 */

List(1 -> "one", 2 -> "two")
  .foreach { case (k, v) => println(s"$k -> $v") }

//List(1, "one")
//  .map { case i: Int => i + 1 } // crashed, MatchError...

List(1, "one")
  .collect { case i: Int => i + 1 }

// partial function
// not defined for d == 0
def fraction(d: Int) = 41 / d

/*
The "def" version is creating a parameterless function, which produces a partial function every time it is invoked.
 */
val fraction2 = new PartialFunction[Int, Int] {
  def apply(d: Int) = 42 / d

  def isDefinedAt(d: Int) = d != 0
}

fraction2.isDefinedAt(0)


fraction2(42)
//fraction2(0) // ArithmeticException

// use `case` to define partial functions
val fraction3: PartialFunction[Int, Int] = {case d: Int if d != 0 => 42 / d}
fraction3.isDefinedAt(0)
fraction3.isDefinedAt(12)
//fraction3(0) // MatchError

val incAny: PartialFunction[Any, Int] = {case i: Int => i + 1}


// In Scala, any instance of Seq, Map is also a function
// Set is NOT a partial function
// In Scala any instance of Seq or Map (but not Set) is actually a partial function
// Set extends Function1[A, Boolean] and not PartialFunction[A, ???]

Map(1 -> 2).isDefinedAt(1)
//Set(1).isDefinedAt(1) // Error
// Set maps from objects to Boolean values, so it's always defined (true for members, false for nonmbers).

val pets = List("cat", "dog", "frog")
pets.isDefinedAt(0)
pets.isDefinedAt(13)
Seq(1, 2, 34).collect(pets)

//  Scala the PartialFunction trait supports the lift method, which converts the partial function to a normal function that doesn’t crash:
//  lift returns a function that returns an Option of the value.
pets.lift(0)
pets.lift(13)


// partial application
val maybePet = pets lift _
maybePet(0)
maybePet(13)

// doesn't make sense
trait NamedSet[T] extends Set[T] with PartialFunction[T, Boolean] {
  override def isDefinedAt(x: T) = true
}


// A terrific feature of partial functions is that you can chain them together.
val handleOdd: PartialFunction[Int, Int] = {case i: Int if i % 2 == 0 => println("odd"); i * 2}
val handleEven: PartialFunction[Int, Int] = {case i: Int if i % 2 == 1 => println("even"); i * 3}

val handleAll = handleOdd orElse  handleEven

handleAll(12)
handleAll(13)
