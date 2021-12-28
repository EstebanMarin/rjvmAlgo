package com.esteban.scalaalgo.Numbers

import scala.annotation.tailrec

object ListMethods:
  implicit class LRich(list: List[Char]):
    def newString: String =
      "1234"

// if (remaining.isEmpty) acc.newString.toInt

object StringMethods:
  import ListMethods.*

  implicit class RString(string: String):
    def parseInteger: Int =
      val list: List[Char] = string.toList
      @tailrec
      def parseTailRec(remaining: List[Char], acc: List[Char]): Int =
        if (remaining.isEmpty) acc.reverse.mkString.toInt
        else
          parseTailRec(remaining.tail, if (remaining.head.isDigit) remaining.head :: acc else acc)
      parseTailRec(list, List())

@main def parseMain =
  import StringMethods.*

  println("-" * 50)
  println(" .   +12344567 is the the number I want".parseInteger)
  println("-" * 50)
