package com.esteban.scalaalgo.Algo.String

import scala.annotation.tailrec
object StringProblems:
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

  def anagram(sa: String, sb: String): Boolean =
    sa.sorted == sb.sorted

  type CharCount = Map[Char, Int]
  def countCharacter(s: String): CharCount =
    def optionLogic(key: Char, charCount: CharCount): CharCount =
      charCount.get(key) match
        case Some(value) => charCount + (key -> (value + 1))
        case None        => charCount + (key -> 1)

    @tailrec def tailCountRect(string: String, acc: CharCount): CharCount =
        if s.isEmpty then acc
        else tailCountRect(string.tail, optionLogic(s.head, acc))
    tailCountRect(s, Map.empty)


  def parenthesis(string: String): Boolean =
    def tailrec(remaining: String, open: Int): Boolean =
      if (remaining.isEmpty) then open == 0
      else if (open == 0 && remaining.head == ')') false
      else if (remaining.head == '(') tailrec(remaining.tail, open + 1)
      else tailrec(remaining.tail, open - 1)
    tailrec(string, 0)

@main def stringMain =
  println("-" * 50)
  // println(StringProblems.countCharacters("Scala"))
  // println(StringProblems.countCharacters("Esteban"))
//  println(StringProblems.anagram("stressed", "stressed"))
  println(StringProblems.countCharacter("E"))
  println("-" * 50)
