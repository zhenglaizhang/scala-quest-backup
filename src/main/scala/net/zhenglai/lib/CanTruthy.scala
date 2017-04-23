package net.zhenglai.lib

import scalaz.Alpha.C

trait CanTruthy[A] {
  self =>

  /**
    * @param a
    * @return true, if `a` is truthy
    */
  def truthy(a: A): Boolean
}

object CanTruthy {
  def apply[A](implicit ev: CanTruthy[A]) = ev

  def truthys[A](f: A => Boolean): CanTruthy[A] = new CanTruthy[A] {
    /**
      * @param a
      * @return true, if `a` is truthy
      */
    override def truthy(a: A) = f(a)
  }
}

trait CanTruthyOps[A] {
  def self: A

  implicit def F: CanTruthy[A]

  final def truthy: Boolean = F.truthy(self)
}

object ToCanIsTruthyOps {
  implicit def toCanIsTruthyOps[A](v: A)(implicit ev: CanTruthy[A]): CanTruthyOps[A] =
    new CanTruthyOps[A] {
      override def self = v

      implicit def F: CanTruthy[A] = ev
    }
}

object CommonCanTruthy {
  implicit def intCanTruthy: CanTruthy[Int] = CanTruthy.truthys({
    case 0 => false
    case _ => true
  })

  implicit def listCanTruthy[A]: CanTruthy[List[A]] = CanTruthy.truthys({
    case Nil => false
    case _ => true
  })

  implicit def nilCanTruthy: CanTruthy[scala.collection.immutable.Nil.type] = CanTruthy.truthys(_ => false)

  implicit def booleanCanTruthy: CanTruthy[Boolean] = CanTruthy.truthys(identity)
}

object ext {

  import net.zhenglai.lib.ToCanIsTruthyOps._

  def truthyIf[A: CanTruthy, B, C](cond: A)(ifyes: => B)(ifno: => C) =
    if (cond.truthy) ifyes
    else ifno

  // TODO: fix it
//  truthyIf(Nil) {"NIL"} {"NOT NIL"}
//  truthyIf(2 :: 3 :: Nil) {"NOT NIL"} {"NIL"}
//  truthyIf(true) {"TRUE"} {"FALSE"}
}
