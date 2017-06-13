/*
A table is an abstraction, so is a chair, and so is a house. I hope you get the drift.
We want to be selectively ignorant of the details at times, and selective ignorance
is abstraction.
Now, you may ask why does it matter to us programmers? The reason is that it gets
things done in a compact manner.
 */

// Type Less Do More philosophy
"hello world".toList.filter(_.isLetter).groupBy(identity).map { y => y._1 -> y._2.size }


/*
A pure function depends only on its input to compute the output.
  1) side effects free
  2) referentially transparent
If the function is a long running one, we could call it just once and cache the results.
The cached results would be used for the second and subsequent calls.

Immutable

*/

// TODO: recursion!!



