package com.pepegar.instantiator.utils

import com.pepegar.instantiator.tree.{Branch, Tree, Leaf}
import org.scalatest._
import scala.reflect.runtime.universe._

class TypesToValuesMapperSpec extends FunSpec with Matchers {

  new {} with TypesToValuesMapper with TypesTreeMaker {
    describe("mapToValueTree") {
      it("converts a Tree[ClassSymbol] to a Tree[Any]") {
        val t = typeOf[A]
        val typesTree = generateTypesTree(t)
        val valuesTree = mapToValuesTree(typesTree)

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

        val valueBranch = mapToValuesTree(typeBranch)

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

        val valuesBranch = mapToValuesTree(typeBranch)

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

    def assertLeafType[T, U](implicit tagT: TypeTag[T], tagU: Manifest[U]) = {
      val typeLeaf = Leaf(None, typeOf[T].typeSymbol.asClass)
      val str = mapToValuesTree(typeLeaf)

      str match {
        case Leaf(_, value) => value shouldBe a [U]
        case _ =>
      }
    }
  }

}
