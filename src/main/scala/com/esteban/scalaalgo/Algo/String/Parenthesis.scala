package com.esteban.scalaalgo.Algo.String

import java.lang.reflect.Parameter

object Parenthesis:
  def hasValidP(string: String): Boolean =
    val stringList: List[Char] = string.toList
    def logic(head: Char, acc: Set[Char]): Set[Char] =
      if (acc.contains(head)) acc - head else acc + head
    def hasValidTailRec(string: List[Char], acc: Set[Char]): Boolean =
      if (string.isEmpty) !acc.isEmpty
      else hasValidTailRec(string.tail, logic(string.head, acc))
    hasValidTailRec(stringList, Set())

def paraMan =
  println("-" * 50)
  println(Parenthesis.hasValidP("()"))
  println(Parenthesis.hasValidP("(("))
  println("-" * 50)
