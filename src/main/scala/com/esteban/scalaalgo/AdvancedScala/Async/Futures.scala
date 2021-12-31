package com.esteban.scalaalgo.AdvancedScala.Async

import scala.concurrent.ExecutionContext
import java.util.concurrent.Executors
import scala.concurrent.{ Future, Promise }
import scala.concurrent.ExecutionContext
import scala.util.{ Failure, Success }
import java.util.concurrent.ExecutorService
import scala.util.Try
import java.util.Random
import com.esteban.scalaalgo.AdvancedScala.Async.Futures.Profile

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

  /*
  Functional composition
   */
  case class Profile(id: String, name: String):
    def sendMessage(anotherProfile: Profile, message: String): Unit =
      println(s"${this.name} sending message to ${anotherProfile.name}: $message")

  object SocialNetwork:
    // database
    given executionContext: ExecutionContext =
      ExecutionContext.fromExecutorService(executors)
    val names = Map(
      "1" -> "Esteban",
      "2" -> "Camilo",
      "3" -> "Adriana",
    )
    // friends
    val friends = Map(
      "1" -> "2"
    )

    val random = new Random()

    def fetchProfile(id: String): Future[Profile] = Future {
      Thread.sleep(random.nextInt(300))
      Profile(id, names(id))
    }

    def fetchBestFriend(profile: Profile): Future[Profile] = Future {
      Thread.sleep(random.nextInt(400))
      val bestFriendId = friends(profile.id)
      Profile(bestFriendId, names(bestFriendId))
    }

    def sendMessageFriend(accountId: String, message: String): Unit =
      for
        profile <- fetchProfile(accountId)
        bestFriend <- fetchBestFriend(profile)
        _ <- Future(println(s"for ${profile.name} ${bestFriend.name}"))
      yield profile.sendMessage(bestFriend, message)

    val recoverFuture: Future[Profile] = fetchProfile("none").recover {
      case e: Throwable => Profile("", "")
    }

    val recoverFlatMatp = fetchProfile("None").recoverWith {
      case e: Throwable => fetchProfile("dummy")
    }

    val caseFallback = fetchProfile("None").fallbackTo(fetchProfile("Dummy"))
    // promise
    val promise: Promise[Int] = Promise[Int]()
    val futureInside: Future[Int] = promise.future
    // val testPromise = promise.

    object Exercises:
      // full immvediatelly a Future with a value
      val value: Future[Int] = Future.successful(42)

      def inSequence[A, B](first: Future[A], second: Future[B]): Future[B] =
        for
          _ <- first
          s <- second
        yield s

      def first[A](f1: Future[A], f2: Future[A]): Future[A] =
        val promise = Promise[A]()
        f1.onComplete(result => promise.tryComplete(result))
        f2.onComplete(result => promise.tryComplete(result))
        promise.future

      def last[A](f1: Future[A], f2: Future[A]): Future[A] =
        val bothPromise = Promise[A]()
        val lastPromise = Promise[A]()
        def checkAndComplete(result: Try[A]): Unit =
          if (!bothPromise.tryComplete(result))
            lastPromise.complete(result)
        f1.onComplete(checkAndComplete)
        f2.onComplete(checkAndComplete)
        lastPromise.future

      def retryUntill[A](action: () => Future[A], predicate: A => Boolean): Future[A] = ???

  @main def futuresMain =
    println("-" * 50)
    println(futureResult)
    Thread.sleep(2000)
    executors.shutdown()
    println(SocialNetwork.sendMessageFriend("1", "Hello from here"))
    println("-" * 50)

end Futures
