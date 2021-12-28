package com.esteban.scalaalgo

import scala.annotation.tailrec

object NumMath:
  def isPrime(n: Int): Boolean =
    /*
    isPrime(11) = true
    ip(2) = 11 % 2 != 0 && ip(3)
    ips(3) = 11 % 3 != 0 && ip(4)
    ips(4) = true

    isPrime(15) = ip(2)
    ip(2) = 15 % 2 != 0 && ip(3)
    ips(3) = 15 % 3 != 0  => false

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
