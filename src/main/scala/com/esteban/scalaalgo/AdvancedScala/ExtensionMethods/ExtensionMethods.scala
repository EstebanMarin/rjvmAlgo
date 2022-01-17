package com.esteban.scalaalgo.AdvancedScala.ExtensionMethods

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

  @main def extensionMain =
    import IntExtensions.*

    val listInt: List[Int] = List(1, 2, 3, 4, 5)
    println("-" * 50)
    println("Esteban".greetAsPerson)
    println(listInt.ends)
    println(listInt.combineAll)
    println(7.isPrime)
    println(6.isPrime)
    println("-" * 50)
