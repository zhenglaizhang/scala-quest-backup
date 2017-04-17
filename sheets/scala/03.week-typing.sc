def str(sizes: Seq[(Int, Int)]): Seq[String] = sizes.map {
  case (height, width) => s"$height × $width"
  // height & width swapped, spot the problem!
}

case class Size(
  width: Int,
  height: Int
)

/*
Types are our friends. They make sure we don’t make stupid
mistakes, they document code and help with testing
(auto-generating).

Illegal state should be impossible to create,


Generated Classes:
Debatable: direct using of generated classes, mainly Protobuf,
is not safe. Protobuf is an interchange format, not a
representation of business domain. It allows to create illegal
state, encourages dangerous programming, offers suboptimal
implementation (collections), poor extensibility and may have
problems with compatibility.
Proposal: translate Protobuf objects to real business domain
objects right after parsing and only use real business domain
objects for business logic. Use Protobuf only for interchange
between systems.
 */


