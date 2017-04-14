
object A {

}

trait Recurse {
  type Next <: Recurse

  type x[R <: Recurse] <: Int
}