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

        assertTree(typesTree, 2)
      }
    }
  }

  def assertTree[T](tree: T, expectedPropsLength: Int): Unit = tree match {
    case Branch(props) => {
      assert(props.length === expectedPropsLength)

      props.foreach { t =>
        t match {
          case Branch(p) => assertTree(t, p.length)
          case _ => 
        }
      }
    }
    case _ => 
  }
}
