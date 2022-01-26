package com.esteban.scalaalgo.AdvancedScala.PathDependentTypes

import com.esteban.scalaalgo.AdvancedScala.PathDependentTypes.ParthDependentTypes.Outer
import com.esteban.scalaalgo.AdvancedScala.PathDependentTypes.ParthDependentTypes.StringRecord

object ParthDependentTypes:
  class Outer:
    class Inner
    object InnerObject
    type InnerType
    def process(arg: Inner) = println(arg)
    def processGeneral(arg: Outer#Inner) = println(arg)

  val outer = new Outer
  val inner = new outer.Inner

  val outerA = new Outer
  val outerB = new Outer
//   val inner2: outerA.Inner = new outerB.Inner

  val innerA = new outerA.Inner
  val innerB = new outerB.Inner

//   outerA.process(innerB) // compilation error
  outerA.process(innerA)
  outer.process(inner)

  // parent type: Outer#Inner
  outerA.processGeneral(innerA)
  outerA.processGeneral(innerB) // outer.inner <: Outer#Inner
  // type level programming
  trait Record:
    type Key
    def defaultValue: Key

  class StringRecord extends Record:
    override type Key = String
    override def defaultValue = ""

  class IntRecord extends Record:
    override type Key = Int
    override def defaultValue = 0

  def getDefaultValue(record: Record): record.Key = record.defaultValue

  val aString: String = getDefaultValue(new StringRecord)
  val anInt: Int = getDefaultValue(new IntRecord)

  // function with dependent types
  val getIdentifier: (record: Record) => record.Key = getDefaultValue

  @main def PathMain =
    println("-" * 50)
    println("-" * 50)
