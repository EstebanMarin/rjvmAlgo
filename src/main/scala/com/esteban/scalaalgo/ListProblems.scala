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
  // run -length - enconding
  def rle: RList[(T, Int)]
  def duplicateElements(k: Int): RList[T]

object RList:
  def from[T](iterable: Iterable[T]): RList[T] =
    def convertToRList(remaining: Iterable[T], acc: RList[T]): RList[T] =
      if (remaining.isEmpty) acc
      else convertToRList(remaining.tail, remaining.head :: acc)
    convertToRList(iterable, RNill).reverse

case object RNill extends RList[Nothing]:
  override def head = throw new NoSuchElementException
  override def tail = throw new NoSuchElementException
  val isEmpty = true
  override def toString: String = "[]"
  override def apply(index: Int): Nothing = throw new NoSuchElementException
  override def length = 0
  override def reverse = throw new NoSuchElementException
  override def ++[S >: Nothing](anotherList: RList[S]): RList[S] = anotherList
  override def remove(k: Int): RList[Nothing] = throw new NoSuchElementException
  override def map[S](f: Nothing => S): RList[S] = RNill
  override def filter(f: Nothing => Boolean): RList[Nothing] = RNill
  override def flatMap[S](f: Nothing => RList[S]): RList[S] = RNill
  override def rle: RList[(Nothing, Int)] = throw new NoSuchElementException
  override def duplicateElements(k: Int): RList[Nothing] = ???

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
  override def ++[S >: T](anotherList: RList[S]): RList[S] =
    @tailrec
    def concantTailRec(remaining: RList[S], acc: RList[S]): RList[S] =
      if (remaining.isEmpty) acc
      else concantTailRec(remaining.tail, remaining.head :: acc)
    concantTailRec(this.reverse, anotherList)
  override def remove(k: Int): RList[T] =
    @tailrec
    def removeTailRec(
        remaining: RList[T],
        acc: RList[T],
        iteration: Int,
      ): RList[T] =
      if (iteration == k) acc.tail.reverse ++ (remaining.head :: remaining.tail)
      else removeTailRec(remaining.tail, remaining.head :: acc, iteration + 1)
    removeTailRec(tail, head :: RNill, 0)
  override def map[S](f: T => S): RList[S] =
    @tailrec
    def mapTailRec(acc: RList[S], remaining: RList[T]): RList[S] =
      if (remaining.isEmpty) acc.reverse
      else mapTailRec(f(remaining.head) :: acc, remaining.tail)
    mapTailRec(RNill, this)
  override def filter(f: T => Boolean): RList[T] =
    @tailrec
    def filterTailRec(acc: RList[T], remaining: RList[T]): RList[T] =
      if (remaining.isEmpty) acc.reverse
      else filterTailRec(if (f(remaining.head)) remaining.head :: acc else acc, remaining.tail)
    filterTailRec(RNill, this)
  override def flatMap[S](f: T => RList[S]): RList[S] =
    def flatMapTailRec(acc: RList[S], remaining: RList[T]): RList[S] =
      if (remaining.isEmpty) acc.reverse
      else flatMapTailRec(f(remaining.head).reverse ++ acc, remaining.tail)
    flatMapTailRec(RNill, this)
  override def rle: RList[(T, Int)] =
    @tailrec
    def rleTail(
        acc: RList[(T, Int)],
        currentTuple: (T, Int),
        remaining: RList[T],
      ): RList[(T, Int)] =
      if (remaining.isEmpty && currentTuple._2 == 0) acc
      else if (remaining.isEmpty) currentTuple :: acc
      else if (remaining.head == currentTuple._1)
        rleTail(acc, currentTuple.copy(_2 = currentTuple._2 + 1), remaining.tail)
      else rleTail(currentTuple :: acc, (remaining.head, 1), remaining.tail)
    rleTail(RNill, (head, 1), tail).reverse
  override def duplicateElements(k: Int): RList[T] =
    def duplicateEach(head: T): RList[T] =
      @tailrec
      def duplicateEachTailRec(iteration: Int, acc: RList[T]): RList[T] =
        if (iteration == k) acc
        else duplicateEachTailRec(iteration + 1, head :: acc)
      duplicateEachTailRec(0, RNill)
    @tailrec
    def duplicateTailRec(acc: RList[T], remaining: RList[T]): RList[T] =
      if (remaining.isEmpty) acc.reverse
      else duplicateTailRec(duplicateEach(remaining.head) ++ acc, remaining.tail)
    duplicateTailRec(RNill, this)

@main def firstMain =
  println("-" * 50)
  // println(nList(2))
  // println(nList.length)
  // println(nList.reverse)
  // println(RList.from(1 to 10).reverse)
  // println(RList.from(1 to 10).remove(6))
  // println(RList.from(1 to 10).map(x => x * 2))
  // println(RList.from(1 to 10).filter(_ == 2))
  // println(RList.from(1 to 10).flatMap(x => x :: (2 * x) :: RNill))
  // println((1 :: 1 :: 1 :: 2 :: 2 :: 3 :: 3 :: 3 :: 4 :: 5 :: RNill).rle)
  println((1 :: 2 :: 3 :: 0 :: RNill).duplicateElements(3))
  println("-" * 50)

object ListProblems
