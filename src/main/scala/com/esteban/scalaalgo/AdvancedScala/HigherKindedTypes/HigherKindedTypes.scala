package com.esteban.scalaalgo.AdvancedScala.HigherKindedTypes

object HigherKindedTypes:
  class HKT[F[_]]
  class HTKV2[F[_], G[_], A]

  val hkExameple = new HKT[List]
  val seondExampl = new HTKV2[List, Option, Int]

  @main def HKmain =
    println("-" * 50)
