package com.esteban.scalaalgo

import scala.languageFeature.implicitConversions

object Reverse:
  implicit class RRichInter(number: Int):
    def reverse: String =
      number.toString.reverse
    def reverseToInt: Int =
      number.toString.reverse.toInt

@main def reverseMain =
  import Reverse.*
  println(14.reverseToInt)
  println(4.reverse)
