package com.pepegar.instantiator.utils

case class A(b: B, c: C)
case class B(d: D)
case class C(d: D)
case class D(left: Int, right: Float)
case class ClassWithTwoIntParam(a: Int, b: Int)
case class AnotherClass(a: Int, b: ClassWithTwoIntParam)
