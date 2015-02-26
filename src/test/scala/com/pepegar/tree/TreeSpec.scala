package com.pepegar.tree

import org.scalatest._

class TreeSpec extends FunSpec {
  describe("Branch") {
    describe("map") {
      it("converts a Branch[Tree] to Branch[T]") {
        def intToString(i: Int): String = i.toString
        val integerBranch = Branch(List(1,2,3,4))
        val stringBranch = integerBranch.map[String](intToString)

        stringBranch.properties.foreach { elem =>
          assert(elem.isInstanceOf[String])
        }
      }
    }
  }
}
