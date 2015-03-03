package com.pepegar

import com.pepegar.instantiator.tree.{Branch, Tree, Leaf}
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

      it("returns a Leaf[Any] containing an Int given a Leaf[Int]") {
        assertLeafType[Int, java.lang.Integer]
      }

      it("returns a Leaf[Any] containing an String given a Leaf[String]") {
        assertLeafType[String, String]
      }

      it("returns a Leaf[Any] containing an Float given a Leaf[Float]") {
        assertLeafType[Float, java.lang.Float]
      }

      it("returns Leaf[Any] containing a Boolean given a Leaf[Boolean]") {
        assertLeafType[Boolean, java.lang.Boolean]
      }

      it("returns Leaf[Any] containing a Byte given a Leaf[Byte]") {
        assertLeafType[Byte, java.lang.Byte]
      }

      it("returns Leaf[Any] containing a Short given a Leaf[Short]") {
        assertLeafType[Short, java.lang.Short]
      }

      it("returns Leaf[Any] containing a Char given a Leaf[Char]") {
        assertLeafType[Char, java.lang.Character]
      }

      it("returns Leaf[Any] containing a Long given a Leaf[Long]") {
        assertLeafType[Long, java.lang.Long]
      }

      it("returns Leaf[Any] containing a Double given a Leaf[Double]") {
        assertLeafType[Double, java.lang.Double]
      }

      it("works with Branches containing only Leaves") {
        val typeBranch = Branch(
                           None,
                           List(
                             Leaf(None, typeOf[Int].typeSymbol.asClass),
                             Leaf(None, typeOf[String].typeSymbol.asClass)))

        val valueBranch = Instantiator.mapToValuesTree(typeBranch)

        valueBranch match {
          case Branch(_, ch) => ch.foreach {
            case Leaf(_, v) => assert(v.isInstanceOf[String] || v.isInstanceOf[Int])
            case _ => assert(false)
          }
        }
      }

      it("works with Branches containing both Branches and Leaves") {
        val typeBranch = Branch(
                           None,
                           List(
                             Leaf(None, typeOf[Int].typeSymbol.asClass),
                             Branch(
                               None,
                               List(
                                 Leaf(None, typeOf[Int].typeSymbol.asClass),
                                 Leaf(None, typeOf[String].typeSymbol.asClass)))))

        val valuesBranch = Instantiator.mapToValuesTree(typeBranch)

        valuesBranch match {
          case Branch(_, children) => children foreach {
            case Leaf(_, value) => value shouldBe a [java.lang.Integer]
            case Branch(_, ch) => ch foreach {
              case Leaf(_, v) => assert(v.isInstanceOf[java.lang.Integer] || v.isInstanceOf[String])
              case _ =>
            }
          }
        }
      }
    }
  }

  def assertLeafType[T, U](implicit tagT: TypeTag[T], tagU: Manifest[U]) = {
    val typeLeaf = Leaf(None, typeOf[T].typeSymbol.asClass)
    val str = Instantiator.mapToValuesTree(typeLeaf)

    str match {
      case Leaf(_, value) => value shouldBe a [U]
      case _ =>
    }
  }

  def assertTreeLengths[T](typesTree: Tree[T]): Unit = typesTree match {
    // this branch should represent the A type
    case Branch(_, c1) => {
      assert(c1.length === 2)

      c1.foreach {
        // this branch should represent the B & C types
        case Branch(_, c2) => {
          assert(c2.length === 1)

          c2.foreach {
            // this branch should represent the D type
            case Branch(_, c3) => assert(c3.length === 2)
            case _ =>
          }
        }
        case _ =>
      }
    }
    case _ =>
  }
}
