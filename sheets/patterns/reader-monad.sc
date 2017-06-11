import scalaz.{Id, Kleisli}

val triple = (i: Int) => i * 3
val plusOne = (i: Int) => i + 1

val f = triple andThen plusOne
f(3)

(triple andThen (_.toString)) (1233)

/*
The Reader Monad is a monad defined for unary functions, using andThen as the map operation. A Reader, then, is just a Function1. We can wrap the function in a scalaz.Reader to get the map and flatMap methods
 */

import scalaz.Reader

val tripleR = Reader((i: Int) => i + 4)
tripleR.run(12)
tripleR(13)

tripleR.map(_ * 2)
  .run(2)

// The map and flatMap methods let us use for comprehensions to define new Readers:

val g: Kleisli[Id.Id, Int, Int] = for {
  _ <- tripleR
  j <- Reader((_: Int) * 4)
} yield j

val g1 = g.run(14)
g1

case class User(id: String, email: String, supervisorId: String)

trait UserRepository {
  def get(id: String): User

  def find(userName: String): User
}

object UserRepositoryImpl extends UserRepository {
  override def get(id: String): User = ???

  override def find(userName: String): User = ???
}

trait MailService

trait Config {
  def userRepository: UserRepository

  def mailService: MailService
}

trait Users {

  import scalaz.Reader

  // a function that will eventually return a User when given a UserRepository.
  // The actual injection of the dependency is deferred.
  def getUser(id: String) = Reader(
    (config: Config) => config.userRepository.get(id)
  )

  def findUser(userName: String) = Reader(
    (config: Config) => config.userRepository.find(userName)
  )
}

// We can now define all the other operations (as Readers) in terms of the primitive Readers:
object UserInfo extends Users {

  def userEmail(id: String): Reader[Config, String] = {
    getUser(id).map(_.email)
  }

  def userInfo(userName: String): Reader[Config, Map[String, String]] =
    for {
      user <- findUser(userName)
      boss <- getUser(user.supervisorId)
    } yield Map(
      "fullName" -> userName,
      "email" -> user.email,
      "boss" -> boss.id
    )
}


/*
Unlike in the implicits example, we don’t have UserRepository anywhere in the signatures of userEmail and userInfo. We don’t have to mention UserRepository anywhere other than our primitives. If we gain additional dependencies we can encapsulate them all in a single Config object and we only have to change the primitives.

a huge win in a large application that has many times more higher-level Readers than primitives.
 */

// All of these methods return Readers that can get User-related stuff out of a UserRepository.
// The actual dependency injection keeps getting deferred up to higher layers.

trait Controller {}

class Application(userRepository: UserRepository) extends Controller with Users {

  private def run[A](reader: Reader[UserRepository, A]) = {
    reader(userRepository)
  }

  // actions which calls e.g:
  //  run(getUser("id"))
}

/*
object Application extends Application(UserRepositoryImpl)

class Application(userRepository: UserRepository) extends Controller with Users {

  def userEmail(id: Int) = Action {
    Ok(run(UserInfo.userEmail(id)))
  }

  def userInfo(username: String) = Action {
    Ok(run(UserInfo.userInfo(username)))
  }

  private def run[A](reader: Reader[UserRepository, A]): JsValue = {
    Json.toJson(reader(userRepository))
  }
}
 */


object Application extends Application(UserRepositoryImpl)

/*
The object Application uses the default concrete implementation, and we can instantiate a test version using the class Application with a mock repository for testing. In this example we’ve also defined a convenience method run that injects the UserRepository into a Reader and converts the result to JSON.
 */


/*
Why not use the Reader Monad throughout our application’s core, and the cake pattern at the outer edge?

object Application extends Application with UserRepositoryComponentImpl

trait Application extends Controller with Users {
  this: UserRepositoryComponent =>

  def userEmail(id: Int) = Action {
    Ok(run(UserInfo.userEmail(id)))
  }

  def userInfo(username: String) = Action {
    Ok(run(UserInfo.userInfo(username)))
  }

  private def run[A](reader: Reader[UserRepository, A]): JsValue = {
    Json.toJson(reader(userRepository))
  }
}


This way we get the benefit of the cake pattern but we only have to apply it to our controllers. The Reader Monad lets us push the injection out to the edges of our application where it belongs.


Yeah, I had some suspicion that Reader is not as flexible as cake, especially when declaring the services offered by a Reader and then hooking up the offered services to another Reader that requests those services. How to do that? Might get complicated. Not really convinced. I am thinking about the function dependency graph and how the Reader clusters the nodes there ... and I have difficulty imagining how the Reader can declare the provided services, the edges pointing outwards from the clustered nodes: http://stackoverflow.com/qu... , so this suggests that the Reader offers a restricted way of clustering the dependency graph as opposed to the cake.

Il like the implicit parameters for simple DI cases.
If you want to avoid declaring implicit parameters on each method, you can use a class with a constructor level implicit parameter

You could do that, and in fact both the implicits and reader monad strategies are essentially doing just that. The problem is you then have to keep threading that extra argument through every call, cluttering up the code in the process. Both the implicits and reader monad strategies are ways of eliminating that. Implicits eliminate the need to pass the argument explicitly, and the reader monad further eliminates the need to explicitly put the parameter in the method signature.

'm trying to build an example app to test this out but I'm struggling as well to adapt it to a more real-world situation.

I often have to call web services, or use an asynchronous database layer. How do you deal with repositories that return Future[User] rather than User?

And how do we deal with multiple levels of this monadic DI? For example I have Repositories that access my database and return Futures of objects. Then I have a middle layer of Services that perform domain logic by interacting withseveral different Repositories... for example one service call will look up a user and his roles, returning both Future[User] and Future[Roles]. The top layer is my controllers which call the appropriate Service.

With Cake it's pretty straightforward to have a three-layer system that's passing around Futures. With Reader Monad I'm confused.

Just literally think of Reader as a synonym for Function1, with the type parameters being the input and the output.

You can still use Reader even with asynchronous services, you'll just need to 'unwrap' the Reader-wrapped code at some later point to get the Future value.
 */
