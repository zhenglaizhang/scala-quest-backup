/*
A function is a mapping from one set, called a domain, to another set, called the codomain. A function associates every element in the domain with exactly one element in the codomain. In Scala, both domain and codomain are types.
 */

val square: Int => Int = x => x * x

square(2)

/*

A higher-order function is a function that accepts or returns a function.

 */
trait List[A] {
  def filter(f: A => Boolean): List[A]
}

/*

Function combinators are higher-order functions that accept and return functions.

 */

class ConfigReader

type Conf[A] = ConfigReader => A

def string(name: String): Conf[String] = _.readstring(name)

def both[A, B](left: Conf[A], right: Conf[B]): Conf[(A, B)] = c => (left(c), right(c))