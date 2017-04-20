def head[A](xs: Seq[A]): A = xs(0)

head(List(1, 2))
head(1 :: 2 :: Nil)
