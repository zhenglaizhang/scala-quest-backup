/**
  * In the cake pattern we represent the dependency as a component trait.
  * We put the thing that is depended on into the trait along with an
  * abstract method to return an instance.
  */
trait OnOffDeviceComponent {
  val onOff: OnOffDevice

  trait OnOffDevice {
    def on: Unit

    def off: Unit
  }

}

trait SensorDeviceComponent {

  val sensor: SensorDevice

  trait SensorDevice {
    def isCoffeePresent: Boolean
  }

}


// implementations
trait OnOffDeviceComponentImpl extends OnOffDeviceComponent {

  class Heater() extends OnOffDevice {
    def on: Unit = println(s"heater.on")

    def off: Unit = println(s"heater.off")
  }

}

trait SensorDeviceComponentImpl extends SensorDeviceComponent {

  class PotSensor() extends SensorDevice {
    def isCoffeePresent: Boolean = true
  }

}

// service declaring two dependencies that it wants injected
trait WarmerComponentImpl {
  this: SensorDeviceComponent with OnOffDeviceComponent =>

  class Warmer() {
    def trigger = {
      if (sensor.isCoffeePresent) onOff.on
      else onOff.off
    }
  }

}

object ComponentRegistry extends
  OnOffDeviceComponentImpl with
  SensorDeviceComponentImpl with
  WarmerComponentImpl {
  val onOff = new Heater
  val sensor = new PotSensor
  val warmer = new Warmer
}

val warmer = ComponentRegistry.warmer
warmer.trigger
