package com.pepegar.instantiator.utils

import org.scalatest._
import scala.reflect.runtime.universe._
import com.pepegar.instantiator.tree.{Branch, Tree, Leaf}

class TypesTreeMakerSpec extends FunSpec with Matchers {
  describe("isLiteral") {
    it("should work for all literals") {
      new {} with TypesTreeMaker {
        val literals = List(
          typeOf[String],
          typeOf[Int],
          typeOf[Float],
          typeOf[Boolean],
          typeOf[Byte],
          typeOf[Short],
          typeOf[Char],
          typeOf[Long],
          typeOf[Double])

        literals.foreach(l => assert(isLiteral(l)))
      }
    }
  }

  describe("generateTypesTree") {
    it("should create a valid tree given a type instance.") {
      new {} with TypesTreeMaker {
        val t = typeOf[A]
        val typesTree = generateTypesTree(t)
        assertTreeLengths(typesTree)
      }
    }

    it("works with primitive types") {
      new {} with TypesTreeMaker with TypesToValuesMapper with ValueTreeParser {
        val string = typeOf[String]
        val stringTypesTree = generateTypesTree(string)
        stringTypesTree shouldBe a [Leaf[_]]

        val int = typeOf[Int]
        val intTypesTree = generateTypesTree(int)
        intTypesTree shouldBe a [Leaf[_]]

        val float = typeOf[Float]
        val floatTypesTree = generateTypesTree(float)
        floatTypesTree shouldBe a [Leaf[_]]
      }
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
