val m = Map(1 -> "one", 2 -> "two", 3 -> "three")

m map (_._2)
m map Function.tupled(_ -> _.length)

/*
 The magic of returning a fitting collection is the work of CanBuildFrom

 
 */


