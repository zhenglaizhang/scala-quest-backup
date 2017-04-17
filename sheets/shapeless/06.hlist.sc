import shapeless.{ HList, HNil, :: }

val hlist = "foo" :: 42 :: java.util.UUID.randomUUID() :: HNil
// hlist: shapeless.::[String,shapeless.::[Int,shapeless.::[java.util.UUID,shapeless.HNil]]] = foo :: 42 ::
// 1647cec2-c9a0-4d95-a67f-266c2385f287 :: HNil
// a kind of cons list of types, with shapeless.:: as the ctor.

// infix notation for types
val hlist2: String :: Int :: java.util.UUID :: HNil = "foo" :: 42 :: java.util.UUID.randomUUID() :: HNil

// We can get its head or tail if and only if it is a cons
// but if it is HNil there is no such method to call :

hlist.head
hlist.tail


val hl1 = 1 :: "foo" :: true :: HNil
val hl2 = "foo" :: 2 :: true :: HNil

// pattern matching
def thirdIsTrue(hlist: HList) = hlist match {
  case (_: Int) :: (_: String) :: true :: HNil => true
  case _ :: _ :: true :: HNil => true
  case _ => false
}

thirdIsTrue(hl1)
thirdIsTrue(hl2)

