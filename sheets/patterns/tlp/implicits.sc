/*
Implicits are a very important feature in Scala, they are used basically in 2 main ways:
  1) implicit parameters
  2) implicit conversions

An implicit variable in Scala is one marked with the implicit modifier,

an implicit parameter is a parameter marked with that keyword
technically, it's a parameter list that's marked with the modifier

implicit parameters are heavily used to do TLP in Scala
  it is mostly used when we need some kind of context (ExecutionContext)

There is compile time overhead but also runtime, because we’ll have to instantiate a Printer instance per cycle.

Since we're indexing by type only, Scala has no idea what to do if multiple implicit variables of the same type are in scope.
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



/*
  Implicit conversions.

If you define a one argument method with the implicit modifier, Scala uses that as a way to convert arguments of the input type to the output type in calls when this method is in scope.

This also applies for one argument classes.


implicit conversions have some obvious uses - generally for method injection

 I'd say it's generally not a great idea to use implicit parameters. They're simply not ... explicit enough, and can lead to hard-to-debug magical behaviour. That they're indexed only by type is a further source of error, and the complex precedence rules for resolving implicit ambiguities is a third. Except Type Classes...
 */

implicit def agentCodename(i: Int): String = s"00$i"
def hello(name: String) = s"Hello, $name"
hello(7)
// "hello 007!

implicit class Agent(code: Int) {
  def codeName = s"00$code"
}

def hello2(agent: Agent) = s"hello, ${agent.codeName}"
hello(7)

// http://www.cakesolutions.net/teamblogs/demystifying-implicits-and-typeclasses-in-scala
