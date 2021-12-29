package com.esteban.scalaalgo.AdvancedScala.parternMatching

import com.esteban.scalaalgo.parternMatching.ParternMatching.Person

object Partial:
  // partial function
  val aPartialFuntion: PartialFunction[Int, Int] =
    case 1 => 42
    case 2 => 3
    case 5 => 999

  val anotherPartial: PartialFunction[Int, Int] =
    case 34 => 77

//   val aPi = aPartialFuntion.lift

  // val pfChain = aPartialFuntion.orElse[Int, Int](anotherPartial)

  // HOF accepts PFs as arguments
  val aList = List(1, 2, 3, 4, 5)
  val achangedList = aList.map {
    case 1 => 4
    case _ => 5
  }

  val someKids: List[Person] = List(
    Person("e", 1),
    Person("t", 1),
    Person("h", 1),
    Person("r", 1),
  )

  val kidsPlus1: List[Person] = someKids.map {
    case Person(name, age) => Person(name, age + 1)
  }
