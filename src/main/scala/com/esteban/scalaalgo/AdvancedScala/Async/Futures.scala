package com.esteban.scalaalgo.AdvancedScala.Async

import scala.concurrent.ExecutionContext
import java.util.concurrent.Executors
import scala.concurrent.Future
import scala.concurrent.ExecutionContext
import scala.util.{ Failure, Success }
import java.util.concurrent.ExecutorService
import scala.util.Try

object Futures:
  def meainingofLife(): Int =
    Thread.sleep(1000)
    42
  val executors = Executors.newFixedThreadPool(4)
  given executionContext: ExecutionContext =
    ExecutionContext.fromExecutorService(executors)

  val aFuture: Future[Int] = Future(meainingofLife())
  val futureResult: Option[Try[Int]] = aFuture.value

  aFuture.onComplete {
    case Success(value) => println(s"$value")
    case Failure(ex) => println(s"$ex")
  }

  @main def futuresMain =
    println("-" * 50)
    println(futureResult)
    Thread.sleep(2000)
    executors.shutdown()
    println("-" * 50)
