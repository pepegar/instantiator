package com.pepegar.instantiator.utils

import org.scalatest._
import scala.reflect.runtime.universe._
import com.pepegar.instantiator.tree.{Branch, Tree, Leaf}

class TypesTreeMakerSpec extends FunSpec with Matchers {
  describe("generateTypesTree") {
    it("should create a valid tree given a type instance.") {
      new {} with TypesTreeMaker {
        val t = typeOf[A]
        val typesTree = generateTypesTree(t)
        assertTreeLengths(typesTree)
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
