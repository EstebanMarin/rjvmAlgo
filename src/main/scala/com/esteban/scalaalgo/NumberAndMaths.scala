package com.esteban.scalaalgo

import scala.annotation.tailrec

object NumMath:
  def isPrime(n: Int): Boolean =
    /*
    isPrime(3) = 2 > 9 ? = 3
     */
    @tailrec
    def isPrimeTailRec(current: Int): Boolean =
      if (current > Math.sqrt(Math.abs(n))) true
      // sigue derecho si no es true
      else n % current != 0 && isPrimeTailRec(current + 1)
    // continua la iteracino
    isPrimeTailRec(2)

@main def numbers =
  println("-" * 50)
  println("in numbers")
  println(NumMath.isPrime(3))
  println("-" * 50)
