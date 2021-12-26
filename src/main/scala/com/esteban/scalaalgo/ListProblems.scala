package com.esteban.scalaalgo

import scala.annotation.tailrec

sealed abstract class RList[+T]:
  def head: T
  def tail: RList[T]
  val isEmpty: Boolean
  def ::[S >: T](that: S): RList[S] = new ::(that, this)

case object RNill extends RList[Nothing]:
  def head = throw new NoSuchElementException
  def tail = throw new NoSuchElementException
  val isEmpty = true
  override def toString: String = "[]"

case class ::[+T](override val head: T, override val tail: RList[T]) extends RList[T]:
  override val isEmpty = false
  override def toString: String =
    @tailrec
    def toStringTail(remaining: RList[T], result: String): String =
      if (remaining.isEmpty) result
      else if (remaining.tail.isEmpty) s"$result${remaining.head}"
      else toStringTail(remaining.tail, s"${result}${remaining.head},  ")

    "[" + toStringTail(this, "") + "]"

@main def firstMain =
  val nList = 1 :: 2 :: 3 :: RNill
  println(nList)

object ListProblems
