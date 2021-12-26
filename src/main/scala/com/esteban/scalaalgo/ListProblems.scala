package com.esteban.scalaalgo

import scala.annotation.tailrec

sealed abstract class RList[+T]:
  def head: T
  def tail: RList[T]
  val isEmpty: Boolean
  def ::[S >: T](that: S): RList[S] = new ::(that, this)
  def apply(index: Int): T
  def length: Int
  def reverse: RList[T]

case object RNill extends RList[Nothing]:
  def head = throw new NoSuchElementException
  def tail = throw new NoSuchElementException
  val isEmpty = true
  override def toString: String = "[]"
  override def apply(index: Int): Nothing = throw new NoSuchElementException
  override def length = 0
  override def reverse = throw new NoSuchElementException

case class ::[+T](override val head: T, override val tail: RList[T]) extends RList[T]:
  override val isEmpty = false
  override def apply(index: Int): T =
    @tailrec
    def recursiveSearch(iteration: Int, remaining: RList[T]): T =
      if (iteration == index) remaining.head
      else recursiveSearch(iteration + 1, remaining.tail)
    if (index < 0) throw new NoSuchElementException
    else recursiveSearch(0, this)
  override def length =
    @tailrec
    def getLenght(acc: Int, tail: RList[T]): Int =
      if (tail.isEmpty) acc
      else getLenght(acc + 1, tail.tail)
    getLenght(0, this)
  override def toString: String =
    @tailrec
    def toStringTail(remaining: RList[T], result: String): String =
      if (remaining.isEmpty) result
      else if (remaining.tail.isEmpty) s"$result${remaining.head}"
      else toStringTail(remaining.tail, s"${result}${remaining.head},  ")
    "[" + toStringTail(this, "") + "]"
  override def reverse =
    /*
    [1,2,3].reverse = reversRec([1] :: RNill,[2,3]) =
        reverseRec([2] :: [1] :: RNill)
     */
    @tailrec
    def reverseRec(result: RList[T], remaining: RList[T]): RList[T] =
      if (remaining.isEmpty) result
      else reverseRec(remaining.head :: result, remaining.tail)
    reverseRec(head :: RNill, tail)

@main def firstMain =
  val nList = 1 :: 2 :: 3 :: RNill
//   println(nList(2))
//   println(nList.length)
  println(nList.reverse)

object ListProblems
