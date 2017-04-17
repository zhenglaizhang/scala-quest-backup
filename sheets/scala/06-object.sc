/*
In Scala, keyword object can be used in two ways:
• Singleton instance of a class
• Namespace
As singleton instance is very rarely needed. As namespace is
very useful.


Singleton:
Should contain no mutable state. It decreases safety,
reasoning about code and testing.
If there is need for initialization, prefer normal class.


Caches:
Many caches implemented as singletons. Most of them are
used only in one place (typically an actor). Should be local
(can be more easily controlled and efficient).


Using object as namespace is very good practice. It can be
used for functions, type aliases, implicit conversions, pimping
etc.
Combination trait + object gives choice about how to use:
trait CoolFunctions {
  def coolFunction(in: Unit): Unit = ...
}
object CoolFunctions extends CoolFunctions
 */
