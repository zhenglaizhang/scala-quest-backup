/*
The functional and side-effect free approach that resolves this problem, is wrapping the result of the computation in a datatype, that
will represent the possibility of the computation failing. Of course this also means that anything using this kind of value with a
failure context needs to take into account the possibility that the result represents a failure and act accordingly.
 */
def divide(dividend: Double, divisor: Double): Either[String, Double] = {
  if (divisor == 0)
  // handle the possible failed state of the computation
    Left("Can't divide by 0")
  else
    Right(dividend / divisor)
}

// TODO: more about Either!!


divide(12, 0) match {
  case Left(err) => println(err)
  case Right(res) => println(res)
}

divide(12, 1) match {
  case Left(err) => println(err)
  case Right(res) => println(res)
}

/*
Xor is almost the same as Either (they’re isomorphic, i.e. one can be freely transformed into the other) but there are two important
differences that make Xor a better solution in most cases:

Xor is a Monad (simply put in a practical context it has a map and flatMap function)
It’s right-side biased by default, i.e. the Right value of Xor represents success. This produces some differences on how some functions
work when using it (e.g. map will only apply to the right-sided value/type)
 */


sealed trait DivisionError

case object DivisionByZero extends DivisionError

case object SomeOtherPossibleError extends DivisionError

//def divide1(dividend: Double, divisor: Double): DivisionError


import net.zhenglai.lib._

divide(12, 1).mapR(_ + 100)
divide(12, 1).right.map(_ + 200)
divide(12, 0).left.map(_ + " 200")

for {
  r1 <- divide(12, 1).right
  r2 <- divide(12, 1).right
  r3 <- Right(r1 + r2).right
} yield (r1 + r2 + r3)

divide(12, 1).fold(l => {
  println(l)
}, r => {
  println(r)
})

divide(12, 0).fold(
  _ => 0, // default value
  r => r
)
