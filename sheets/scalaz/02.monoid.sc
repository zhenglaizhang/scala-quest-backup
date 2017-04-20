import scalaz.Alpha.A
/*
Monoid. … It’s a type for which there exists a function mappend, which produces another type in the same set; and also a function that
produces a zero.
 */


/*
IntMonoid is a monoid on Int
 */

trait Monoid[A] {
  def mappend(a1: A, a2: A): A

  def mzero: A
}

object IntMonoid extends Monoid[Int] {
  def mappend(a: Int, b: Int): Int = a + b

  def mzero: Int = 0
}

def sum(xs: List[Int]): Int = xs.foldLeft(IntMonoid.mzero)(IntMonoid.mappend)

sum(List(1, 2, 3, 4))

def sum1[A](xs: List[A], m: Monoid[A]): A = xs.foldLeft(m.mzero)(m.mappend)

sum1(List(1, 2, 3, 4, 4), IntMonoid)


/*
The final change we have to take is to make the Monoid implicit so we don’t have to specify it each time
 */


implicit val intMonoid = IntMonoid
// implicit parameter is often written as context bound:
def sum[A: Monoid](xs: List[A]) = {
  val m = implicitly[Monoid[A]]
  xs.foldLeft(m.mzero)(m.mappend)
}

sum(List(1, 2, 3))

/*
Our sum function is pretty general now, appending any monoid in a list. We can test that by writing another Monoid for String. I’m also
going to package these up in an object called Monoid. The reason for that is Scala’s implicit resolution rules: When it needs an implicit
 parameter of some type, it’ll look for anything in scope. It’ll include the companion object of the type that you’re looking for.
 */

object Monoid {
  // best practice, put monoid instance in campanion object
  implicit val StringMonoid: Monoid[String] = new Monoid[String] {
    override def mappend(a1: String, a2: String) = a1 + a2

    override def mzero = ""
  }
}

sum(List("a", "b", "c"))
