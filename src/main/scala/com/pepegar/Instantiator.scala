package com.pepegar

import com.pepegar.tree._
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
      case true => Leaf(symbol.asClass)
      case false => Branch(classProperties.map(s => generateTypesTree(s.typeSignature)).toList)
    }
  }

  def mapToValuesTree(typesTree: Tree[ClassSymbol]): Tree[Any] = {
    typesTree.map(symbolToValue)
  }

  def symbolToValue(s: ClassSymbol): Any = {
    s match {
      case s if s.toString == "class Int" => Random.nextInt
      case s if s.toString == "class String" => Random.alphanumeric.take(10).toList.mkString("")
      case s if s.toString == "class Float" => Random.nextFloat
      case s if s.toString == "class Boolean" => Random.nextBoolean
      case s if s.toString == "class Byte" => Random.nextInt.toByte
      case s if s.toString == "class Short" => Random.nextInt(Short.MaxValue).toShort
      case s if s.toString == "class Char" => Random.alphanumeric.take(1)(0)
      case s if s.toString == "class Long" => Random.nextLong
      case s if s.toString == "class Double" => Random.nextDouble
      case _ =>
    }
  }
}
