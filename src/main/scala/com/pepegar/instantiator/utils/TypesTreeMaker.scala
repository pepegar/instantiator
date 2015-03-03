package com.pepegar.instantiator.utils

import com.pepegar.instantiator.tree._
import scala.reflect.runtime.universe.{Type, ClassSymbol}

trait TypesTreeMaker {
  /** This method generates the type tree of properties of the given type
   *
   * The purpose of it is creating a Tree that will be parsed later in the moment of generating the
   * instance of the class.
   *
   * @author pepegar
   * */
  def generateTypesTree(tpe: Type): Tree[ClassSymbol] = {
    val symbol = tpe.typeSymbol
    val classProperties = tpe.members.filter(!_.isMethod)

    classProperties.isEmpty match {
      case true => Leaf(Some(symbol.name), symbol.asClass)
      case false => Branch(Some(symbol.name), classProperties.map(s => generateTypesTree(s.typeSignature)).toList)
    }
  }
}
