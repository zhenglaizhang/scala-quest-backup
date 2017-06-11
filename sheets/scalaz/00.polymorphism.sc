// todo http://eed3si9n.com/learning-scalaz/polymorphism.html
// todo http://debasishg.blogspot.tw/2010/06/scala-implicits-type-classes-here-i.html

def head[A](xs: Seq[A]): A = xs.head

head(List(1, 2))
head(1 :: 2 :: Nil)
head(List(Some(1), None))


/*
Parametric polymorphism refers to when the type of a value contains one or more (unconstrained) type variables, so that the value may
adopt any type that results from substituting those variables with concrete types.
 */


object subtype {

  trait Plus[A] {
    def plus(a2: A): A
  }

  def plus[A <: Plus[A]](a1: A, a2: A): A = a1.plus(a2)

  /*
   subtyping is not flexible since trait Plus needs to be mixed in at the time of defining the datatype. So it can’t work for Int and
   String.
   */
}


object ad_hoc_polymorphism {

  trait Plus[A] {
    def plus(a1: A, a2: A): A
  }


  def plus[A: Plus](a1: A, a2: A): A = implicitly[Plus[A]].plus(a1, a2)
  /*
  This is truely ad-hoc in the sense that

1. we can provide separate function definitions for different types of A
2. we can provide function definitions to types (like Int) without access to its source code
3. the function definitions can be enabled or disabled in different scopes
   */
}
