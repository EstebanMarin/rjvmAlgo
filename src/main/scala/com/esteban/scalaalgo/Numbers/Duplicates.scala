package com.esteban.scalaalgo.Numbers

object Duplicates:
  def duplictes(list: List[Int]): Int =
    def addToSet(acc: Set[Int], head: Int): Set[Int] =
      if (acc.contains(head)) acc - head else acc + head
    def duplicatesTailRec(remaing: List[Int], acc: Set[Int]): Int =
      if (remaing.isEmpty) acc.toList(0)
      else duplicatesTailRec(remaing.tail, addToSet(acc, remaing.head))
    duplicatesTailRec(list, Set())

@main def duplicateMain =
  println("-" * 50)
  println("Hello from duplicate")
  Duplicates.duplictes(List(1, 1, 3, 3, 4, 4, 5))
  println("-" * 50)
