// Giving explicit types to every elements of the classes

case class UntypedUser(lastName: String, firstName: String, age: Int)

case class User(lastName: User.LastName, firstName: User.LastName, age: User.Age)

object User {

  case class Id(value: Long) extends AnyVal

  case class FirstName(value: String) extends AnyVal

  case class LastName(value: String) extends AnyVal

  case class Age(value: Int) extends AnyVal


  // extra type safety
  def getUser(id: User.Id): Option[User] = ???

  // documentation
  def all(): List[(User.Id, User)] = ???
}
/*
cons:
Not only am I adding boilerplate on the definition site, but you'd expect it to also make it harder to create new instances of User
 */

/*
pros:

Extra safety
you won't fear to change function parameters anymore. Nothing will break, the compiler guarantees it. This simple thing makes your program
 significantly more robust, especially as your codebase grows larger.


Documentation
The best thing is this doc is always up to date! Moreover, types propagate inside your code, which mean everything necessarily have at least a basic doc.


Make the compiler work for you



Manage resources automatically
ExecutionContext confused usage
 */


// TODO: http://jto.github.io/articles/type-all-the-things/
