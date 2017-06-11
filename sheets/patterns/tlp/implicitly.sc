// capturing an implicit value that is in scope and has type T
//def implicitly[T](implicit e: T) = e

// The implicit that we want in this case is A =:= B for some types A and B.  A =:= B will only be found when A is the same type as B
implicitly[Int =:= Int]
//implicitly[Int =:= String]


// <:< and <%< for type conformance and views
//implicitly[Int =:= AnyVal]

// conformance (<:<)
implicitly[Int <:< AnyVal]

// conversion (<%<)
//implicitly[Int <%< Long]
