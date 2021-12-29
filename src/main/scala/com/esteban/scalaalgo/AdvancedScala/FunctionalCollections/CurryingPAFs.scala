package com.esteban.scalaalgo.AdvancedScala.FunctionalCollections

object CurryingPAFs:
  // currying
  val superAdder: Int => Int => Int =
    x => y => x + y
  val eight = superAdder(3)(5)

  // underscore
  def concatenator(
      x: String,
      y: String,
      z: String,
    ): String = x + y + z

  val insertName = concatenator("Hello my name is: ", _: String, "!!")
  val fillTheBlanks = concatenator(_: String, "Esteban", _: String)

  @main def curryingMain =
    println(insertName("Esteban"))
    println(fillTheBlanks("He is trully => ", " => Brilliant"))
