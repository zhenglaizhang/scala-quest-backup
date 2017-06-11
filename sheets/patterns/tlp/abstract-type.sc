/*
An Abstract Type is a type that is not known yet and we can define later,
it is defined with the keyword type, it basically works for types like the def keyword works for values
 */
trait Foo {
  type T

  def value: T
}

object FooString extends Foo {
  type T = String

  def value: T = "chao"
}

object FooInt extends Foo {
  type T = Int

  def value: T = 1
}

//  the function getValue is able to change his return type depending on the input that we pass.
def getValue(f: Foo): f.T = f.value

val fs: String = getValue(FooString)
val fi: Int = getValue(FooInt)


// type T is an alias
type T = String

// type T is not an alias
// actually a function, takes T as parameter and returns
// Either[String, T]
// `type` keyword allows us to define functions at type level
type Result[T] = Either[String, T]


