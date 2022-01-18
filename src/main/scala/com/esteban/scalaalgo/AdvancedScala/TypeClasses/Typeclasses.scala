package com.esteban.scalaalgo.AdvancedScala.TypeClasses

object Typeclasses:
  trait HTMLWritable:
    def toHtml: String

  case class User(
      name: String,
      age: Int,
      email: String,
    ) extends HTMLWritable:
    override def toHtml = s"<div>$name $age yo <a href=$email/></div>"

// Part 1 - Type class definition
  trait HTMLSerializer[T]:
    def serialized(value: T): String

  // Part 2 - Type class instance for the supported type
  given userSerializer: HTMLSerializer[User] with
    override def serialized(value: User): String =
      val User(name, age, email) = value
      s"<div>$name $age yo <a href=$email/></div>"

  val bobUser = User("bob", 46, "bob@com.com")
  val bob: String = userSerializer.serialized(bobUser)

  // import java.util.Date
  // given dateSerializer: HTMLSerializer[Date] with
  //   override def serialize(date: Date) = s"<div>${date.toString()}</div>"

  def serialize[T](value: T)(using serializer: HTMLSerializer[T]): String =
    serializer.serialized(value)

  object HTMLSyntax:
    extension [T](value: T)
      def toHtml(using serializer: HTMLSerializer[T]): String = serializer.serialized(value)

  import HTMLSyntax.*
  val boHTML4 = bobUser.toHtml

  @main def typeMain =
    println("-" * 50)
    println(bob)
    println("-" * 50)
