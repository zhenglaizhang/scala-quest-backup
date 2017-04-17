/*
Shapeless provides a simple lenses implementation.


 */

import shapeless._

case class Address(street: String, city: String, postcode: String)

case class Person(name: String, age: Int, address: Address)

val ageLens = lens[Person].age

val person = Person("Joe Grey", 37, Address("Southover Street", "Brighton", "BN2 9UA"))

// read
val age1 = ageLens.get(person)
// TODO: wow
//typed[Int](age1)
assert(age1 == 37)

val person2 = ageLens.set(person)(38)
assert(person2.age == 38)

// TODO: https://github.com/julien-truffaut/Monocle
