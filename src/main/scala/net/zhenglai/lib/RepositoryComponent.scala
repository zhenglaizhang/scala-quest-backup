package net.zhenglai.lib

case class User(name: String, age: Int)

trait RepositoryComponent {

  class UserRepository() {
    def authenticate(user: User): User = {
      println(s"authenticating user: $user")
      user
    }

    def create(user: User) = println(s"creating $user")

    def delete(user: User) = println(s"deleting: $user")
  }

}

