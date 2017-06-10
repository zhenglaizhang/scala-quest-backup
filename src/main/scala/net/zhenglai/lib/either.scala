
object Either1 {
  val either: Either[String, Int] = Right(4)
  either.right.map(_ + 1)
  either.left.map(_ + "1")
}

object Either {

  import cats.syntax.either._

  val right: Either[String, Int] = Right(5)
  right.map(_ + 1)
}

