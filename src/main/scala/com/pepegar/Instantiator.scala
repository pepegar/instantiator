package com.pepegar

import scala.reflect.runtime.universe._

abstract class TypeTree
abstract class Branch(a: TypeTree*) extends TypeTree
abstract class Leaf[T](value: T) extends TypeTree

object Instantiator {
  def createInstance[T](implicit tag: TypeTag[T]) = {
    val t = typeOf[T]
    val params = t.members.filter(!_.isMethod).map(s => (s.name, s.typeSignature))
    params
  }

  //def generateTree(l: List[(TermName, )]) = 
}
