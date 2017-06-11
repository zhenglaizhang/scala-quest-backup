/*
Implicits are a very important feature in Scala, they are used basically in 2 main ways:
  1) implicit parameters
  2) implicit conversions

implicit parameters are heavily used to do TLP in Scala
  it is mostly used when we need some kind of context (ExecutionContext)

There is compile time overhead but also runtime, because we’ll have to instantiate a Printer instance per cycle.
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
  implicit val intPrinter: Printer[Int] = (t: Int) => s"$t: Int"

  implicit val stringPrinter: Printer[String] = (t: String) => t

  implicit def optionPrinter[V](implicit pv: Printer[V]): Printer[Option[V]] = {
    case None => "None"
    case Some(v: V) => s"Option[${pv.print(v)}]"
  }

  // when a type take type parameters like List[T] is also called type constructor
  implicit def listPrinter[V](implicit pv: Printer[V]): Printer[List[V]] = {
    case Nil => "Nil"
    case l: List[V] => s"List[${l.map(pv.print).mkString(",")}]"
  }
}

def bar[T](t: T)(implicit p: Printer[T]) = p.print(t)
bar(3)

implicit val bp: Printer[Boolean] = (t: Boolean) => t.toString
bar(false)
//bar(Nil)
bar(List(1, 2, 3))
//bar(None)
bar(Option("wow"))
bar(Option(List(1, 2, 3)))


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


/*
  Multi-step Resolution

Implicit resolution doesn’t stop at the first level, we can have implicits that are generated taking implicit parameters themselves,
and this can go on until the compiler finds a stable implicit value,
this is a very good way to kill the compiler!
 */
