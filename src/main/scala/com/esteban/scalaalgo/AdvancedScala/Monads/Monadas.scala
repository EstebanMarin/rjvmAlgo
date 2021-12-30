package com.esteban.scalaalgo.AdvancedScala.Monads

import com.esteban.scalaalgo.AdvancedScala.Monads.Monadas.ListStory.OptionStory
import scala.annotation.targetName

object Monadas:
  object ListStory:
    val aList = List(1, 2, 3)
    val anList = List(4, 5, 6, 6)
    val listMult =
      for
        x <- aList
        y <- anList
      yield x * y

    val listFla = aList.flatMap(x => anList.map(y => x * y))
    val f = (x: Int) => List(x, x + 1)
    val g = (x: Int) => List(x, x * 2)
    val pure = (x: Int) => List(x)

    // property 1: left Identity
    val leftIdentity = pure(42).flatMap(f) == f(42)
    val rightIdentity = aList.flatMap(pure) == aList

    // property 3: associativity f(g(x)) composition
    // Garantuess order of application
    // this is not garantieed in the signature, is garanteed by implementation
    val associativity = aList.flatMap(f).flatMap(g) == aList.flatMap(x => f(x).flatMap(g))

    object OptionStory:
      // look on options
      val aOption: Option[Int] = Option(42)
      val bOption: Option[String] = Option("Esteban")
      val another: Option[String] =
        for
          x <- aOption
          y <- bOption
        yield s"$x - $y"

      val flattedOption = aOption.flatMap(x => bOption.map(y => s"$x - $y"))

      val f = (x: Int) => Option(x + 1)
      val g = (x: Int) => Option(x * 2)
      val pure = (x: Int) => Option(x)

      val leftIdentity = pure(42).flatMap(f) == f(42)
      val rightIdentity = aOption.flatMap(pure) == aOption

      // property 3: associativity f(g(x)) composition
      // Garantuess order of application
      // this is not garantieed in the signature, is garanteed by implementation
      val associativity = aOption.flatMap(f).flatMap(g) == aOption.flatMap(x => f(x).flatMap(g))

  // Any computation that might perform side effects
  case class PossiblyMonad[A](unsafeRun: () => A):
    def map[B](f: A => B): PossiblyMonad[B] =
      PossiblyMonad(() => f(unsafeRun()))
    def flatMap[B](f: A => PossiblyMonad[B]): PossiblyMonad[B] =
      PossiblyMonad(() => f(unsafeRun()).unsafeRun())

  object PossiblyMonad:
    @targetName("pure")
    def apply[A](value: => A): PossiblyMonad[A] =
      new PossiblyMonad(() => value)

  object Possibly:
    val aPossibly = PossiblyMonad(42)
    val f = (x: Int) => PossiblyMonad(x + 1)
    val g = (x: Int) => PossiblyMonad(x * 2)
    val pure = (x: Int) => PossiblyMonad(x)

    def possiblyMonadExample(): Unit =
      val aPossibleMonad: PossiblyMonad[Int] = PossiblyMonad {
        println("printing first possible monad")
        42
      }

      val aPossibleMonad2: PossiblyMonad[String] = PossiblyMonad {
        println("printing first possible monad")
        "Scala"
      }

      //   val return1 = aPossibleMonad.unsafeRun()
      //   //   println(return1)
      val program =
        for // computation described not EXECUTED
          x <- aPossibleMonad
          y <- aPossibleMonad2
        yield s"$x - $y"
      println(program.unsafeRun())

  //   val leftIdentity = pure(42).flatMap(f) == f(42)
  //   val rightIdentity = aPossibly.flatMap(pure) == aPossibly
  //   val associativity = aPossibly.flatMap(f).flatMap(g) == aPossibly.flatMap(x => f(x).flatMap(g))

  //   val associativity = aOption.flatMap(f).flatMap(g) == aOption.flatMap(x => f(x).flatMap(g))

  @main def monadsMain =
    println("-" * 50)
    // println(ListStory.leftIdentity)
    // println(ListStory.rightIdentity)
    // println(ListStory.associativity)
    // println(OptionStory.another.get)
    // println(OptionStory.flattedOption.get)
    println(Possibly.possiblyMonadExample())
    println("-" * 50)
