package com.esteban.scalaalgo.AdvancedScala

import com.esteban.scalaalgo.AdvancedScala.LiteralUnionIntersectionTypes.Crocodile

object LiteralUnionIntersectionTypes:

  // literal type
  val aNumber = 3
  val three: 3 = 3
  // union type
  val truthor42: Boolean | Int = 43

  def ambivalantMethod(arg: String | Int) = arg match
    case _: String => "a String"
    case _: Int => "a Number"

  // type inference chooses the lower type ancestor
  val stringOrInt: Matchable = if (43 > 0) "string" else 45
  val string_Int: String | Int = if (43 > 0) "string" else 45

  // union types + nulls
  type Maybe[T] = T | Null

  def handleMaybe(someValue: Maybe[String]): Int =
    if (someValue != null) someValue.length // flow typing
    else 0

  class Animal
  trait Carnivore
  class Crocodile extends Animal with Carnivore

  val carnivoreAnimal: Crocodile & Animal = new Crocodile

  trait Camera:
    def takepicture() = println("smile")
    def use() = println("click")

  trait Phone:
    def makeCall() = println("calling")
    def use() = println("ring")

  def useSmartDevice(sp: Camera & Phone): Unit =
    sp.takepicture()
    sp.makeCall()
    sp.use() // who's methods is going to be called

  @main def liteMain =
    println("-" * 50)
    println("-" * 50)
