import shapeless._
import net.zhenglai.lib._

case class Greeting(
  message: String
)

object MainApp extends App {
  val generic = Generic[Greeting]

  val hlist: String :: HNil = "Hello from shapeless!" :: HNil

  val greeting: Greeting = generic.from(hlist)

  println(greeting.message)


  (Right(12): Either[String, Int])
    .mapR(_ + 12)
    .mapL(e => println(e))
    .foreachR(println)

  (Left("error"): Either[String, Int])
    .mapR(_ + 12)
    .mapL(e => println(e))
    .foreachR(println)
}
