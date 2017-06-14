package net.zhenglai.lib.fun

import scala.util.Try

// lazy Try[T]
final class Attempt[A](proc: => A) {
  def evaluate(): Try[A] = Try(proc)

  def evaluateUnsafe(): A = proc

  def map[B](f: A => B): Attempt[B] = new Attempt[B](f(proc))

  def flatMap[B](f: A => Attempt[B]): Attempt[B] =
    new Attempt[B](f(proc).evaluateUnsafe())

  def withFilter(f: A => Boolean): Attempt[A] =
    new Attempt[A]({
      val r = proc
      if (f(r)) r
      else throw new NoSuchElementException("filter == false")
    })
}

object Attempt {
  def apply[A](proc: => A): Attempt[A] = new Attempt(proc)
}
