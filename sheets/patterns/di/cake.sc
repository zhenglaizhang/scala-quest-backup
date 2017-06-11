case class User(name: String, password: String)

trait LoggingComponent {

  val logger: Logging

  trait Logging {
    def log(msg: String): Unit
  }

  class ConsoleLogger extends Logging {
    override def log(msg: String): Unit = println(msg)
  }

}

trait UserRepositoryComponent {
  val userRepository: UserRepository

  class UserRepository() {
    def authenticate(name: String, password: String): User = {
      println(s"authenticating user: $name")
      ???
    }

    def create(user: User) = println(s"creating user: $user")

    def delete(user: User) = println(s"deleteing user: $user")
  }

}

trait UserServiceComponent {
  this: UserRepositoryComponent with LoggingComponent =>
  // declared the dependencies. What is left is the actual wiring.

  val userService: UserService

  class UserService {
    def authenticate(username: String, password: String): User =
      userRepository.authenticate(username, password)

    def create(username: String, password: String) =
      userRepository.create(new User(username, password))

    def delete(user: User) = userRepository.delete(user)

  }

}

// all wiring is statically typed
// abstracted away the actual component instantiation
// as well as the wiring into a single “configuration” object.
object ComponentRegistry extends UserServiceComponent with UserRepositoryComponent with LoggingComponent {
  val userService = new UserService
  val userRepository = new UserRepository
  val logger = new ConsoleLogger
}


trait TestEnvironment extends UserServiceComponent with UserRepositoryComponent with LoggingComponent {
  val userService = new UserService
  val userRepository = new UserRepository
  val logger = new ConsoleLogger
}

class UserServiceSuite extends TestEnvironment
