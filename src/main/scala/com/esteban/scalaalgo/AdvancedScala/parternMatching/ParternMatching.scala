package com.esteban.scalaalgo.parternMatching

object ParternMatching:
  class Person(val name: String, val age: Int)

  object Person:
    def unapply(person: Person): Option[(String, Int)] = // person match {Person(string, int)}
      if (person.age < 21) None
      else Some((person.name, person.age))

    def unapply(age: Int): Option[String] = // person match {Person(int)}
      if (age < 21) Some("minor")
      else Some("legally allowed to drink")

  val esteban = new Person("Esteban", 35)
  val estebanAge = esteban match // esteban.unapply(esteban) => Option[(String, Int)]
    case Person(n, a) => s"age $a, name: $n"
  val estebanStatus = esteban.age match // esteban.unapply(esteban.age) => Option[String]
    case Person(status) => s"Esteban's drinking status: $status "

  def PaternMain =
    println("-" * 50)
    println(estebanAge)
    println(estebanStatus)
    println("-" * 50)
