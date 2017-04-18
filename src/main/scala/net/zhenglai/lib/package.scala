package net.zhenglai

import scalaz.Alpha.C

package object lib {

  implicit class EitherOps[A, B](val either: Either[A, B]) extends AnyVal {
    def mapR[C](f: B => C): Either[A, C] = either match {
      case Left(a) => Left(a)
      case Right(b) => Right(f(b))
    }

    def mapL[C](f: A => C): Either[C, B] = either match {
      case Left(a) => Left(f(a))
      case Right(b) => Right(b)
    }

    def foreachR[C](f: B => Unit): Either[A, Unit] = either match {
      case Left(a) => Left(a)
      case Right(b) => Right(f(b))
    }
  }

}
