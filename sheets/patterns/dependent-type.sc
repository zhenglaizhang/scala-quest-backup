trait DepValue {
  type V
  val value: V
}

// parameter dependent type
def magic(that: DepValue): that.V = that.value

trait Meow {
  trait Bar

  def bar: Bar
}

def meow(f: Meow): f.Bar = f.bar

/*
Dependent Type:
  The return type of "magic" depends on the argument passed in.

  In computer science and logic, a dependent type is a type that depends on a value.

Scala is not a fully dependently typed language

 */


// Path Dependent Types
// In Scala we can define nested components,
// for instance we can define a class inside a trait, a trait inside a class and so on …

class Foo() {
  class Bar
}

val foo1 = new Foo
val foo2 = new Foo

val a: Foo#Bar = new foo1.Bar
val b: Foo#Bar = new foo2.Bar

val c: foo1.Bar = new foo1.Bar
//val d: foo2: Bar = new foo1.Bar     // error

/*
there are 2 ways to refer to a nested type:

  1) # means that we don’t refer to any specific instance, in this case Foo#Bar, every Bar inside every instance of Foo will be a valid instance

  2) . means that we can only refer the Bar instances that belong to a specif instance of Foo

The values a and b of type Foo#Bar can accept every Bar, instead as you can see d won’t work because foo1.Bar is different from foo2.Bar.


Parameter Dependent Types are a form of Path Dependent Types, as we have seen before we can refer to a type nested in a specific instance with the . syntax:

 */



// Build a type extractor
//trait Inner[F] {
//  type T
//}
//
//object Inner {
//  def apply[F](implicit inner: Inner[F]): Inner[F] = inner
//
//  implicit def mk[F[_]] = new Inner[F[A]] {
//    type T = A
//  }
//}


