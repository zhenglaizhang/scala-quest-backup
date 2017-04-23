import scalaz._
import Scalaz._

1 == "1"
/*
Warning:(4, 4) comparing values of types Int and String using `==' will always yield false
1 == "1"
  ^
 */

//1 === "1"


1.some =/= 2.some
1.some =/= 1.some

//1 assert_=== 2
// RuntimeException

1 assert_=== 1
1.some /== 2.some

/*
Instead of the standard ==, Equal enables ===, =/=, and assert_=== syntax by declaring equal method. The main difference is that ===
would fail compilation if you tried to compare Int and String.

you should encourage people to use =/= and not /== since the latter has bad precedence.

Normally comparison operators like != have lower higher precedence than &&, all letters, etc. Due to special precedence rule /== is
recognized as an assignment operator because it ends with = and does not start with =, which drops to the bottom of the precedence:

scala> 1 != 2 && false
res4: Boolean = false

scala> 1 /== 2 && false
<console>:14: error: value && is not a member of Int
              1 /== 2 && false
                      ^

scala> 1 =/= 2 && false
res6: Boolean = false
 */


// Ord
/*
 Ord is for types that have an ordering. Ord covers all the standard comparing functions such as >, <, >= and <=.


Scalaz equivalent for the Ord typeclass is Order:


Order enables ?|? syntax which returns Ordering: LT, GT, and EQ. It also enables lt, gt, lte, gte, min, and max operators by declaring
order method. Similar to Equal, comparing Int and Doubl fails compilation.
  */
1 > 2.0
//1 gt 2.0
//Error:(19, 7) type mismatch;
// found   : Double(2.0)
// required: Int
//1 gt 2.0
//     ^
1.0 ?|? 2.0
1.0 max 2.0


/*

Scalaz equivalent for the Show typeclass is Show:


Cord apparently is a purely functional data structure for potentially long Strings.
 */
2.show
3.shows
"hello".println


/*
Enum members are sequentially ordered types — they can be enumerated. The main advantage of the Enum typeclass is that we can use its
types in list ranges. They also have defined successors and predecessors, which you can get with the succ and pred functions.

Scalaz equivalent for the Enum typeclass is Enum:

Instead of the standard to, Enum enables |-> that returns a List by declaring pred and succ method on top of Order typeclass. There are a
 bunch of other operations it enables like -+-, ---, from, fromStep, pred, predx, succ, succx, |-->, |->, |==>, and |=>. It seems like
 these are all about stepping forward or backward, and returning ranges.
 */
'a' to 'e'
'a' |-> 'e'
(3 |=> 5).toList
// TODO: EphemeralStream 
'B'.succ
'B'.pred




/*

Bounded members have an upper and a lower bound.

Scalaz equivalent for Bounded seems to be Enum as well.
 */
implicitly[Enum[Char]].min
implicitly[Enum[Char]].max

//implicitly[Enum[Double]].max
implicitly[Enum[Int]].max
implicitly[Enum[Boolean]].max
implicitly[Enum[Boolean]].min
// Enum typeclass instance returns Option[T] for max values.



