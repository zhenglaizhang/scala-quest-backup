object Foo {
  def bar(s: String) = println(s)
}

// use the infix notation for methods
Foo.bar("hello")    // standard
Foo bar "world"     // infix

// use the infix operator for types
trait Bar[A, B]

type Test1 = Bar[Int, String]   // standard
type Test2 = Int Bar String     // infix

// use symbols in type names the same way we can use them in method names
trait ::[A, B]

type Test3 = ::[Int, String]
type Test4 = Int :: String
