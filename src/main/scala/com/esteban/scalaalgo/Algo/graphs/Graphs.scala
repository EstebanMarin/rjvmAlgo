package com.esteban.scalaalgo.Algo.graphs

import scala.annotation.tailrec
import scala.collection.immutable

object Graphs extends App:
  type Graph[T] = Map[T, Set[T]]
  val graph: Graph[Int] = Map()
  val socialNetwork: Graph[String] = Map(
    "Alice" -> Set("Bob", "Charlie", "David"),
    "Bob" -> Set(),
    "Charlie" -> Set(),
    "David" -> Set("Bob", "Mary"),
    "Mary" -> Set("Bob", "Charlie"),
  )

  def outDegree[T](graph: Graph[T], node: T): Int =
    val getFriends: Iterable[Int] =
      for
        (name: T, friends: Set[T]) <- graph
        nameListOfFriends = if node == name then friends else Set.empty[T]
        outDegree = nameListOfFriends.size
      yield outDegree
    getFriends.fold(0)(_ + _)

  def inDegree[T](graph: Graph[T], node: T): Int =
    val getHitInList =
      for
        (name: T, friends: Set[T]) <- graph
        hitInList = friends.count(list => list == node)
      yield hitInList
    getHitInList.fold(0)(_ + _)

  def isPath[T](
      graph: Graph[T],
      start: T,
      end: T,
    ): Boolean =

    @tailrec def isPathTailRec(remaining: List[T], consideredNodes: Set[T]): Boolean =
      if remaining.isEmpty then false
      else
        val node = remaining.head
        if (node == end) true
        else if consideredNodes.contains(node) then isPathTailRec(remaining.tail, consideredNodes)
        else isPathTailRec(remaining ++ graph(node), consideredNodes + node)

    isPathTailRec(List(start), Set())


  // just fiding a path
  def findPath[T](graph: Graph[T], start: T, end: T): List[T] =
    def findPathTailRec(remaining: List[T], consideredNodes: Set[T], acc: List[T]): List[T] = ???
    ???
      



  println(s"HELLO THERe out = ${outDegree[String](socialNetwork, "Alice")}")
  println(s"HELLO THERe out = ${outDegree[String](socialNetwork, "David")}")
  println(s"HELLO THERe out = ${inDegree[String](socialNetwork, "Bob")}")
  println(s"HELLO THERe out = ${isPath[String](socialNetwork, "Bob", "David")}")
  
