package com.pepegar

import com.pepegar.tree.{Branch, Tree, Leaf}
import org.scalatest._
import scala.reflect.runtime.universe._

class InstantiatorSpec extends FunSpec {
  case class A(b: B, c: C)
  case class B(d: D)
  case class C(d: D)
  case class D(left: Int, right: Float)

  describe("Instantiator") {
    describe("generateTypesTree") {
      it("should create a valid tree given a type instance.") {
        val t = typeOf[A]
        val typesTree = Instantiator.generateTypesTree(t)

        assertTreeLengths(typesTree)
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
