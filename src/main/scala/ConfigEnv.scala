import com.typesafe.config.ConfigFactory

/**
  * config file with switch, not good...
  */
object ConfigEnv {

  val environment = sys.env.getOrElse("environment", "development")

  /**
    * problem: runtime crash, fail lazily
    */
  //  lazy val config = ConfigFactory.load(environment + ".config")


  /**
    * fail fast
    */
  val config = ConfigFactory.load(environment + ".config")
  val httpHost = config.getString("http.host")
  val httpPort = config.getInt("http.port")
  val mongoLogErrors = config.getBoolean("mongo.log-errors")

  /**
    * turn configuration into a case class
    */
  case class DemoAppConfig(httpHost: String,
                           httpPort: Int,
                           mongoLogErrors: Boolean)

}
