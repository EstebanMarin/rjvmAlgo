package com.esteban.scalaalgo.Numbers

import scala.annotation.tailrec

object UglyNumber:
  def uglyNumber(number: Int): Boolean =
    def logic(acc: List[Int]): Boolean =
      val set = Set(2, 3, 5)
      acc.filter(x => !set.contains(x)).isEmpty
    @tailrec
    def uglyTailRec(interation: Int, acc: List[Int]): Boolean =
      if (interation == number) logic(acc)
      else uglyTailRec(interation + 1, if (number % interation == 0) interation :: acc else acc)
    if (number == 1) true else uglyTailRec(2, Nil)

@main def uglyMain =
  println(UglyNumber.uglyNumber(6))
  println(UglyNumber.uglyNumber(14))
