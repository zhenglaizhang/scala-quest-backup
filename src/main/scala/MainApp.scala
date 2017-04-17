import shapeless._

case class Greeting(
  message: String
)

object MainApp extends App {
  val generic = Generic[Greeting]

  val hlist: String :: HNil = "Hello from shapeless!" :: HNil

  val greeting: Greeting = generic.from(hlist)

  println(greeting.message)
}
