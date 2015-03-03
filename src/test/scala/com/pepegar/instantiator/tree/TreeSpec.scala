package com.pepegar.instantiator.tree

import org.scalatest._

class TreeSpec extends FunSpec with Matchers {
  def intToString(i: Int): String = i.toString

  describe("Tree") {
    describe("scan") {
      it("correctly chooses between Branch or Leaf implementation") {
        val tLeaf: Tree[Int] = Leaf(None, 1)
        val tBranch: Tree[String] = Branch(None, List(Leaf(None, "asdf")))

        tLeaf scan { s =>
          s shouldBe a [java.lang.Integer]
        }

        tBranch scan { s =>
          s shouldBe a [java.lang.String]
        }
      }
    }
  }

  describe("Branch") {
    describe("scan") {
      it("converts a Branch[T] to Branch[V]") {
        val integerBranch = Branch(None, List(Leaf(None, 1), Leaf(None, 2), Leaf(None, 3), Leaf(None, 4)))
        val stringBranch = integerBranch.scan(intToString)

        stringBranch match {
          case Branch(_, children) => children.foreach {
            case Leaf(_, v) => assert(v.isInstanceOf[String])
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
        val leafOfInt = Leaf(None, 1)
        val leafOfString = leafOfInt.scan(intToString)

        leafOfString match {
          case Leaf(_, v) => assert(v.isInstanceOf[String])
          case _ =>
        }
      }
    }
  }
}
