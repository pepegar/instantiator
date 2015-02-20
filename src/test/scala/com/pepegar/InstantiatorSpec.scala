package com.pepegar

import org.scalatest._
import scala.reflect.runtime.universe._

class InstantiatorSpec extends FunSpec {
  case class A(b: B, c: C)
  case class B(d: D)
  case class C(d: D)
  case class D(left: Int, right: String)

  describe("Instantiator") {
    describe("generateTypesTree") {
      it("should create a valid tree given a type instance.") {
        val t = typeOf[A]
        val typesTree = Instantiator.generateTypesTree(t)

        typesTree match {
          case Branch(name, props) => {
            assert(props.length === 2) // A has 2 properties
            assert(name.toString === "A")
          }
          case _ =>
        }
      }
    }
  }
}
