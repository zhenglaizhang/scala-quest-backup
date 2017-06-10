package net.zhenglai.lib.other

import net.zhenglai.lib.fun.converter.ScalaFuture

import scala.collection.JavaConverters.asJavaIterableConverter
import scala.concurrent.Future

// TODO: how to iterate over multiple completions?
// TODO: vim delete word start
// TODO: live template group
// TODO: postfix completions
// TODO: scala.meta annotation e.g. @main
// TODO: SBT Shell? `sbt-structure`, Gringotts, sbt-autoplugin, scredis?
// TODO: sbt community plugins

/**
  * 1.  2 shifts          => search everything
  * 2.  ctrl + n          => search class
  * 3.  ctrl + shift + n  => search file
  * 4.  ctrl + shift + a  => search action/option (search xxx, new scala class, start sbt shell, toggle presentation mode)
  * 5.  ctrl + e          => switcher
  * 6.  ctrl + shift + e  => recently edited files
  * 7.  ctrl + alt + o    => optimize imports
  * 8.  xxx + shift       => enable extension mode
  * 9.  ctlr + space      => completion (basic), invoke it second time (double compoletion) to look up implicits! asTwitter, asScala...
  * 10. ctlr + shift + space => smart completion (assignment, after `new` ...)
  * 11. ctrl + backspace  => delete a word start
  * 12. crrl + w  => extend selection
  * 13. alter + enter => show intentions/editings/inspections (desugar for loop, use name arguments, generate compaign object, pattern matching...)
  * 14. shift + f6 => renamin
  * 15. live templates => (main, priv, mkt, mkcc...)
  * 16. ctrl + alt + t => surround with for the selection (ctrl + w)
  * 17. postfix completions => "abc".prtln   abc.match
  * 18. ctrl + shift + p => implicit parameter
  * 18. ctrl + shift + q => choose implicit convention methods
 */

trait LazyWithIntellij {
  val fut: Future[String] = ???

  fut.asTwitter


  // inspection
  Seq(1)(0)
  Seq(1).find(_ == 1).nonEmpty
  Seq(1, 2, 3).find(_ == 2).getOrElse(null)
  Map.empty[String, String].map(_._1)


  for (i <- 1 to 6) { }

  def foo(name: String) = ???
  foo(name = "wow")


  sealed trait ABC {

  }

  final case object DEF extends ABC

  def abc: ABC = ???

  abc match {
    case DEF =>
  }



//  "abd" // switch out " by type "
  //  "abc".prtln


//  fut.map(identity)

  Seq(1, 2).asJava
}
