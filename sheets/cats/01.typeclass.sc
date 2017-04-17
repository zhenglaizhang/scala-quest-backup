import cats._
import cats.data._
import cats.implicits._

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

