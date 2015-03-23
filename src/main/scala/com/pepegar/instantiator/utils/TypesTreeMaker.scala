package com.pepegar.instantiator.utils

import com.pepegar.instantiator.tree._
import scala.reflect.runtime.universe.{Type, ClassSymbol}

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
    val classProperties = tpe.members.filter(!_.isMethod)
    // HACK: this is the ugliest shit ever... i'm not proud of it.
    //       I need to find a way to get if the type has literal representation
    //       in the language.
    val shouldLeaf = classProperties.isEmpty || symbol.fullName == "java.lang.String"

    shouldLeaf match {
      case true => Leaf(Some(symbol.fullName), symbol.asClass)
      case false => Branch(Some(symbol.fullName), classProperties.map(s => generateTypesTree(s.typeSignature)).toList)
    }
  }
}
