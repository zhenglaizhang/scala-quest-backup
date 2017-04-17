import shapeless.{ HNil, the }
/*
We encode type class in Scala using traits and implicits. A type class is a parameterised trait represenঞng some sort of general
funcঞonality that we would like to apply to a wide range of types:
 */

trait CsvEncoder[A] {
  def encode(value: A): List[String]
}

case class Employee(
  name: String,
  number: Int,
  manager: Boolean
)

implicit val employeeEncoder: CsvEncoder[Employee] = new CsvEncoder[Employee] {
  override def encode(e: Employee): List[String] = List(
    e.name,
    e.number.toString,
    if (e.manager) "yes" else "no"
  )
}

/*
We mark each instance with the keyword implicit, and define one or more
entry point methods that accept an implicit parameter of the corresponding
type:
 */
def writeCsvOld[A](values: List[A])(implicit enc: CsvEncoder[A]): String =
  values.map(v => enc.encode(v).mkString(",")).mkString("\n")

def writeCsv[A: CsvEncoder](values: List[A]): String =
  values.map(v => implicitly[CsvEncoder[A]].encode(v).mkString(",")).mkString("\n")

val employees: List[Employee] = List(
  Employee("Bill", 1, true),
  Employee("Peter", 2, false),
  Employee("Milton", 3, false)
)
/*
When we call writeCsv, the compiler calculates the value of the type parameter
and searches for an implicit CsvEncoder of the corresponding type:
 */
writeCsv(employees)


case class IceCream(name: String, numCherries: Int, inCone: Boolean)

implicit val iceCreamEncoder: CsvEncoder[IceCream] =
  new CsvEncoder[IceCream] {
    def encode(i: IceCream): List[String] =
      List(
        i.name,
        i.numCherries.toString,
        if (i.inCone) "yes" else "no"
      )
  }
val iceCreams: List[IceCream] = List(
  IceCream("Sundae", 1, false),
  IceCream("Cornetto", 0, true),
  IceCream("Banana Split", 0, false)
)
writeCsv(iceCreams)


// Type classes are very flexible but they require us to define instances for every type we care about.
// Fortunately, the Scala compiler has a few tricks up its sleeve to resolve instances for us given sets of user-defined rules. For
// example, we can write a rule that creates a CsvEncoder for (A, B) given CsvEncoders for A and B:

implicit def pairEncoder[A, B](
  implicit
  aEncoder: CsvEncoder[A],
  bEncoder: CsvEncoder[B]
): CsvEncoder[(A, B)] = new CsvEncoder[(A, B)] {
  override def encode(pair: (A, B)): List[String] = {
    val (a, b) = pair
    aEncoder.encode(a) ++ bEncoder.encode(b)
  }
}
/*
When all the parameters to an implicit def are themselves marked as
implicit, the compiler can use it as a resoluঞon rule to create instances from other instances.

Given a set of rules encoded as implicit vals and implicit defs, the compiler
is capable of searching for combinaঞons to give it the required instances.
This behaviour, known as “implicit resoluঞon”, is what makes the type class
paern so powerful in Scala.
 */

writeCsv(employees zip iceCreams)


// Idiomaঞc type class definiঞons
object CsvEncoder {
  // "Summoner" method (“materializer”)
  // summon a type class instance given a target type
  def apply[A](implicit enc: CsvEncoder[A]): CsvEncoder[A] = enc

  // "Constructor" method
  // The instance method, someঞmes named pure, provides a terse syntax for creating new type class instances, reducing the boilerplate of
  // anonymous class syntax:
  def instance[A](func: A => List[String]): CsvEncoder[A] = new CsvEncoder[A] {
    override def encode(value: A) = func(value)
  }

  // Globally visible type class instances
}

CsvEncoder[IceCream]
implicitly[CsvEncoder[IceCream]]
/*
when working with shapeless we encounter
situaঞons where implicitly doesn’t infer types correctly. We can
always define the summoner method to do the right thing, so it’s worth writing
one for every type class we create. We can also use a special method from
shapeless called “the”
 */

the[CsvEncoder[IceCream]]

//implicit val booleanEncoder: CsvEncoder[Boolean] = new CsvEncoder[Boolean] {
//  override def encode(value: Boolean) = if (b) List("yes") else List("no")
//}


def createEncoder[A](func: A => List[String]): CsvEncoder[A] = new CsvEncoder[A] {
  override def encode(value: A) = func(value)
}

implicit val stringEncoder: CsvEncoder[String] = createEncoder(str => List(str))

implicit val intEncoder: CsvEncoder[Int] = createEncoder(num => List(num.toString))

implicit val booleanEncoder: CsvEncoder[Boolean] = CsvEncoder.instance(b => if (b) List("yes") else List("no"))

import shapeless._
implicit val hnilEncoder: CsvEncoder[HNil] = createEncoder(hnil => HNil)
// TODO: wow
