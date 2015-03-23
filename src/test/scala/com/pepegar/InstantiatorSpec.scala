package com.pepegar

import org.scalatest._

case class TwoInts(a: Int, b: Int)
case class TwoFloats(a: Float, b: Float)
case class TwoStrings(a: String, b: String)
case class StringAndInt(a: String, b: Int)
case class IntAndString(a: Int, b: String)
case class TwoIntsTwoFloats(a: TwoInts, b: TwoFloats)
case class A()
case class B()
case class C()
case class OtherClass(a:A, b: B, c: C)
case class HellOnEarth( a: Int, b: String, c: Double, d: OtherClass, e: A, f: B, g: C, h: Int, i: String, j: Double, k: OtherClass, l: A, m: B, n: C, o: Int, p: String, q: Double, s: OtherClass, t: A, u: B, v: C, w: Int, x: String, y: Double, z: OtherClass)

class InstantiatorSpec extends FunSpec with Matchers {
  describe("createInstance") {
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

    it("instantiates simple classes correctly") {
      val maybeTwoInts = Instantiator.createInstance[TwoInts]
      maybeTwoInts shouldBe a [TwoInts]

      val maybeTwoFloats = Instantiator.createInstance[TwoFloats]
      maybeTwoFloats shouldBe a [TwoFloats]

      val maybeTwoStrings = Instantiator.createInstance[TwoStrings]
      maybeTwoStrings shouldBe a [TwoStrings]
    }

    it("instantiates classes with different constructor params") {
      val maybeIntAndString = Instantiator.createInstance[IntAndString]
      maybeIntAndString shouldBe a [IntAndString]
    }

    it("instantiates classes whose params are not only primitives but also other classes ") {
      val maybeTwoIntsTwoFloats = Instantiator.createInstance[TwoIntsTwoFloats]
      maybeTwoIntsTwoFloats shouldBe a [TwoIntsTwoFloats]
    }

    it("instantiates classes without parameters") {
      val maybeA = Instantiator.createInstance[A]
      maybeA shouldBe a [A]
    }

    it("instantiates BEHEMOTH classes") {
      val maybeHellOnEarth = Instantiator.createInstance[HellOnEarth]
      maybeHellOnEarth shouldBe a [HellOnEarth]
    }
  }
}
