/*
considering data types in terms of their —that is, the operations they support and the laws that algebras
govern those operations.

We will consider algebras in the abstract, by writing code that doesn't just operate on one data type or another but on data types that
share a common all algebra

The name "monoid" comes from mathematics. The prefix "mon-" means "one", and in category theory a monoid is a category with one object.

The term for this kind of algebra is . The laws of "monoid" associativity and identity are collectively called the monoid laws. A monoid
consists of:
  1) Some type A
  2) A binary associative operation that takes two values of type and combines them into
one.
  3) A value of type that is an identity for that operation.
 */

package net.zhenglai.lib.fun

/*
Having vs. being a monoid
There is a slight terminology mismatch between programmers and
mathematicians, when they talk about a type a monoid as against being
having a monoid instance. As a programmer, it's natural to think of the
instance of type Monoid[A] as being . But that is not a monoid
accurate terminology. The monoid is actually both things—the type
together with the instance. When we say that a method accepts a value
of type Monoid[A], we don't say that it takes a monoid, but that it
takes that the type is a monoid

What a monoid, then? It is simply an implementation of an interface is governed by some laws. Stated tersely, a monoid is a type together
 with an associative binary operation ( ) which has an identity element () op zero .

Can we write any interesting programs over data any type, knowing nothing about that type other than that it's a monoid? Absolutely!
  */

trait Monoid[A] {
  def op(a: A, b: A): A

  def zero: A
}

object Monoid {

  val stringMonoid = new Monoid[String] {
    override def op(a: String, b: String) = a + b
    override def zero = ""
  }

  def listMonoid[A] = new Monoid[List[A]] {
    override def op(a: List[A], b: List[A]) = a ++ b
    override def zero = Nil
  }

  // TODO: todo
  /*
  intAddition
  intMultiplication
  booleanOr
  booleanAnd
  optionMonoid[A]

  test with val monoidLaws[A](m: Monoid[A]): Prop
   */


  /**
    * A function having the same argument and return type is called an endofunction
    * (The Greek prefix "endo-" means "within", in the sense that an endofunction's codomain is within
    * its domain.)
    *
    * @tparam A
    * @return
    */
  def EndoMonoid[A]: Monoid[A => A] = ???

  // Write a monoid instance for that inserts spaces String between words unless there already is one,
  // and trims spaces off the ends of the result
  def wordsMonoid(s: String): Monoid[String] = new Monoid[String] {
    override def op(a: String, b: String) = a.trim + " " + b.trim
    override def zero = ""
  }

  /*
    Monoids compose
  if types and are monoids, then the tuple type A B (A, B) is also a monoid (called their product)
   */
  def productMonoid[A, B](A: Monoid[A], B: Monoid[B]): Monoid[(A, B)] = ???

  // TODO: http://blog.higher-order.com/blog/2014/03/19/monoid-morphisms-products-coproducts/ 
  def coproductMnoid[A, B](A: Monoid[A], B: Monoid[B]): Monoid[Either[A, B]] = ???


  // Some data structures also have interesting monoids as long as their value types are monoids.
  def mapMergeMonoid[K, V](V: Monoid[V]): Monoid[Map[K, V]] = new Monoid[Map[K, V]] {
    override def zero = Map.empty[K, V]
    override def op(a: Map[K, V], b: Map[K, V]) = a.map {
      case (k, v) => (k, V.op(v, b.get(k) getOrElse V.zero))
    }
  }
}

def concatenate[A](as: List[A], m: Monoid[A]): A = { as.foldLeft(m.zero)(m.op) }

import Monoid._

val words = List("abc", "def", "ghi")
words.foldLeft(stringMonoid.zero)(stringMonoid.op)
words.foldRight(stringMonoid.zero)(stringMonoid.op)
words.foldLeft(wordsMonoid.zero)(wordsMonoid.op)
/*
it doesn't matter if we choose or foldLeft foldRight when folding with a monoid —we should get the same result.
This is precisely because the laws of associativity and identity hold
 */

/*
 What if our list has an element type that doesn't have a instance?
 Well, we can always over the list to turn it into a type that does. map
*/
def foldMap[A, B](ax: List[A], m: Monoid[B])(f: A => B): B = ???


/*
Associativity and parallelism
*/

sealed trait WC
case class Stub(chars: String) extends WC
case class Part(lStub: String, words: Int, rStub: String) extends WC

val wcMonoid: Monoid[WC] = ???

/*
Monoid homomorphisms
If you have your law-discovering cap on while reading this chapter, you
may notice that there is a law that holds for some functions between
monoids.

"foo".length + "bar".length == ("foo" + "bar").length
Here, is a function from to that length String Int preserves the
monoid structure. Such a function is called a monoid homomorphism4.
A monoid homomorphism between monoids and obeys the f M N
following general law for all values and : x y

  M.op(f(x), f(y)) == f(N.op(x, y))

This word comes from Greek, "homo" meaning "same" and "morphe" meaning "shape".

The same law holds for the homomorphism from to in the String WC
current example.
This property can be very useful when designing your own libraries. If two types that your library uses are monoids, and there exist functions between them, it's a good idea to think about whether those functions are expected to preserve the monoid structure and to check the monoid
homomorphism law with automated tests.
There is a higher-order function that can take any function of type A => B , where is a monoid, and turn it in to a monoid homomorphism from List[A]  to B.
Sometimes there will be a homomorphism in both directions between two monoids. Such a relationship is called a monoid isomorphism ("iso-" meaning "equal") and we say that the two monoids are isomorphic.

Associativity can also be exploited to gain efficiency.
A more efficient strategy would be to combine the list by halves.
 */


// length & splitAt
def foldMapV[A, B](v: IndexedSeq[A], m: Monoid[B])(f: A => B): B = ???



/*
Foldable data structures


ints.foldRight(0)(_ + _)
 */

  // We write it as F[_] , where the underscore indicates that is not a type but
  // a type constructor that takes one type argument
trait Foldable[F[_]] {
  def foldRight[A, B](as: F[A])(f: (A, B) => B): B
  def foldLeft[A, B](as: F[A])(f: (B, A) => B): B
  def foldMap[A, B](as: F[A])(f: A => B)(mb: Monoid[B]): B
  def concatenate[A](as: F[A])(m: Monoid[A]): A = as.foldLeft(m.zero)(m.op)

  // Any foldable structure can be turned into a List. Write this conversion in a generic way
  def toList[A](fa: F[A]): List[A]
}

/*
Foldable[List]
Foldable[IndexedSeq]
Foldable[Stream]
Foldable[Tree]
 */
