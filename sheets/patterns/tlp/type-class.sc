/*
Type Classes, it is the way Polymorphism is implemented in Haskell, we can encode them in Scala as well, the most common way to do it is basically what we did before, an abstract type that takes one type parameter
`trait Printer[T] { ... }` and different implicit implementations in the companion object

  1) `Simulacrum` The main goal is to avoid encoding inconsistencies using macros
  2) `Scato` The main goal is to avoid inheritance to encode the hierarchy of the classes using instead natural transformations
 */

