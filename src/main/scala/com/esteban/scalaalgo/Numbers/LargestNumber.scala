package com.esteban.scalaalgo

object NumOps:
  implicit class RRichList(numbers: List[Int]):
    /*
    List(10, 2) => "210"
    List(3, 30, 5, 9, 34) => "9534330"
     */
    def largestNumber: String =
      given newOrdering: Ordering[Int] = Ordering.fromLessThan { (a, b) =>
        val aS = a.toString
        val bS = b.toString
        (aS + aS).compare(bS + aS) >= 0
      }
      val largest = numbers.sorted.mkString("")
      if (numbers.isEmpty || largest.charAt(0) == '0') "0"
      else largest

object LargestNumber:
  /*
    List(10, 2) => "210"
    List(3, 30, 5, 9, 34) => "9534330"
   */
  def largestNumber(numbers: List[Int]): String =
    given newOrdering: Ordering[Int] = Ordering.fromLessThan { (a, b) =>
      val aS = a.toString
      val bS = b.toString
      (aS + aS).compare(bS + aS) >= 0
    }
    val largest = numbers.sorted.mkString("")
    if (numbers.isEmpty || largest.charAt(0) == '0') "0"
    else largest

def largestMain =
  import NumOps.*
  val test = List(3, 30, 5, 9, 34, 40, 4, 41, 4211, 4311)
  println(LargestNumber.largestNumber(test))
  println(test.largestNumber)
