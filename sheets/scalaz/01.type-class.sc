/*
A typeclass is a sort of interface that defines some behavior. If a type is a part of a typeclass, that means that it supports and
implements the behavior the typeclass describes.

Type class provides purely functional data structures to complement those from the Scala standard library. It defines a set of
foundational type classes (e.g. Functor, Monad) and corresponding instances for a large number of data structures.

Type classes define a set of contracts that the adaptee type needs to implement. Many people misinterpret type classes synonymously with
interfaces in Java or other programming languages. With interfaces the focus is on subtype polymorphism, with type classes the focus
changes to parametric polymorphism. You implement the contracts that the type class publishes across unrelated types.

A type class implementation has two aspects to it :-
defining the contracts that instances need to implement
allow selection of the appropriate instance based on the static type checking that the language offers
Haskell uses the class syntax to implement (1), though this class is a completely different concept than the one we associate with OO
programming. For Scala we used traits to implement this feature and an extension of the trait into an object to implement instances of it.

As I mentioned earlier, Haskell uses a global dictionary to implement (2), while Scala does it through a lookup in the enclosing scope of
 invocation. This gives an additional flexibility in Scala where you can select the instance by bringing it into the local scope.
 */

import scalaz._
import Scalaz._

import net.zhenglai.lib.CanTruthy


sealed trait TrafficLight

object TrafficLight {

  case object Red extends TrafficLight

  case object Yellow extends TrafficLight

  case object Green extends TrafficLight

  def red: TrafficLight = Red

  def yellow: TrafficLight = Yellow

  def green: TrafficLight = Green

}


implicit val TrafficLightEqual: Equal[TrafficLight] = Equal.equal(_ == _)

import TrafficLight._

red === yellow
red === red


// A Yes-No type class

import net.zhenglai.lib.ToCanIsTruthyOps._
import net.zhenglai.lib.CommonCanTruthy._

10.truthy
List(1, 2, 3).truthy
Nil.truthy

//true.truthy

//false.truthy

