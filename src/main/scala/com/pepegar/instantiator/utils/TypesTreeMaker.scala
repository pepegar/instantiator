package com.pepegar.instantiator.utils

import com.pepegar.instantiator.tree._
import scala.reflect.runtime.universe.{Type, ClassSymbol, Symbol}

/** TypesTreeMaker trait
 *
 * This trait contains one method creating a tree given a type.
 *
 * It is a recursive method, so it will try to populate the tree with values
 * up to the
 *
 * @author pepegar
 */
trait TypesTreeMaker {

  /** This method generates the type tree of properties of the given type
   *
   * The purpose of it is creating a Tree that will be parsed later in the
   * moment of generating the instance of the class.
   *
   * @author pepegar
   * */
  def generateTypesTree(tpe: Type): Tree[ClassSymbol] = {
    val symbol = tpe.typeSymbol
    val constructorParams = getConstructor(tpe).paramLists(0)
    val shouldLeaf = isLiteral(tpe) || constructorParams.isEmpty

    shouldLeaf match {
      case true => Leaf(Some(symbol.fullName), symbol.asClass)
      case false => Branch(Some(symbol.fullName), constructorParams.map(s => generateTypesTree(s.typeSignature)).toList)
    }
  }

  def isLiteral(tpe: Type): Boolean = {
    val literals: List[String] = List(
      "scala.Int",
      "scala.Float",
      "java.lang.String",
      "scala.Boolean",
      "scala.Byte",
      "scala.Short",
      "scala.Char",
      "scala.Long",
      "scala.Double"
    )

    literals contains tpe.typeSymbol.fullName
  }

  def getConstructor(tpe: Type) = {
    val alternatives = tpe.member(scala.reflect.runtime.universe.termNames.CONSTRUCTOR).alternatives

    def matchTypeAndLength(constructor: Symbol): Boolean = {
      val paramList = constructor.asMethod.paramLists(0)
      val sameTypeAndLength = paramList.length == 1 && paramList(0).typeSignature.toString == tpe.typeSymbol.fullName

      sameTypeAndLength
    }

    if (alternatives.length == 1) {
      alternatives(0).asMethod
    } else {
      alternatives.filter(matchTypeAndLength)(0).asMethod
    }
  }
}
