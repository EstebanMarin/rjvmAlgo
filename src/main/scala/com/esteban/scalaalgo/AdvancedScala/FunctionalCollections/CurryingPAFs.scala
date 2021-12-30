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

  val addF = (x: Int, y: Int) => x + y
  def addD(x: Int, y: Int) = x + y
  def curF(x: Int)(y: Int) = x + y
  // add7
  val add7 = addF(_, 7)
  val add72 = addD(_, 7)
  val add73 = curF(7)
  // add7 Lamdas
  val ad74 = x => addF(x, 7)
  val ad75 = x => addD(x, 7)
  // curried
  val ad76 = addF.curried(7)

  val ad71R = add7(2)
  val ad72R = add72(2)
  val ad73R = add73(2)

  val curriedFormater: String => Double => String = fmt => number => fmt.format(number)
  val someDecimals = List(Math.PI, Math.E, 1, 9.8, 1.3e-12)

  def curryingMain =
    println(insertName("Esteban"))
    println(fillTheBlanks("He is trully => ", " => Brilliant"))
    println(someDecimals.map(curriedFormater("%4.2f")))
