package com.pepegar

import org.scalatest._

case class A(b: B, c: C)
case class B(d: D)
case class C(d: D)
case class D()

class InstantiatorSpec extends FunSpec {
  val ret = Instantiator.createInstance[A]

  println()
  println(ret)
  println()

  assert(false)
}
