package com.esteban.scalaalgo.AdvancedScala.ExtensionMethods

import scala.annotation.tailrec
import scala.util.CommandLineParser.FromString.given_FromString_Int

object ExtensionMethods:
  case class Person(name: String):
    def greet: String = s"Hello my name is $name, nice to meet you"

  extension (string: String) def greetAsPerson: String = Person(string).greet

  extension [A](list: List[A]) def ends: (A, A) = (list.head, list.last)

  trait Combinator[A]:
    def combine(x: A, y: A): A

  extension [A](list: List[A])
    def combineAll(using combinator: Combinator[A]): A =
      list.reduce(combinator.combine)

  given intCombinator: Combinator[Int] with
    override def combine(x: Int, y: Int) = x + y

  // Grouping extensions
  object GroupingExtension:
    extension [A](list: List[A])
      def ends: (A, A) = (list.head, list.last)
      def combineAll(using combinator: Combinator[A]): A =
        list.reduce(combinator.combine)

  object IntExtensions:
    extension (n: Int)
      def isPrime: Boolean =
        /*
                isPrime(11) = true
                ip(2) = 11 % 2 != 0 && ip(3)
                ips(3) = 11 % 3 != 0 && ip(4)
                ips(4) = true
                isPrime(15) = ip(2)
                ip(2) = 15 % 2 != 0 && ip(3)
                ips(3) = 15 % 3 != 0  => false
         */
        @tailrec
        def isPrimeTailRec(current: Int): Boolean =
          if (current > Math.sqrt(Math.abs(n))) true
          // sigue derecho si no es true
          else n % current != 0 && isPrimeTailRec(current + 1)
        // continua la iteracino
        if (n == 0 || n == 1 || n == -1) false else isPrimeTailRec(2)

    sealed abstract class Tree[A]
    case class Leaf[A](value: A) extends Tree[A]
    case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]

    object TreeExtentions:
      extension (tree: Tree[Int])
        def sum: Int = tree match
          case Leaf(value) => value
          case Branch(left, right) => left.sum + right.sum

      extension [A](tree: Tree[A])
        def map[B](f: A => B): Tree[B] = tree match
          case Leaf(value) => Leaf(f(value))
          case Branch(left, right) => Branch(left.map(f), right.map(f))
        def forall(predicate: A => Boolean): Boolean = tree match
          case Leaf(value) => predicate(value)
          case Branch(left, right) => left.forall(predicate) && right.forall(predicate)
        def combineAll(using combinator: Combinator[A]): A = tree match
          case Leaf(value) => value
          case Branch(left, right) => combinator.combine(left.combineAll, right.combineAll)

  case class Purchase(nUnits: Int, unitPrice: Double)
  object Purchase:
    given totalPriceOrdering: Ordering[Purchase] with
      override def compare(x: Purchase, y: Purchase) =
        val xTotalPrice = x.nUnits * x.unitPrice
        val yTotalPrice = y.unitPrice * y.unitPrice
        if (xTotalPrice == yTotalPrice) 0
        else if (xTotalPrice < yTotalPrice) -1
        else 1

  object UnitCountOrdering:
    given unitCountOrdering: Ordering[Purchase] =
      Ordering.fromLessThan((x, y) => y.nUnits > x.nUnits)

  object UnitPriceOrdering;
  given unitPrioce: Ordering[Purchase] =
    Ordering.fromLessThan((x, y) => x.unitPrice < y.unitPrice)

  def extensionMain =
    import IntExtensions.*
    import TreeExtentions.*

    val listInt: List[Int] = List(1, 2, 3, 4, 5)
    val aTree = Branch(Branch(Leaf(3), Leaf(1)), Leaf(10))

    println("-" * 50)
    println("Esteban".greetAsPerson)
    println(listInt.ends)
    println(7.isPrime)
    println(6.isPrime)
    println(aTree.forall(_ % 2 == 0))
    println(aTree.sum)
    // println(aTree.combineAll)
    println("-" * 50)
