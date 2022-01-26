package com.esteban.scalaalgo.AdvancedScala.PathDependentTypes

import com.esteban.scalaalgo.AdvancedScala.PathDependentTypes.ParthDependentTypes.Outer

object ParthDependentTypes:
  class Outer:
      class Inner
      object InnerObject
      type InnerType

  val outer: Outer = new Outer
  val inner: com.esteban.scalaalgo.AdvancedScala.PathDependentTypes.ParthDependentTypes.outer.Inner.type = outer.Inner 

  val outerA = new Outer
  val outerB = new Outer
  val inner2: outerA.Inner = new outerB.Inner
  
  @main def PathMain =
    println("-" * 50)
    println("-" * 50)

