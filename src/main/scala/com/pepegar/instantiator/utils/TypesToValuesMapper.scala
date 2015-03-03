package com.pepegar.instantiator.utils

import com.pepegar.instantiator.tree.{Tree}
import com.pepegar.instantiator.types
import scala.reflect.runtime.universe.{typeOf, Type, ClassSymbol, TypeTag}
import util.Random

/** TypesToValuesMapper trait
 *
 * this trait contains the methods and implementation for all the things
 * relative to mapping a types tree to a values tree.
 *
 * @author pepegar
 */
trait TypesToValuesMapper {

  /** mapToValuesTree method
   *
   * this method takes a Tree[ClassSymbol] AKA a tree of type definitions
   * and maps it to a tree of values, being the values random generated values
   * of that types.
   *
   * @author pepegar
   */
  def mapToValuesTree(typesTree: Tree[ClassSymbol]): Tree[Any] = {
    typesTree.scan(symbolToValue)
  }

  /** symbolToValue method
   *
   * this method takes a class symbol and returns a valid value for the given
   * class.
   *
   * @author pepegar
   */
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
