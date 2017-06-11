/*
  Applicative extends Apply by adding a single method, pure:

    def pure[A](x: A): F[A]

This method takes any value and returns the value in the context of the functor. For many familiar functors, how to do this is obvious. For Option, the pure operation wraps the value in Some. For List, the pure operation returns a single element List
 */

import cats._
import cats.implicits._

Applicative[Option].pure(1)   // Some(1)
Applicative[List].pure(1)     // List(1)


/*
Like Functor and Apply, Applicative functors also compose naturally with each other.
 */

(Applicative[List] compose Applicative[Option])
  .pure(1)
// List(Some(1))



/*

APPLICATIVE FUNCTORS & MONADS

Applicative is a generalization of Monad, allowing expression of effectful computations in a pure functional way.

Applicative is generally preferred to Monad when the structure of a computation is fixed a priori. That makes it possible to perform certain kinds of static analysis on applicative values.
 */
Monad[Option].pure(1)         // Some(1)
Applicative[Option].pure(1)   // Some(1)
