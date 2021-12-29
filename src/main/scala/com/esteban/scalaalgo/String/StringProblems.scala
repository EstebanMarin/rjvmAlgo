package com.esteban.scalaalgo.String

import scala.annotation.tailrec
def countCharacters(s: String): Map[Char, Int] =
  val sList: List[Char] = s.toList
  def logic(element: Char, acc: Map[Char, Int]): Map[Char, Int] =
    if (acc.contains(element)) acc + (element -> (acc.apply(element) + 1))
    else acc + (element -> 1)
  @tailrec
  def countTailRec(remaining: List[Char], acc: Map[Char, Int]): Map[Char, Int] =
    if (remaining.isEmpty) acc
    else countTailRec(remaining.tail, logic(remaining.head, acc))
  countTailRec(sList, Map())

@main def stringMain =
  println("-" * 50)
  println(StringProblems.countCharacters("Scala"))
  println(StringProblems.countCharacters("Esteban"))
  println("-" * 50)
