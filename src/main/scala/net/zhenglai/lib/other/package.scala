package net.zhenglai.lib

import cats.kernel.Monoid

package object other {

  implicit def monoidTuple[A: Monoid, B: Monoid]: Monoid[(A, B)] =
    new Monoid[(A, B)] {
      override def empty: (A, B) = (Monoid[A].empty, Monoid[B].empty)

      override def combine(x: (A, B), y: (A, B)): (A, B) = {
        val (xa, xb) = x
        val (ya, yb) = y
        Monoid[A].combine(xa, ya) -> Monoid[B].combine(xb, yb)
      }
    }
}
