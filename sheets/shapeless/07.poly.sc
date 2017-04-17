import shapeless.Poly1
/*
A polymorphic function is a function that is defined for various – possibly unrelated – input types. Its output type varies according to
the parameters types.
 */

// one polymorphic function of one argument
object MakeBigger extends Poly1 {
  // int => int
  implicit def intCase = at[Int](_ * 100)

  // string => string
  implicit def stringCase = at[String](_.toUpperCase)
}
