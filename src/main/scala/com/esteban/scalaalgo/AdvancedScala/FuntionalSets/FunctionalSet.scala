package com.esteban.scalaalgo.AdvancedScala.FuntionalSets

import scala.annotation.tailrec
import javax.management.RuntimeErrorException

abstract class FSet[A] extends (A => Boolean):
  def contains(elem: A): Boolean
  def apply(elem: A): Boolean = contains(elem)
  infix def +(elem: A): FSet[A]
  infix def ++(another: FSet[A]): FSet[A]
  def map[B](f: A => B): FSet[B]
  def flatMap[B](f: A => FSet[B]): FSet[B]
  def filter(predicate: A => Boolean): FSet[A]
  def foreach(f: A => Unit): Unit
  infix def -(elem: A): FSet[A]
  infix def --(another: FSet[A]): FSet[A]
  infix def &(another: FSet[A]): FSet[A] // filtering
  def unary_! : FSet[A] = new PBSet(x => !contains(x))

class PBSet[A](property: A => Boolean) extends FSet[A]:
  def contains(elem: A): Boolean = property(elem)
  infix def +(elem: A): FSet[A] = new PBSet(x => x == elem || property(x))
  infix def ++(another: FSet[A]): FSet[A] = new PBSet(x => property(x) || another(x))
  def map[B](f: A => B): FSet[B] = throw new RuntimeException("not iterable")
  def flatMap[B](f: A => FSet[B]): FSet[B] = throw new RuntimeException("not iterable")
  def filter(predicate: A => Boolean): FSet[A] = new PBSet(x => property(x) && predicate(x))
  def foreach(f: A => Unit): Unit = throw new RuntimeException("not iterable")
  infix def -(elem: A): FSet[A] = filter(x => x != elem)
  infix def --(another: FSet[A]): FSet[A] = filter(!another)
  infix def &(another: FSet[A]): FSet[A] =
    filter(another)

case class Empty[A]() extends FSet[A]:
  override def contains(elem: A): Boolean = false
  infix override def +(elem: A): FSet[A] = Cons(elem, Empty())
  infix override def ++(elem: FSet[A]): FSet[A] = elem
  override def map[B](f: A => B): FSet[B] = Empty()
  override def flatMap[B](f: A => FSet[B]): FSet[B] = Empty()
  override def filter(predicate: A => Boolean): FSet[A] = this
  override def foreach(f: A => Unit): Unit = ()
  infix override def -(elem: A): FSet[A] = this
  infix override def --(another: FSet[A]): FSet[A] = this
  infix override def &(another: FSet[A]): FSet[A] = this

case class Cons[A](head: A, tail: FSet[A]) extends FSet[A]:
  // notice no order is preserved!!!!!
  override def contains(elem: A): Boolean = elem == head || tail.contains(elem)
  infix override def +(elem: A): FSet[A] =
    if contains(elem) then this else Cons(elem, this)
  // no order preservation no problem
  infix override def ++(elem: FSet[A]): FSet[A] = tail ++ elem + head
  override def map[B](f: A => B): FSet[B] = tail.map(f) + f(head)
  override def flatMap[B](f: A => FSet[B]): FSet[B] = f(head) ++ tail.flatMap(f)
  override def filter(predicate: A => Boolean): FSet[A] =
    if predicate(head) then tail.filter(predicate) + head
    else tail.filter(predicate)
  override def foreach(f: A => Unit): Unit =
    f(head)
    tail.foreach(f)
  infix override def -(elem: A): FSet[A] =
    if (head == elem) tail
    else tail - elem + head
  infix override def --(another: FSet[A]): FSet[A] = filter(!another)
  infix override def &(another: FSet[A]): FSet[A] = filter(another)

object FSet:
  def apply[A](values: A*): FSet[A] =
    @tailrec
    def buildSet(valueSeq: Seq[A], acc: FSet[A]): FSet[A] =
      if (valueSeq.isEmpty) acc
      else buildSet(valueSeq.tail, acc + valueSeq.head)
    buildSet(values, Empty())

def partialMain =
  println("-" * 50)
  val first5 = FSet(1, 2, 3, 4, 5)
  val someNumbers = FSet(4, 5, 6, 7, 8, 9)
  println(first5.contains(2))
  // set as a function to filter
  val aSet = Set(1, 2, 3, 4)
  val aList = (1 to 10).toList.filter(aSet)
  println(aList)
  println((first5 - 3).contains(3))
  println((first5 -- someNumbers).contains(4))
  println((first5 & someNumbers).contains(4))

  val naturals = new PBSet[Int](_ => true)
  println(naturals.contains(23452345))
  println("-" * 50)
