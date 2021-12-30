package com.esteban.scalaalgo.AdvancedScala.Async

import scala.concurrent.ExecutionContext
import java.util.concurrent.Executors
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.ExecutionContext
import scala.util.{ Failure, Success }
import java.util.concurrent.ExecutorService

object Futures:
  def meainingofLife(): Int =
    Thread.sleep(1000)
    42

  val excutionContext =
    ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(4))

// ExecutionContext.fromExecutorService())

@main def futuresMain =
  println("-" * 50)
  println("-" * 50)
