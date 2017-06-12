// mark the primary constructor as “private”
class Product private(var state: Int) {
  def doSomething() = {
    state += 1
    println(s"I did something: $state")
    ()
  }
}

// JVM’s static-initializer facilities

// Scala generates an auxiliary class, “Product$”,
// which holds a static initializer that immediately turns around and constructs an instance of “Product$”,
object Product {
  private val _instance = new Product(0)
  def instance(): Product = _instance
}

/*
 Scala deliberately chooses to syntactically try to “hide” the differences
 in syntax between property, method, and field
 */

Product.instance().doSomething
Product.instance().doSomething
Product.instance().doSomething
Product.instance().doSomething
