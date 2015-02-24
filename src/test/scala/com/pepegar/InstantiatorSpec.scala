package com.pepegar

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

        assertTree(typesTree, "A", 2)
      }
    }
  }

  def assertTree(tree: Tree, expectedName: String, expectedPropsLength: Int): Unit = tree match {
    case Branch(name, props) => {
      assert(name.toString === expectedName)
      assert(props.length === expectedPropsLength)

      props.foreach { t =>
        t match {
          case Branch(n, p) => assertTree(t, n.toString, p.length)
          case _ => 
        }
      }
    }
    case _ => 
  }
}
