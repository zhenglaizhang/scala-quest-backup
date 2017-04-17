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

// a bunch implicit defs that mimics pattern-matching on types

MakeBigger(42)
MakeBigger("foo")

// MakeBigger(true)
// MakeBigger(32: Any)
// a completely type-safe operation, but with a poor error reporting.

/*
scala> :type MakeBigger.intCase
PolyExamples.MakeBigger.Case[Int]{type Result = Int}

scala> :type MakeBigger.stringCase
PolyExamples.MakeBigger.Case[String]{type Result = String}
 */
