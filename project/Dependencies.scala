import sbt._

object Dependencies {
  case object org {
    case object cats {
      val cats =
        "org.typelevel" %% "cats-core" % "2.7.0"

      val catsEffect =
        "org.typelevel" %% "cats-effect" % "3.3.9"
    }

    case object scalatest {
      val scalatest =
        "org.scalatest" %% "scalatest" % "3.2.10"
    }

    case object scalatestplus {
      val `scalacheck-1-15` =
        "org.scalatestplus" %% "scalacheck-1-15" % "3.2.10.0"
    }
  }
}
