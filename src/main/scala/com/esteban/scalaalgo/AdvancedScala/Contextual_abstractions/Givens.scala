package com.esteban.scalaalgo.AdvancedScala.Contextual_abstractions

object Givens:
  given desendingOrder: Ordering[Int] = Ordering.fromLessThan(_ < _)
  val aList = List(4, 3, 2, 1)
  val ordered = aList.sorted

  case class Person(name: String, age: Int)
  val people = List(Person("Alice", 29), Person("Sarah", 39), Person("Jim", 42))

  given personOrdering: Ordering[Person] = new Ordering[Person] {
    override def compare(x: Person, y: Person): Int = x.name.compareTo(y.name)

  }

  val orderedPeople = people.sorted

  trait Combinator[A]:
    def combine(x: A, y: A): A

  given intCombinator: Combinator[Int] with
    override def combine(x: Int, y: Int) = x + y

  def combineAll[A](list: List[A])(using combinator: Combinator[A]): A =
    list.reduce(combinator.combine)

  given peopleCombinator: Combinator[Person] with
    override def combine(x: Person, y: Person) =
      new Person(x.name + y.name, Math.abs(x.age - y.age))

  val combineAllPeople = combineAll(people)

  @main def run =
    println("-" * 50)
    println(combineAllPeople)
    println("-" * 50)
