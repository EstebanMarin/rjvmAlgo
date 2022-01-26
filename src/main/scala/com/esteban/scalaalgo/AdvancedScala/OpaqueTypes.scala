package com.esteban.scalaalgo.AdvancedScala

import com.esteban.scalaalgo.AdvancedScala.OpaqueTypes.SocialNetwork.Name

object OpaqueTypes:
  object SocialNetwork:
    // some data struncture = " domain"
    opaque type Name = String
    object Name:
      def apply(stg: String): Name = stg
    extension (name: Name)
      def length: Int = name.length
    // inside Name <-> String
    def addFriend(person: Name, person2: Name): Boolean =
      person.length == person2.length

    val name: Name = "Daniel"

  import SocialNetwork.*
  // val name: Name = "Daniel" //will not compile

  object Graphics:
    opaque type Color = Int // In Hex
    opaque type ColorFilter <: Color = Int

    val Red: Color = 0xff00000
    val Green: Color = 0x00ff000
    val Blue: Color = 0x00000ff
    val halfTransperancy: ColorFilter = 0x88
   
  import Graphics.*
  val aName: Name = Name("Esteban")
  val nameLength: Int = aName.length

  @main def opaceMain =
    println("-" * 50)
    println("-" * 50)
