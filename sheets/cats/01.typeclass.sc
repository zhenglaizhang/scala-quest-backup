import cats._


object Holder {

  // common interface/trait
  trait Closeable {
    def close(): Unit
  }

  // using interface
  class Connection extends Closeable {
    def close(): Unit = println("closing")
  }

  def closeIt(cl: Closeable): Unit = {
    cl.close() // close returns this
  }

  val conn: Connection = ???
  closeIt(conn)
}

/*
Interface - Problems:
  * Class must extend interface during its definition
    * Existing class cannot work
  * When interface used, function cannot safely return original type
    *  TODO http://tpolecat.github.io/2015/04/29/f-bounds.html
  * Operations on interfaces always require instance
  * Problems with inheritance (equals may be hard to implement)
 */


// Type Class


trait GenTraversableOnce[A] {
  def sum(implicit num: Numeric[A]): A

  def min(implicit ord: Ordering[A]): A

  def maxBy[B](f: A => B)(implicit cmp: Ordering[B]): A
}


trait Closeable[T] {
  def close(t: T): Unit
} // type class definition

class ActorSystem {def shutdown() = println("shutdowning...")}

implicit val asClose = new Closeable[ActorSystem] {
  def close(t: ActorSystem): Unit = t.shutdown()
} // type class instance for type ActorSystem

def closeIt[A: Closeable](a: A): Unit = implicitly[Closeable[A]].close(a)
def closeIt2[A](a: A)(implicit cl: Closeable[A]): Unit = cl.close(a)
// using type class

closeIt(new ActorSystem)

// type class
sealed trait TrafficLight

object TrafficLight {

  def red: TrafficLight = Red

  def yellow: TrafficLight = Yellow

  def green: TrafficLight = Green

  case object Red extends TrafficLight

  case object Yellow extends TrafficLight

  case object Green extends TrafficLight

}

implicit val trafficLightEq: Eq[TrafficLight] = new Eq[TrafficLight] {
  override def eqv(x: TrafficLight, y: TrafficLight) = x == y
}

// So apparently Eq[TrafficLight] doesn’t get picked up because Eq has nonvariant subtyping: Eq[A].
//TrafficLight.Red === TrafficLight.Yellow

// One way to workaround this issue is to define helper functions to cast them up to TrafficLight

