package com.pepegar.instantiator.utils

import org.scalatest._
import com.pepegar.instantiator.tree.{Leaf, Branch}

class ValueTreeParserSpec extends FunSpec with Matchers {

  new {} with ValueTreeParser {
    describe("instantiate") {
      it("works with leaves (by returning the value)") {
        val value1 = instantiate(Leaf(None, 4))

        value1 shouldBe a [java.lang.Integer]
        value1 shouldBe 4

        val value2 = instantiate(Leaf(None, "asdf"))

        value2 shouldBe a [String]
        value2 shouldBe "asdf"
      }

      it("works with branches containing only leaves") {
        val branch = instantiate(
          Branch(
            Some("com.pepegar.instantiator.utils.ClassWithTwoIntParam"),
            List(
              Leaf(None, 4),
              Leaf(None, 5))))

        branch shouldBe a [ClassWithTwoIntParam]
      }

      it("works with branches containing both branches and leaves") {
        val instance = instantiate(
          Branch(
            Some("com.pepegar.instantiator.utils.AnotherClass"),
            List(
              Leaf(None, 4),
              Branch(
                Some("com.pepegar.instantiator.utils.ClassWithTwoIntParam"),
                List(
                  Leaf(None, 4),
                  Leaf(None, 5))))))

        instance shouldBe a [AnotherClass]
      }
    }
  }

}
