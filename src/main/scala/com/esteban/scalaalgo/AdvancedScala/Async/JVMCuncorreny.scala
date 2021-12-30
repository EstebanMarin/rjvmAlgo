package com.esteban.scalaalgo.AdvancedScala.Async

import java.util.concurrent.Executors
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{ Failure, Success }
object JVMCuncorreny:
  def basics() =
    val runnable = new Runnable {
      override def run(): Unit = println("Hello there")

    }
    val aThread = new Thread(runnable)
    aThread.start()
    // this will block the thread
    aThread.join()

  def orderOfExecution(): Unit = ??? // not garanteed
  val thread = new Thread(() => println("hello"))
  val threadHello = new Thread(() => (1 to 5).foreach(_ => println("hello")))
  val threadGoodbye = new Thread(() => (1 to 5).foreach(_ => println("test")))
  threadHello.start()
  threadGoodbye.start()

  def demoExcutors(): Unit = ???
  val threadPool = Executors.newFixedThreadPool(8)

// val pool = Executors.ne
//   threadPool.execute(() => println("form pool"))
//   threadPool.shutdown()
