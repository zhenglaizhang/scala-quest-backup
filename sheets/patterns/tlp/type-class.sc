/*
Type Classes, it is the way Polymorphism is implemented in Haskell, we can encode them in Scala as well, the most common way to do it is basically what we did before, an abstract type that takes one type parameter
`trait Printer[T] { ... }` and different implicit implementations in the companion object

  1) `Simulacrum` The main goal is to avoid encoding inconsistencies using macros
  2) `Scato` The main goal is to avoid inheritance to encode the hierarchy of the classes using instead natural transformations


The idea of typeclasses is that you provide evidence that a class satisfies an interface. Let's explain that again.
 */

trait CanFoo[A] {
  def foos(x: A): String
}

object CanFoo {
  def apply[A: CanFoo]: CanFoo[A] = implicitly
}

case class Wrapper(wrapped: String)

implicit object WrapperCanFoo extends CanFoo[Wrapper] {
  def foos(x: Wrapper) = x.wrapped
}

/*
The idea of typeclasses is that you provide evidence (WrapperCanFoo) that a class (Wrapper) satisfies an interface (CanFoo).
Instead of having Wrapper implement an interface directly, typeclasses let us split up the definition of the class and the implementation of the interface. That means I can implement an interface for your class, or that a third party can implement my interface for your class, and everything basically ends up working out.
 */

//def foo[A: CanFoo](thing: A) = implicitly[CanFoo[A]].foos(thing)
def foo[A: CanFoo](thing: A) = CanFoo[A].foos(thing)

implicit class CanFooOps[A: CanFoo](thing: A) {
  def foo = CanFoo[A].foos(thing)
}

def foo2[A: CanFoo](thing: A) = thing.foo



