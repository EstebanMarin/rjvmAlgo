package com.esteban.scalaalgo.Numbers

object UglyNumber:
  /*
    only factors 2,3,5
   */
  def uglyNumber_V0(number: Int): Boolean =
    // List(2, 3, 5).filter(x => number % x == 0).isEmpty
    val test = List(2, 3, 5).filter(x => number % x == 0)
    println(test)
    test.isEmpty

  def uglyNumber(number: Int): Boolean =
    def logic(acc: List[Int]): Boolean =
      val set = Set(2, 3, 5)
      acc.filter(x => !set.contains(x)).isEmpty
    def uglyTailRec(interation: Int, acc: List[Int]): Boolean =
      if (interation == number) logic(acc)
      else uglyTailRec(interation + 1, if (number % interation == 0) interation :: acc else acc)
    uglyTailRec(2, Nil)

@main def uglyMain =
  println(UglyNumber.uglyNumber(6))
  println(UglyNumber.uglyNumber(14))
