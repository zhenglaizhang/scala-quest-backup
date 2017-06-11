trait DepValue {
  type V
  val value: V
}

def magic(that: DepValue): that.V = that.value

/*
Dependent Type:
  The return type of "magic" depends on the argument passed in.
 */


// Build a type extractor
trait Inner[F] {
  type T
}

object Inner {
  def apply[F](implicit inner: Inner[F]): Inner[F] = inner

  implicit def mk[F[_]] = new Inner[F[A]] {
    type T = A
  }
}


