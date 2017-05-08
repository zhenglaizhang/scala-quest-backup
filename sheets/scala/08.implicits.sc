/*

Implicits in Scala refers to either a value that can be passed "automatically", so to speak, or a conversion from one type to another that is made automatically.

Implicit Conversion

Speaking very briefly about the latter type, if one calls a method m on an object o of a class C, and that class does not support method m, then Scala will look for an implicit conversion from C to something that does support m.
 */

// implicit conversion
"abc".map(_.toInt)
// String => StringOps


/*
The other kind of implicit is the implicit parameter. These are passed to method calls like any other parameter, but the compiler tries to fill them in automatically. If it can't, it will complain. One can pass these parameters explicitly, which is how one uses breakOut
 */

def foo[T](t: T)(implicit integral: Integral[T]) = println(integral)


/*
View Bounds

There's one situation where an implicit is both an implicit conversion and an implicit parameter.
 */
def getIndex[T, CC](seq: CC, v: T)(implicit conv: CC => Seq[T]) = seq.indexOf(v)
/*
Behind the scenes, the compiler changes seq.IndexOf(value) to conv(seq).indexOf(value)
 */

getIndex("abc", 'b')


// TODO: fix it
// view bounds are deprecated, replace with implicit parameter
/*

This syntactic sugar is described as a view bound, akin to an upper bound (CC <: Seq[Int]) or a lower bound (T >: Null).
 */
def getIndex2[T, CC <% Seq[T]](seq: CC, v: T) = seq.indexOf(v)
getIndex2("abc", 'c')



/*
Context Bounds

Another common pattern in implicit parameters is the type class pattern. This pattern enables the provision of common interfaces to classes which did not declare them. It can both serve as a bridge pattern -- gaining separation of concerns -- and as an adapter pattern.
 */

def sum[T](xs: List[T])(implicit integral: Integral[T]) = {
  import integral._
  xs.foldLeft(integral.zero)(_ + _)
}

sum(List(1, 2, 3))

