package com.esteban.scalaalgo.catsEffects

import cats.effect.{IO, IOApp, Ref}

object effectsRef:
  val atomicMo: IO[Ref[IO, Int]] = Ref[IO].of(42)

  val incremental: IO[Unit] = atomicMo.flatMap{ ref => 
    ref.set(43)
  }


//  val mol: IO[Unit] = atomicMo.flatMap{ ref =>
//    ref.get
//  }

  @main def effectsMain = 
      println("-" * 50)
      println("-" * 50)
