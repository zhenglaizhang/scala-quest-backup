
trait Foo[A] {
  type B

  def value: B
}

implicit def fi = new Foo[Int] {
  override type B = String

  override val value = "Foo"
}

implicit def fs = new Foo[String] {
  override type B = Boolean

  override val value = false
}

/*
in Scala we can use parameter dependent types to access the type defined inside a class/trait (path dependent type)
so if we want to use our type B in a function, as a return type, we can do that
 */

def foo[T](t: T)(implicit f: Foo[T]): f.B = f.value

val i: String = foo(2)
val s: Boolean = foo("")

/*
We can change the return type of a function using dependent type and the implicit resolution,
 */


import scalaz._
import Scalaz._

//def bar[T](t: T)(implicit f: Foo[T], m: Monoid[f.B]): f.B = m.zero
// illegal dependent method type:
//  parameter appears in the type of another parameter in the same section or an earlier one
/*

Scala tells us that we can’t use the dependent type in the same section, we can use it in the next parameters block or as a return type only.

 */

// defining a type alias where A0 is mapped to Foo A and B0 is mapped to type B
type Aux[A0, B0] = Foo[A0] {type B = B0}

//def bar[T, R](t: T)(implicit f: Foo.Aux[T, R], m: Monoid[R]): R = m.zero

// TODO: http://gigiigig.github.io/posts/2015/09/13/aux-pattern.html
