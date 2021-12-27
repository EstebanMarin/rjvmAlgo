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
  def remove(k: Int): RList[T]
  def map[S](f: T => S): RList[S]
  def filter(f: T => Boolean): RList[T]
  def flatMap[S](f: T => RList[S]): RList[S]

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
  def remove(k: Int): RList[Nothing] = throw new NoSuchElementException
  def map[S](f: Nothing => S): RList[S] = RNill
  def filter(f: Nothing => Boolean): RList[Nothing] = RNill
  def flatMap[S](f: Nothing => RList[S]): RList[S] = RNill

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
    @tailrec
    def reverseRec(result: RList[T], remaining: RList[T]): RList[T] =
      if (remaining.isEmpty) result
      else reverseRec(remaining.head :: result, remaining.tail)
    reverseRec(head :: RNill, tail)
  def ++[S >: T](anotherList: RList[S]): RList[S] =
    @tailrec
    def concantTailRec(remaining: RList[S], acc: RList[S]): RList[S] =
      if (remaining.isEmpty) acc
      else concantTailRec(remaining.tail, remaining.head :: acc)
    concantTailRec(this.reverse, anotherList)
  def remove(k: Int): RList[T] =
    @tailrec
    def removeTailRec(
        remaining: RList[T],
        acc: RList[T],
        iteration: Int,
      ): RList[T] =
      if (iteration == k) acc.tail.reverse ++ (remaining.head :: remaining.tail)
      else removeTailRec(remaining.tail, remaining.head :: acc, iteration + 1)
    removeTailRec(tail, head :: RNill, 0)
  def map[S](f: T => S): RList[S] =
    @tailrec
    def mapTailRec(acc: RList[S], remaining: RList[T]): RList[S] =
      if (remaining.isEmpty) acc
      else mapTailRec(f(remaining.head) :: acc, remaining.tail)
    mapTailRec(f(head) :: RNill, tail).reverse
  def filter(f: T => Boolean): RList[T] =
    def accumutateIf(
        head: T,
        acc: RList[T],
      ): RList[T] =
      if (f(head)) head :: acc else acc
    @tailrec
    def filterTailRec(acc: RList[T], remaining: RList[T]): RList[T] =
      if (remaining.isEmpty) acc
      else filterTailRec(accumutateIf(remaining.head, acc), remaining.tail)
    filterTailRec(RNill, this).reverse

  def flatMap[S](f: T => RList[S]): RList[S] =
    ???

@main def firstMain =
  val nList = 1 :: 2 :: 3 :: RNill
  println("-" * 50)
//   println(nList(2))
//   println(nList.length)
  // println(nList.reverse)
  // println(RList.from(1 to 10).reverse)
  // println(RList.from(1 to 10).remove(6))
  // println(RList.from(1 to 10).map(x => x * 2))
  println(RList.from(1 to 10).filter(x => x == 2))
  println("-" * 50)

object ListProblems
