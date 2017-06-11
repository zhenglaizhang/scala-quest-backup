import cats._
import cats.data._
import cats.implicits._

/*
Functor typeclass, which is basically for things that can be mapped over.

Functor Laws:
In order for something to be a functor, it should satisfy some laws. All functors are expected to exhibit certain kinds of
functor-like properties and behaviors. …

1) The first functor law states that if we map the id function over a functor, the functor that we get back should be the same as the original functor.

2) The second law says that composing two functions and then mapping the resulting function over a functor should be the same as first mapping one function over the functor and then mapping the other one.

These are laws the implementer of the functors must abide, and not something the compiler can check for you.

  def map[A, B, F](fa: F[A])(f: A => B): F[B]

 */


Functor[List].map(List(1, 2, 3)) {_ + 1}

// Either[A, B] as Functor
(Right(1): Either[String, Int]) map {_ + 1}

(Left("Boom"): Either[String, Int]) map {_ + 1}
/*
. Therefore, even though the operator syntax looks familiar, we should either avoid using it unless you’re sure that standard library
doesn’t implement the map or you’re using it from a polymorphic function. One workaround is to opt for the function syntax.
 */

val h = ((x: Int) => x + 1) map {_ * 7}
h(3)
(((x: Int) => x + 1) compose ((x: Int) => x * 7))(3)

// Given any functor F[_] and any functor G[_] we can create a new functor F[G[_]] by composing them:
val g = ((_: Int) + 1) compose ((x: Int) => x * 7)
g(3)

val listOpt: Functor[({ type λ[α] = List[Option[α]] })#λ] = Functor[List] compose Functor[Option]
val listOptMap = listOpt.map(List(Some(1), None, Some(3)))(_ + 1)
listOptMap

/*
Basically map gives us a way to compose functions, except the order is in reverse from f compose g. Another way of looking at Function1 is that it’s an infinite map from the domain to the range.
 */

// We can use Functor to "lift" a function from A => B to F[A] => F[B]:
// lifted the function {(_: Int) * 2} to List[Int] => List[Int].
val lifted = Functor[List].lift {(_: Int) * 2}
lifted(List(1, 2, 3, 4))

val lenOption: Option[String] => Option[Int] = Functor[Option].lift(_.length)
lenOption(Some("abcd"))

List(1, 2, 3).void

// Functor provides an fproduct function which pairs a value with the result of applying a function to that value.
List(1, 2, 3) fproduct {(_: Int) * 2}
List("ab", "c", "def").fproduct(_.length)


List(1, 2, 3) as "x"


// law 1
assert((Right(1) map identity) == Right(1))

// law 2
val f1 = {(_: Int) * 3}
val f2 = {(_: Int) + 1}
val x = List(1, 2, 3)
assert((x map (f1 map f2)) == (x map f1 map f2))


// checking laws
//import cats.laws.discipline.FunctorTests
//val rs = FunctorTests[Either[Int, ?]].functor[Int, Int, Int]
//rs.all.check

implicit val optionFunctor: Functor[Option] = new Functor[Option] {
  override def map[A, B](fa: Option[A])(f: (A) => B): Option[B] = fa map f
}

implicit val listFunctor: Functor[List] = new Functor[List] {
  override def map[A, B](fa: List[A])(f: (A) => B): List[B] = fa map f
}

//implicit def function1Functor[In]: Functor[Function1[In, ?]] = {
//  override def map[A, B](fa: In => A)(f: A => B): Function1[In, B] = fa andThen f
//}

Functor[Option].map(Option("hello"))(_.length)
Functor[Option].map(None: Option[String])(_.length)

