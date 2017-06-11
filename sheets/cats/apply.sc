/*
Apply extends the Functor type class (which features the familiar map function) with a new function ap.
The ap function is similar to map in that we are transforming a value in a context (a context being the F in F[A]; a context can be Option, List or Future for example).
However, the difference between ap and map is that for ap the function that takes care of the transformation is of type F[A => B], whereas for map it is A => B:
 */

import cats._

implicit val optionApply: Apply[Option] = new Apply[Option] {
  override def ap[A, B](ff: Option[(A) => B])(fa: Option[A]): Option[B] =
    fa.flatMap(a => ff.map(ff => ff(a)))

  override def map[A, B](fa: Option[A])(f: (A) => B): Option[B] = fa map f

  override def product[A, B](fa: Option[A], fb: Option[B]): Option[(A, B)] =
    fa.flatMap(a => fb.map(b => (a, b)))
}

implicit val listApply: Apply[List] = new Apply[List] {

  override def ap[A, B](ff: List[(A) => B])(fa: List[A]): List[B] =
    fa.flatMap(a => ff.map(f => f(a)))

  override def map[A, B](fa: List[A])(f: (A) => B): List[B] = fa map f

  override def product[A, B](fa: List[A], fb: List[B]): List[(A, B)] =
    fa.flatMap(a => fb.map(b => (a, b)))
}

import cats.implicits._

Apply[Option].map(Some(1))(_.toString)
Apply[Option].map(None: Option[Int])(_ + 2)

val listOpt: Apply[({
  type λ[α] = List[Option[α]]
})#λ] = Apply[List] compose Apply[Option]
val plusOne = (_: Int) + 1
listOpt.ap(List(Some(plusOne)))(List(Some(1), None, Some(3)))

val plusTwo = (_: Int) + 2

Apply[List].ap(List(plusTwo))(List(1, 2, 3, 4))
Apply[List].ap(List(plusOne, plusTwo))(List(1, 2, 3, 4))
Apply[Option].ap(None)(None)
Apply[Option].ap(None)(Some(1))
//Apply[Option].ap(Some((_: Int) => _ * 2))(Some(1))
//Apply[Option].ap(Some((_: Int) => _ * 2))(None)


// ap[n]
val addArity2 = (a: Int, b: Int) => a + b
val addArity3 = (a: Int, b: Int, c: Int) => a + b + c
Apply[Option].ap2(Some(addArity2))(Some(1), Some(2))
Apply[Option].ap2(Some(addArity2))(Some(1), None)
Apply[Option].ap3(Some(addArity3))(Some(1), Some(3), Some(2))
Apply[Option].ap3(Some(addArity3))(Some(1), Some(3), None)

// map[n]
Apply[Option].map2(Some(1), Some(3))(addArity2)
Apply[Option].map3(Some(1), Some(2), Some(3))(addArity3)
Apply[Option].map3(Some(1), None, Some(3))(addArity3)

Apply[List].map2(List(1, 2), List(3, 4, 5))(addArity2)
Apply[List].map3(List(1, 2), List(3, 4, 5), List(5))(addArity3)
Apply[List].map3(List(1, 2), Nil, List(5))(addArity3)

Apply[Option].tuple2(Some(1), Some(3))
Apply[Option].tuple3(Some(1), Some(3), Some(3))
Apply[Option].tuple3(Some(1), None, Some(3))
Apply[List].tuple3(List(1, 2), List(3, 4, 5), List(5))

/*
The |@| operator offers an alternative syntax for the higher-arity Apply functions (apN, mapN and tupleN). In order to use it, first import cats.implicits._.
*/

import cats.implicits._
val opt2 = Option(1) |@| Option(2)
val opt3 = opt2 |@| None

opt2.map(addArity2)
opt3.map(addArity3)

opt2.apWith(Some(addArity2))
opt3.apWith(Some(addArity3))
opt2.tupled
opt3.tupled
