package com.esteban.scalaalgo.AdvancedScala.variance

object Variance:
  class Animal
  case class Dog(name: String) extends Animal
  case class Cat(name: String) extends Animal
  case class Crocodile(name: String) extends Animal

  class MyList[+A]

  val lassie = Dog("Lassie")
  val hachi = Dog("Hachi")
  val listOfAnimals: List[Animal] = List(lassie, hachi)

  trait Vet[-A]:
    def heal(animal: A): Boolean

  val myVet: Vet[Dog] = new Vet[Animal]:
    override def heal(animal: Animal) = true

  val healLassie = myVet.heal(lassie)

  /*
    Rule of thumb:
        - if your type PRODUCES or Retreives a value. Then it should be COVARIANT
        - if your type CONSUMES or ACTS ON a value CONTRAVARIANT
        - Otherwise INVARIANT
   */

  // COVARIANT
  class Cage[A <: Animal]

  // Wierd container
  class Cage2[A >: Animal]
  val aRealCage: Cage[Dog] = new Cage[Dog]

  abstract class LList[+A]:
    def head: A
    def tail: LList[A]
    def add[B >: A](element: B): LList[B] //widen the type

  def VarianceMain =
    println("-" * 50)
    println("-" * 50)
