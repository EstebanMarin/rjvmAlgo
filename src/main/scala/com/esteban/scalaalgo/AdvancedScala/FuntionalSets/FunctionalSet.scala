package com.esteban.scalaalgo.AdvancedScala.FuntionalSets

import javax.swing.text.html.FormSubmitEvent
import scala.annotation.tailrec

abstract class FSet[A] extends (A => Boolean):
  def contains(elem: A): Boolean
  def apply(elem: A): Boolean = contains(elem)
  infix def +(elem: A): FSet[A]
  infix def ++(elem: FSet[A]): FSet[A]
  def map[B](f: A => B): FSet[B]
  def flatMap[B](f: A => FSet[B]): FSet[B]
  def filter(predicate: A => Boolean): FSet[A]
  def foreach(f: A => Unit): Unit

case class Empty[A]() extends FSet[A]:
  override def contains(elem: A): Boolean = false
  infix override def +(elem: A): FSet[A] = Cons(elem, Empty())
  infix override def ++(elem: FSet[A]): FSet[A] = elem
  override def map[B](f: A => B): FSet[B] = Empty()
  override def flatMap[B](f: A => FSet[B]): FSet[B] = Empty()
  override def filter(predicate: A => Boolean): FSet[A] = this
  override def foreach(f: A => Unit): Unit = ()

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

object FSet:
  def apply[A](values: A*): FSet[A] =
    @tailrec
    def buildSet(valueSeq: Seq[A], acc: FSet[A]): FSet[A] =
      if (valueSeq.isEmpty) acc
      else buildSet(valueSeq.tail, acc + valueSeq.head)
    buildSet(values, Empty())

object FunctionalSet:
  @main def partialMain =
    println("-" * 50)
    val first5 = FSet(1, 2, 3, 4, 5)
    println(first5.contains(2))
    println("-" * 50)
