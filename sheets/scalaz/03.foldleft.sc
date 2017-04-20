// TODO: fix this!!

trait FoldLeft[F[_]] {
  def foldLeft[A, B](xs: F[A], b: B, f: (B, A) => B): B
}

object FoldLeft {
  implicit val FoldLeftList: FoldLeft[List] = new FoldLeft[List] {
    def foldLeft[A, B](xs: List[A], b: B, f: (B, A) => B) = xs.foldLeft(b)(f)
  }
}


trait Monoid[A] {
  def mzero: A

  def mappend(a: A, b: A)
}

object Monoid {
  implicit val intMonoid: Monoid[Int] = new Monoid[Int] {
    override def mzero = 0

    override def mappend(a: Int, b: Int) = a + b
  }

  implicit val stringMonoid: Monoid[String] = new Monoid[String] {
    override def mappend(a: String, b: String) = a + b

    override def mzero = ""
  }
}


def sum[M[_] : FoldLeft, A: Monoid](xs: M[A]): A = {
  val m = implicitly[Monoid[A]]
  val fl = implicitly[FoldLeft[M]]
  fl.foldLeft(xs, m.mzero, m.mappend)
}

sum(List(1, 2, 3, 4))
sum(List("a", "b", "c"))
