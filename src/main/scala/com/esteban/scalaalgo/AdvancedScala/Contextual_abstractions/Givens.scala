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

  // val orderedPeople = people.sorted

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

  // context bounds
  def combineInGroups[A](list: List[A])(using combinator: Combinator[A]): List[A] =
    list.grouped(3).map(combineAll).toList

  def combineInGroups_V2[A: Combinator](list: List[A]): List[A] =
    list.grouped(3).map(combineAll).toList

  given listOrdering(using intOrdering: Ordering[Int]): Ordering[List[Int]] with
    override def compare(x: List[Int], y: List[Int]) =
      x.sum - y.sum

  val listOfList = List(List(1, 2), List(1, 2, 3, 4, 5, 6), List(5, 6, 7, 8, 9, 0))
  val orderNested = listOfList

  // generics

  given listOrderingOnCombinator[A](using ord: Ordering[A])(
      using combinator: Combinator[A]
  ): Ordering[List[A]] with
    override def compare(x: List[A], y: List[A]) =
      ord.compare(combineAll(x), combineAll(y))

// Ordering Option[A]
// 1 - create a given for ordering Option[A] if you can order A
// given optinoOrdering[A](using ord: Ordering[A]): Ordering[Option[A]] with
//   override def compare(x: Option[A], y: Option[A]) = (x, y) match
//     case (None, None) => 0
//     case (None, _) => -1
//     case (_, None) => -1
//     case (Some(a), Some(b)) => ord.compare(a, b)

// given optinoOrderin_V2[A: Ordering]: Ordering[Option[A]] with
//   override def compare(x: Option[A], y: Option[A]) = (x, y) match
//     case (None, None) => 0
//     case (None, _) => -1
//     case (_, None) => -1
//     case (Some(a), Some(b)) => fetchGiven[Ordering[A]].compare(a, b)

given optinoOrderin_V3[A: Ordering]: Ordering[Option[A]] with
  override def compare(x: Option[A], y: Option[A]) = (x, y) match
    case (None, None) => 0
    case (None, _) => -1
    case (_, None) => -1
    case (Some(a), Some(b)) => summon[Ordering[A]].compare(a, b)

def fetchGiven[A](using theValue: A): A =
  theValue

def run =
  println("-" * 50)
  // println(combineAllPeople)
  // println(orderNested)
  println(List(Option(1), Option.empty[Int], Option(3), Option(-1000)).sorted)
  println("-" * 50)
