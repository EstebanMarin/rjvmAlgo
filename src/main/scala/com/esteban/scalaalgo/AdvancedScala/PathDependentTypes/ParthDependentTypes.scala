package com.esteban.scalaalgo.AdvancedScala.PathDependentTypes

import com.esteban.scalaalgo.AdvancedScala.PathDependentTypes.ParthDependentTypes.Outer

object ParthDependentTypes:
  class Outer:
      class Inner
      object InnerObject
      type InnerType
      def process(arg: Inner) = println(arg)

  val outer = new Outer
  val inner = new outer.Inner 

  val outerA = new Outer
  val outerB = new Outer
//   val inner2: outerA.Inner = new outerB.Inner 

  val innerA = new outerA.Inner
  val innerB = new outerB.Inner


//   outerA.process(innerB)
  outerA.process(innerA)
  
  @main def PathMain =
    println("-" * 50)
    println("-" * 50)

