package com.esteban.scalaalgo.Numbers

import scala.annotation.tailrec
object UglyNum:
  implicit class NRich(number: Int):
    def uglyNumber: Boolean =
      @tailrec
      def uglyTailRec(interation: Int, acc: List[Int]): Boolean =
        if (interation == number) acc.filter(number => !Set(2, 3, 5).contains(number)).isEmpty
        else uglyTailRec(interation + 1, if (number % interation == 0) interation :: acc else acc)
      if (number == 1) true else uglyTailRec(2, Nil)

object UglyNumber:
  def uglyNumber(number: Int): Boolean =
    @tailrec
    def uglyTailRec(interation: Int, acc: List[Int]): Boolean =
      if (interation == number) acc.filter(number => !Set(2, 3, 5).contains(number)).isEmpty
      else uglyTailRec(interation + 1, if (number % interation == 0) interation :: acc else acc)
    if (number == 1) true else uglyTailRec(2, Nil)

@main def uglyMain =
  import UglyNum.*

  println(UglyNumber.uglyNumber(6))
  println(UglyNumber.uglyNumber(14))
  println(6.uglyNumber)
