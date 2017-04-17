/*
Generic, the type class for mapping ADT types to generic representations

dependent types


*/

trait GenericSimple[A] { // type paramter

  type Repr // type member

  def to(value: A): Repr

  def from(value: Repr): A
}

import shapeless.Generic

// dependent typing: the result type of getRepr depends on its value parameters via their type members
// The intuiঞve take-away from this is that type parameters are useful as “inputs” and type members are useful as “outputs”.
def getRepr[A](value: A)(implicit gen: Generic[A]) = gen.to(value)

case class Vec(
  x: Int,
  y: Int
)

case class Rect(
  origin: Vec,
  size: Vec
)

getRepr(Vec(1, 2))
getRepr(Rect(Vec(0, 0), Vec(5, 5)))
