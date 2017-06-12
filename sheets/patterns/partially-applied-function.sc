case class Email(
  subject: String,
  text: String,
  sender: String,
  recipient: String
)

type EmailFilter = Email => Boolean

type IntPairPred = (Int, Int) => Boolean

def sizeConstraint(pred: IntPairPred, n: Int, email: Email) =
  pred(email.text.size, n)

val gt: IntPairPred = _ > _
val ge: IntPairPred = _ >= _
val lt: IntPairPred = _ < _
val le: IntPairPred = _ <= _
val eq: IntPairPred = _ == _

// you have to use the placeholder _ for all parameters not bound to an argument value
val minSize: (Int, Email) => Boolean = sizeConstraint(ge, _, _)
val maxSize: (Int, Email) => Boolean = sizeConstraint(le, _, _)
val constr20: (IntPairPred, Email) => Boolean = sizeConstraint(_, 20, _)


/*
When doing partial application on a method, you can also decide to not bind any parameters whatsoever.
The parameter list of the returned function object will be the same as for the method.
You have effectively turned a method into a function that can be assigned to a val
 or passed around

now `_` is redundant
 */

val sizeConstraintFn: (IntPairPred, Int, Email) => Boolean = sizeConstraint _

val min20: EmailFilter = minSize(20, _)
val max20: EmailFilter = maxSize(20, _)



// methods in Scala can have more than one parameter list
def sizeConstraint2(pred: IntPairPred)(n: Int)(email: Email): Boolean = pred(email.text.length, n)

val sizeConstraint2Fn: IntPairPred => Int => Email => Boolean = sizeConstraint2 _

// Such a chain of one-parameter functions is called a curried function

// There is no need to use any placeholders for parameters left blank,
// because we are in fact not doing any partial function application.
val minSize2: Int => Email => Boolean = sizeConstraint2(ge)
val min202: Email => Boolean = minSize2(20)
val min203: Email => Boolean = sizeConstraint2Fn(ge)(20)


val sum: (Int, Int) => Int = _ + _
val sumCurried: Int => Int => Int = sum.curried
val sum2: (Int, Int) => Int = Function.uncurried(sumCurried)
