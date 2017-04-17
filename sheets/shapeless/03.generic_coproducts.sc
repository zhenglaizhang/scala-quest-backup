import shapeless.{ :+:, CNil, Generic, Inl, Inr }

/*
Now we know how shapeless encodes product types. What about coproducts?
We looked at Either earlier but that suffers from similar drawbacks to
tuples

The overall type of a coproduct encodes all the possible types in the disjuncঞon, but each concrete instance contains a value for just
one of the possibiliঞes. :+: has two subtypes, Inl and Inr,
 */

case class Red()

case class Amber()

case class Green()

type Light = Red :+: Amber :+: Green :+: CNil

val red: Light = Inl(Red())

val green: Light = Inr(Inr(Inl(Green())))

/*
Every coproduct type is terminated with CNil, which is an empty type with no values, similar to Nothing. We can’t instanঞate CNil or
build a Coproduct
purely from instances of Inr. We always have exactly one Inl in a value.
 */

// TODO: wow
sealed trait Shape

final case class Rectangle(width: Double, height: Double) extends Shape

final case class Circle(radius: Double) extends Shape

val gen = Generic[Shape]
// The Repr of the Generic for Shape is a Coproduct of the subtypes of the sealed trait
// Repr: Rectangle :+: Circle :+: CNil
gen.to(Rectangle(3.0, 4.0))
//  gen.Repr = Inl(Rectangle(3.0,4.0))
gen.to(Circle(1.0))
//  gen.Repr = Inr(Inl(Circle(1.0)))
