// Shapeless is a type class and dependent type based generic programming library for Scala.

/*
To me, Shapeless is a toolkit to leverage Scala's type system at your own profit. You may use it to have more "precise" types, like statically sized list (lists which size is known at compile time), you may also use HList as a better tuple.

More generally, Shapeless can be used to make the compiler work for you, scrape some boilerplate, and gain a little extra typesafety.

 */

case class Employee(
  name: String,
  number: Int,
  manager: Boolean
)

case class IceCream(
  name: String,
  numCherries: Int,
  inCone: Boolean
)


// serializing to a CSV file

def employeeCsv(e: Employee) = List(e.name, e.number.toString, e.manager.toString)

def iceCreamCsv(c: IceCream) = List(c.name, c.numCherries.toString, c.inCone.toString)

// Generic programming is about overcoming differences like these. Shapeless
//makes it convenient to convert specific types into generic ones that we can
//manipulate with common code.

import scala.collection.generic

import shapeless._


/*
Shapeless provides a type class called Generic that allows us to switch back
and forth between a concrete ADT and its generic representaঞon. Some
behind-the-scenes macro magic allows us to summon instances of Generic
without boilerplate
 */

val genericEmployee = Generic[Employee].to(Employee("Dave", 123, false))
val genericIceCream = Generic[IceCream].to(IceCream("Sundae", 1, false))

//Both values are now of the same type. They are both heterogeneous lists
//(HLists for short) containing a String, an Int, and a Boolean.

def genericCsv(gen: String :: Int :: Boolean :: HNil): List[String] = List(gen(0).toString, gen(1).toString, gen(2).toString)

genericCsv(genericEmployee)
genericCsv(genericIceCream)

// We reformulate problems so we can solve them using generic building blocks, and
//write small kernels of code that work with a wide variety of types. Generic programming
//with shapeless allows us to eliminate huge amounts of boilerplate, making Scala applicaঞons easier to read, write, and maintain.

val iceCreamGen = Generic[IceCream]
/*
Note that the instance of Generic has a type member Repr containing the type of its generic representaঞon. In this case iceCreamGen.Repr is String :: Int :: Boolean :: HNil.
 */

// If two ADTs have the same Repr, we can convert back and forth between them using their Generics
//val repr = iceCreamGen.to(iceCreamGen)
//val iceCream2 = iceCreamGen.from(repr)

val iceCream = IceCream("wow", 1, false)
val employee = Generic[Employee].from(Generic[IceCream].to(iceCream))



// Scala tuples are actually case classes

val tupleGen = Generic[(String, Int, Boolean)]
tupleGen.to(("Hello", 123, true))
tupleGen.from("Hello" :: 123 :: true :: HNil)

// work with case classes of more than 22 fields
case class BigData(
  a:Int,b:Int,c:Int,d:Int,e:Int,f:Int,g:Int,h:Int,i:Int,j:Int,
  k:Int,l:Int,m:Int,n:Int,o:Int,p:Int,q:Int,r:Int,s:Int,t:Int,
  u:Int,v:Int,w:Int)
Generic[BigData].from(Generic[BigData].to(BigData(
  1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23)))
