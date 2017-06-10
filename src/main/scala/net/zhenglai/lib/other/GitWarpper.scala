package net.zhenglai.lib.other

import java.io.File

/**
  * 1. Code generation from doc/man
  * 2. Micro to capture arguments...
  */
object GitWarpper {
  def diff(quiet: Boolean = false, exitCode: Boolean = false): Int = {
    val parts = (
      Seq("git", "diff")
        ++ (if (quiet) Seq("--quiet") else Nil)
        ++ (if (exitCode) Seq("--exit-code") else Nil)
      )

    new ProcessBuilder(parts: _*)
      .directory(new File("."))
      .inheritIO()
      .start()
      .waitFor()
  }
}
