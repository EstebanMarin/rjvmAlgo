package com.esteban.scalaalgo.AdvancedScala.FunctionalCollections

object FunctionalCollections:
  // Set are functions from A => Boolean
  val aSet = Set(1, 2, 3, 4)
  val setContains = aSet(1) // true or false
  // Seq extends  PARTIAL functions from index => Element
  // in this case PartialFunction[Int] => A
  val aSeq = Seq(1, 2, 3, 4)
  val aElem = aSeq(2) // element or
//   val aElem = aSeq(7) // throw error

  // Map
  // Map[K, V] "extends" PartialFunction K => V
  val aPhoneBook: Map[String, Int] = Map(
    "Alice" -> 1234,
    "Bob" -> 121234,
  )
  val aliceNumber = aPhoneBook("Alice")

//   val estebanNumber = aPhoneBook("Esteban") // throws error

@main def collectionsMain =
  println("-" * 50)
  println("-" * 50)
