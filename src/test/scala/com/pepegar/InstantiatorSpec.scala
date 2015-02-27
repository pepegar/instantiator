package com.pepegar

import com.pepegar.tree.{Branch, Tree, Leaf}
import org.scalatest._
import scala.reflect.runtime.universe._

class InstantiatorSpec extends FunSpec with Matchers {
  case class A(b: B, c: C)
  case class B(d: D)
  case class C(d: D)
  case class D(left: Int, right: Float)

  describe("Instantiator") {
    val t = typeOf[A]
    val typesTree = Instantiator.generateTypesTree(t)

    describe("generateTypesTree") {
      it("should create a valid tree given a type instance.") {
        assertTreeLengths(typesTree)
      }
    }

    describe("mapToValueTree") {
      it("converts a Tree[ClassSymbol] to a Tree[Any]") {
        val valuesTree = Instantiator.mapToValuesTree(typesTree)

        assert(valuesTree.isInstanceOf[Tree[Any]])
      }

      it("retruns a Leaf[Any] containing an Int given a Leaf[Int]") {
        val typeLeaf = Leaf(typeOf[Int].typeSymbol.asClass)
        val intLeaf = Instantiator.mapToValuesTree(typeLeaf)

        intLeaf match {
          case Leaf(value) => value shouldBe a [java.lang.Integer]
          case _ =>
        }
      }

      it("retruns a Leaf[Any] containing an String given a Leaf[String]") {
        val typeLeaf = Leaf(typeOf[String].typeSymbol.asClass)
        val str = Instantiator.mapToValuesTree(typeLeaf)

        str match {
          case Leaf(value) => value shouldBe a [String]
          case _ =>
        }
      }
    }
  }

  def assertTreeLengths[T](typesTree: Tree[T]): Unit = typesTree match {
    // this branch should represent the A type
    case Branch(c1) => {
      assert(c1.length === 2)

      c1.foreach {
        // this branch should represent the B & C types
        case Branch(c2) => {
          assert(c2.length === 1)

          c2.foreach {
            // this branch should represent the D type
            case Branch(c3) => assert(c3.length === 2)
            case _ =>
          }
        }
        case _ =>
      }
    }
    case _ =>
  }
}
