package com.pepegar.tree

import org.scalatest._

class TreeSpec extends FunSpec {
  def intToString(i: Int): String = i.toString
  describe("Branch") {
    describe("scan") {
      it("converts a Branch[T] to Branch[V]") {
        val integerBranch = Branch(List(Leaf(1), Leaf(2), Leaf(3), Leaf(4)))
        val stringBranch = integerBranch.scan(intToString)

        stringBranch match {
          case Branch(children) => children.foreach {
            case Leaf(v) => assert(v.isInstanceOf[String])
            case _ =>
          }
          case _ =>
        }
      }
    }
  }

  describe("Leaf") {
    describe("scan") {
      it("should convert a Leaf[T] to a Leaf[V]") {
        val leafOfInt = Leaf(1)
        val leafOfString = leafOfInt.scan(intToString)

        leafOfString match {
          case Leaf(v) => assert(v.isInstanceOf[String])
          case _ =>
        }
      }
    }
  }
}
