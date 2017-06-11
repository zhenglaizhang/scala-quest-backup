/*

Foldable type class instances can be defined for data structures that can be folded to a summary value.

 Most collection types have foldLeft methods, which will usually be used by the associated Foldable[_] instance.


Foldable[F] is implemented in terms of two basic methods:
  1. foldLeft(fa, b)(f) eagerly folds fa from left-to-right.
  2. foldRight(fa, b)(f) lazily folds fa from right-to-left.

*/



