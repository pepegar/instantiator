package com.pepegar

import com.pepegar.instantiator.tree._
import com.pepegar.instantiator.types
import scala.reflect.runtime.universe.{typeOf, Type, ClassSymbol, TypeTag}
import util.Random

/** Instantiator object is the main object and entry point to the library.
 *
 * it exposes a single function, ```createInstance``` which is a generic method. That createInstance
 * method takes a type as parameter and returns a valid instance of it.
 *
 * @author pepegar
 */
object Instantiator {

  /** Nothing to see here yet
   *
   * @author pepegar
   */
  def createInstance[T](implicit tag: TypeTag[T]) = {
    val t = typeOf[T]
    generateTypesTree(t)
  }

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

  def mapToValuesTree(typesTree: Tree[ClassSymbol]): Tree[Any] = {
    typesTree.scan(symbolToValue)
  }

  def symbolToValue(s: ClassSymbol): Any = {
    s.toString match {
      case types.INT => Random.nextInt
      case types.STRING => Random.alphanumeric.take(10).toList.mkString("")
      case types.FLOAT => Random.nextFloat
      case types.BOOLEAN => Random.nextBoolean
      case types.BYTE => Random.nextInt.toByte
      case types.SHORT => Random.nextInt(Short.MaxValue).toShort
      case types.CHAR => Random.alphanumeric.take(1)(0)
      case types.LONG => Random.nextLong
      case types.DOUBLE => Random.nextDouble
      case _ =>
    }
  }
}
