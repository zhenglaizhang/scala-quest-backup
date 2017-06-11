/*
Implicits are a very important feature in Scala, they are used basically in 2 main ways:
  1) implicit parameters
  2) implicit conversions

implicit parameters are heavily used to do TLP in Scala
  it is mostly used when we need some kind of context (ExecutionContext)
*/

implicit val value = 4
def foo(implicit i: Int) = println(i)
foo


// implicits parameters are resolved also when we add type parameters


trait Printer[T] {
  def print(t: T): String
}

object Printer {
  //  the compiler, when looking for an implicit for a certain type, Printer in the example,
  // will look automatically in his companion object
  implicit val ip: Printer[Int] = (t: Int) => t.toString
}

def bar[T](t: T)(implicit p: Printer[T]) = p.print(t)
bar(3)

implicit val bp: Printer[Boolean] = (t: Boolean) => t.toString
bar(false)


// the resolution works even if not all the type parameters are known
trait Resolver[T, R] {
  def resolve(t: T): R
}

object Resolver {
  implicit val ib: Resolver[Int, Boolean] = (t: Int) => t > 1

  implicit val sd: Resolver[String, Double] = (t: String) => t.length.toDouble
}

// only T is extracted from the parameter t that we pass, R is instead computed from the implicit resolution,
// and then we can use it as return type
def resolve[T, R](t: T)(implicit p: Resolver[T, R]): R = p.resolve(t)

resolve(3)
resolve("hello")
