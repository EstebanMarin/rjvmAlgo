package com.esteban.scalaalgo

import scala.annotation.tailrec
import scala.collection.Iterable

sealed abstract class RList[+T]:
  def head: T
  def tail: RList[T]
  val isEmpty: Boolean
  def ::[S >: T](that: S): RList[S] = new ::(that, this)
  def apply(index: Int): T
  def length: Int
  def reverse: RList[T]
  def ++[S >: T](anotherList: RList[S]): RList[S]

object RList:
  def from[T](iterable: Iterable[T]): RList[T] =
    def convertToRList(remaining: Iterable[T], acc: RList[T]): RList[T] =
      if (remaining.isEmpty) acc
      else convertToRList(remaining.tail, remaining.head :: acc)
    convertToRList(iterable, RNill).reverse

case object RNill extends RList[Nothing]:
  def head = throw new NoSuchElementException
  def tail = throw new NoSuchElementException
  val isEmpty = true
  override def toString: String = "[]"
  override def apply(index: Int): Nothing = throw new NoSuchElementException
  override def length = 0
  override def reverse = throw new NoSuchElementException
  def ++[S >: Nothing](anotherList: RList[S]): RList[S] = anotherList

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
        reverseRec([2] :: [1] :: RNill, [3])
        reverRec([3]::[2]::[1]:: RNill, [])
     */
    @tailrec
    def reverseRec(result: RList[T], remaining: RList[T]): RList[T] =
      if (remaining.isEmpty) result
      else reverseRec(remaining.head :: result, remaining.tail)
    reverseRec(head :: RNill, tail)

  // def ++[S >: T](anotherList: RList[S]): RList[S] = head :: (tail ++ anotherList) // this is not stack safe
  def ++[S >: T](anotherList: RList[S]): RList[S] =
    @tailrec
    def concantTailRec(remaining: RList[S], acc: RList[S]): RList[S] =
      if (remaining.isEmpty) acc
      else concantTailRec(remaining.tail, remaining.head :: acc)
    concantTailRec(this.reverse, anotherList)

@main def firstMain =
  val nList = 1 :: 2 :: 3 :: RNill
//   println(nList(2))
//   println(nList.length)
  println(nList.reverse)
  println(RList.from(1 to 10).reverse)

object ListProblems
