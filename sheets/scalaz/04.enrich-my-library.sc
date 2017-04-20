trait Monoid[A] {
  def zero: A

  def append(a: A, b: A): A
}

object Monoid {
  implicit val intMonoid: Monoid[Int] = new Monoid[Int] {
    override def zero = 0

    override def append(a: Int, b: Int) = a + b
  }

  implicit val stringMonoid: Monoid[String] = new Monoid[String] {
    override def append(a: String, b: String) = a + b

    override def zero = ""
  }
}

def plus[A: Monoid](a: A, b: A): A = implicitly[Monoid[A]].append(a, b)


trait MonoidOp[A] {

  val F: Monoid[A]

  val value: A

  def |+|(a2: A) = F.append(value, a2)
}

implicit def toMonoidOp[A: Monoid](a: A): MonoidOp[A] = new MonoidOp[A] {
  val F = implicitly[Monoid[A]]
  val value = a
}

3 |+| 3
"a" |+| "4"

/*
We were able to inject |+| to both Int and String with just one definition.
 */

/*
Using the same technique, Scalaz also provides method injections for standard library types like Option and Boolean:
 */

import scalaz.Scalaz._

1.some | 2
Some(1).getOrElse(2)

(1 > 10) ? 1 | 2
if (1 > 10) 1 else 2


/*
"Pimp My Library" pattern allows you to decorate classes with additional methods and properties.
 */

class BlingString(string: String) {
  def bling = "*" + string + "*"
}

implicit def toBlingString(string: String) = new BlingString(string)
"abc".bling
