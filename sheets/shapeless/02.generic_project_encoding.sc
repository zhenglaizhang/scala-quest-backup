import shapeless.{ HNil, ::, HList }

/*
Shapeless gives us the best of both worlds: we can use friendly semanঞc types
by default and switch to generic representaঞons when we want interoperability
(more on this later). However, instead of using Tuples and Either, shapeless
uses its own data types to represent generic products and coproducts.

Unit as 0-length tuples??
The least upper bound of Unit and Tuple2 is Any so a combinaঞon of the two is impractical.


shapeless uses a different generic encoding for product
types called heterogeneous lists or HLists⁴(it's a `Product`)

An HList is either the empty list HNil, or a pair ::[H, T] where H is an arbitrary
type and T is another HList
 */

val product: String :: Int :: Boolean :: HNil = "Sunday" :: 1 :: false :: HNil

product.head
product.tail
product.tail.head
product.tail.tail

// The compiler knows the exact length of each HList,
product.length
// error on taking from empty list
//product.tail.tail.tail.head

val newp: Long :: String :: Int :: Boolean :: HNil = 32L :: product

// map, filter, or concatenate
