package com.esteban.scalaalgo

import scala.util.Random

object AproximatePi:
  val random = new Random(System.currentTimeMillis())
  def aproximatePi(n: Int): Double =
    val nPointsInside = (1 to n)
      .map { _ =>
        val x = random.nextDouble()
        val y = random.nextDouble()
        x * x + y * y
      }
      .count(distance => distance < 1)
    nPointsInside * 4.0 / n

  def pi =
    println(aproximatePi(1000000))
