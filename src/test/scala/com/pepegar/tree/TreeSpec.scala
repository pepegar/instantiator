package com.pepegar.tree

import org.scalatest._

class TreeSpec extends FunSpec {
  def intToString(i: Int): String = i.toString
  describe("Branch") {
    describe("map") {
      it("converts a Branch[T] to Branch[V]") {
        val integerBranch = Branch(List(Leaf(1), Leaf(2), Leaf(3), Leaf(4)))
        val stringBranch = integerBranch.map(intToString)

        stringBranch.children.foreach {
          case Leaf(v) => assert(v.isInstanceOf[String])
          case _ =>
        }
      }
    }
  }

  describe("Leaf") {
    describe("map") {
      it("should convert a Leaf[T] to a Leaf[V]") {
        val leafOfInt = Leaf(1)
        val leafOfString = leafOfInt.map(intToString)

        leafOfString match {
          case Leaf(v) => assert(v.isInstanceOf[String])
          case _ =>
        }
      }
    }
  }
}
