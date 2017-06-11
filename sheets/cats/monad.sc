/*
Monad extends the Applicative type class with a new function flatten. Flatten takes a value in a nested context (eg. F[F[A]] where F is the context) and "joins" the contexts together so that we have a single context (ie. F[A]).
 */


List(List(1)).flatten
Option(Option(1)).flatten
Option(None).flatten


/*
If Applicative is already present and flatten is well-behaved, extending the Applicative to a Monad is trivial. To provide evidence that a type belongs in the Monad type class, cats' implementation requires us to provide an implementation of pure (which can be reused from Applicative) and flatMap.

We can use flatten to define flatMap: flatMap is just map followed by flatten. Conversely, flatten is just flatMap using the identity function x => x (i.e. flatMap(_)(x => x)).
 */

import cats._
import cats.data.OptionT


/*
implicit def opttionMonad(implicit app: Applicative[Option]) = new Monad[Option] {
  override def pure[A](x: A): Option[A] = app.pure(x)

  override def flatMap[A, B](fa: Option[A])(f: (A) => Option[B]): Option[B] =
    app.map(fa)(f).flatten

  override def tailRecM[A, B](a: A)(f: (A) => Option[Either[A, B]]): Option[B] = ???
}
*/

import cats._
import cats.implicits._

Monad[Option].pure(42)

/*
flatMap is often considered to be the core function of Monad,
and cats follows this tradition by providing implementations of flatten and map derived from flatMap and pure

Name flatMap has special significance in scala, as for-comprehensions rely on this method to chain together operations in a monadic context.
 */
Monad[List].flatMap(List(1, 2, 3))(x => List(x, x))


/*
Monad provides the ability to choose later operations in a sequence based on the results of earlier ones. This is embodied in ifM, which lifts an if statement into the monadic context.
 */

Monad[Option].ifM(Option(true))(Option("truthy"), Option("falsy"))
Monad[List].ifM(List(true, false, true))(List(1, 2), List(3, 4))


/*
Unlike Functors and Applicatives, you cannot derive a monad instance for a generic M[N[_]] where both M[_] and N[_] have an instance of a monad.

A monad transformer. Cats already provides a monad transformer for Option called OptionT
 */

//OptionT.pure(12)


