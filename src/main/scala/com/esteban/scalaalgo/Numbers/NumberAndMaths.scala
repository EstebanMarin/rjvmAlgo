package com.esteban.scalaalgo

import scala.annotation.tailrec
object NumberOps:
  implicit class RRichInt(n: Int):
    def isPrime: Boolean =
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
      if (n == 0 || n == 1 || n == -1) false else isPrimeTailRec(2)

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
    if (n == 0 || n == 1 || n == -1) false else isPrimeTailRec(2)
  /*
    decompose into its prime divisors
    decompose(10)
   */
  def decompose(n: Int): List[Int] =
    @tailrec
    def decomposeTailRec(current: Int, acc: List[Int]): List[Int] =
      if (current == n) acc
      else if (n % current == 0 && isPrime(current)) decomposeTailRec(current + 1, current :: acc)
      else decomposeTailRec(current + 1, acc)
    decomposeTailRec(1, Nil)

def numbers =
  import NumberOps.*
  println("-" * 50)
  println(NumMath.isPrime(2))
  println(2.isPrime)
  println(NumMath.decompose(15))
  println("-" * 50)
