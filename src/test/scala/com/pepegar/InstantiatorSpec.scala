package com.pepegar

import org.scalatest._

class InstantiatorSpec extends FunSpec with Matchers {
  it("instantiates primitives correctly") {
    val maybeInt = Instantiator.createInstance[Int]
    maybeInt shouldBe a [java.lang.Integer]

    val maybeString = Instantiator.createInstance[String]
    maybeString shouldBe a [java.lang.String]

    val maybeFloat = Instantiator.createInstance[Float]
    maybeFloat shouldBe a [java.lang.Float]

    val maybeByte = Instantiator.createInstance[Byte]
    maybeByte shouldBe a [java.lang.Byte]

    val maybeBoolean = Instantiator.createInstance[Boolean]
    maybeBoolean shouldBe a [java.lang.Boolean]

    val maybeShort = Instantiator.createInstance[Short]
    maybeShort shouldBe a [java.lang.Short]
  }
}
