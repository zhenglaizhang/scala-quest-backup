
val s: Seq[Int] = Seq(1, 2, 3)
//   ↑ type       ↑ companion object

//type Seq[A] = scala.collection.Seq[A]
//val Seq = scala.collection.Seq
/* They are NOT
type Seq[A] = scala.collection.immutable.Seq[A]
val Seq = scala.collection.immutable.Seq


Seq represents a sequence.
Mutable or immutable? Does not say, can be both.
What performance characteristics? Does not say

Seq is an abstraction of sequence that is hard to reason about
in performance and mutability.
It should be preferred to use collection directly (most likely
List or Vector) depending on what performance
characteristics are required (random access, sequential
access, ...)
*/


// TODO: wow
//val listAsRuntimeType: Seq[Int] = Seq(1, 2, 3)


/*
List have a significant memory overhead.

How many objects are created on the heap!!!????
 */

List.range(0, 100000)
  .filter(_ % 2 == 0)
  .map(_ + 1)
// scala.collection.immutable.:: 200 000, 3 200 000 B

Vector.range(0, 100000)
  .filter(_ % 2 == 0)
  .map(_ + 1)
// scala.collection.immutable.Vector 3, 147 B

Iterator.range(0, 100000)
  .filter(_ % 2 == 0)
  .map(_ + 1)
// scala.collection.Iterator$$anon 3, 48 B

/*
 List and Vector create new collection on every combinator
(map, filter etc.).

CPU overhead:
List      3501 ms
Vector    3030 ms
Array     2614 ms
Iterator  1506 ms
  */

/*
List for sequential traversal, head/tail.
Iterator for combinators, sequential traversal, no reuse.
Array for sequential traversal, random access, not in API.
Vector otherwise.


Know the API you use (e.g. filter + map = collect).
Do not use .toSeq – you never know what it returns.
Do benchmarks. In many cases, traversing array is much faster
than lookup in map/set, for example.
 */
