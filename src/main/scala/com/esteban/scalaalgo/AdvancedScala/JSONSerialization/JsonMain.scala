package com.esteban.scalaalgo.AdvancedScala.JSONSerialization

import java.util.Date
import com.esteban.scalaalgo.AdvancedScala.JSONSerialization.JSONSerialization.JSONObject

object JSONSerialization:
  case class User(
      name: String,
      age: Int,
      email: String,
    )
  case class Post(content: String, createAt: Date)
  case class Feed(user: User, posts: List[Post])

  sealed trait JSONValue:
    def stringify: String

  final case class JSONString(value: String) extends JSONValue:
    override def stringify = s"\\$value\\"

  final case class JSONInt(value: Int) extends JSONValue:
    override def stringify = value.toString()

  final case class JSONArray(values: List[JSONValue]) extends JSONValue:
    override def stringify = values.map(_.stringify).mkString("[", ",", "]")

  final case class JSONObject(values: Map[String, JSONValue]) extends JSONValue:
    override def stringify = values
      .map {
        case (key, value) => s"\\$key\\: ${value.stringify}"
      }
      .mkString("{", ",", "}")

  val data: JSONObject = JSONObject(
    Map(
      "user" -> JSONString("Esteban"),
      "posts" -> JSONArray(
        List(
          JSONString("Scala is awesome"),
          JSONInt(42),
        )
      ),
    )
  )

  def JsonMain =
    println("-" * 50)
    println(data.toString)
    println("-" * 50)
