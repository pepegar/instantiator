package com.pepegar

import org.scalatest._

case class Test()

class InstantiatorSpec extends FlatSpec with Matchers{
  "Instantiator".should("return the type of the param").in {
    val ret = Instantiator.createInstance[Test](classOf[Test])

    ret.toString.should(be("class com.pepegar.Test"))
  }
}
