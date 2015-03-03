package com.pepegar

import com.pepegar.instantiator.utils.{TypesTreeMaker, TypesToValuesMapper}
import scala.reflect.runtime.universe.{typeOf, TypeTag}

/** Instantiator object is the main object and entry point to the library.
 *
 * it exposes a single function, ```createInstance``` which is a generic method. That createInstance
 * method takes a type as parameter and returns a valid instance of it.
 *
 * @author pepegar
 */
object Instantiator extends TypesTreeMaker with TypesToValuesMapper {

  /** Nothing to see here yet
   *
   * @author pepegar
   */
  def createInstance[T](implicit tag: TypeTag[T]) = {
    val t = typeOf[T]
    generateTypesTree(t)
  }
}
