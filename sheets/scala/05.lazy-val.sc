import cats.Functor

final class A {
  lazy val value: Int = 43
}


final class B {
  @volatile var bitmap_0: Boolean = false
  var value_0: Int = _

  private def value_lazy(): Int = {
    this.synchronized {
      if (!bitmap_0) {value_0 = 42; bitmap_0 = true}
    }
    value_0
  }

  def value = if (bitmap_0) value_0 else value_lazy()
}

/*
Lazy vals - Drawbacks!!!
Lower performance (if ... else on every acccess).
• Possible deadlocks.


Legitimate Use Case – Circular Recursion
Unless some kind of recursion is used, lazy vals are better to
avoid.
 */


def fun(ss: List[String], n: Int): List[Int] = ???
//Inifite number of possible implementations.
//This signature gives programmer too much power (lots of
//methods, lots of possibilities, lots of combinations).

/*
Power tends to corrupt,
and absolute power corrupts absolutely.
— Lord Acton


GO ABSTRACT
 */
def fun2[A](ss: List[A], f: A => Int): List[Int] = ???

def fun3[A, B, F[_]](ss: F[A], f: A => B): F[B] = ???
// No implementation possible. We have no information at all about F.

def fun4[A, B, F[_] : Functor](ss: F[A], f: A => B): F[B] = ???
// (Functor says that F has method map)
/*
One possible implementation (most likely correct). It will work
for many types (lists, options, futures etc.):


Parametricity is an abstract property which, when applied,
makes polymorphic function act the same way in all cases.
In other words: the more type parameters a function uses and
the less concrete types it uses, the more parametric.
Has mathematical background (free theorem).


Scala has several properties that break parametricity. Better to
avoid them to help reasoning:
• null
• throwing exceptions
• isInstanceOf, asInstanceOf
• side effects
• methods on AnyRef (Object): equals, toString,
hashCode, getClass


Try to abstract your functions. Chance is it will more reusable
and less error-prone. In many cases, you will also find out that
such abstract function already exists in some library.
Requires a lot of learning (things like traverse, functor, monad
etc. are very helpful here).
Watch Parametricity, Types are Documentation (2014) by
Tony Morris. You will find it on YouTube
 */
// TODO: wow
