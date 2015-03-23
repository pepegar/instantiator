package com.pepegar.instantiator.utils

import com.pepegar.instantiator.tree._

trait ValueTreeParser {

  /** instantiate takes a tree of values and returns an object of that type
   *
   * @author pepegar
   */
  def instantiate(valueTree: Tree[Any]): Any = {
    valueTree match {
      case Leaf(typeData, value) => getInstanceForLeaf(typeData, value)
      case Branch(typeData, children) => {
        typeData match {
          case None => None
          case Some(s) => {
            val args = convertToArgArray(children)

            Class
              .forName(s.toString)
              .getConstructors()(0)
              .newInstance(args: _*)
          }
        }
      }
    }
  }

  def getInstanceForLeaf(typeData: Option[Any], value: Any): Any = {
    value match {
      case () => Class.forName(typeData.get.toString).getConstructors()(0).newInstance()
      case v => castToPrimitive(typeData, v)
    }
  }

  /** converts a list of trees (both leaves and branches) to an array
   * of objects of them.
   *
   * This is a convenience method needed to interop with Java reflection
   * machinery
   *
   * @author pepegar
   */
  def convertToArgArray(list: List[Tree[Any]]): Array[Object] = {
    list.map { elem =>
      elem match {
        case Leaf(typeData, value) => castToPrimitive(typeData, value)
        case Branch(_, _) => instantiate(elem).asInstanceOf[AnyRef]
      }
    }.toArray
  }

  /** castToPrimitive unboxes objects to its primitive representations
   *
   * @author pepegar
   */
  def castToPrimitive(typeData: Option[Any], value: Any) = {
    value match {
      case v: java.lang.Integer => v.asInstanceOf[java.lang.Integer]
      case v: java.lang.Float => v.asInstanceOf[java.lang.Float]
      case v: java.lang.String => v.asInstanceOf[java.lang.String]
      case v: java.lang.Boolean => v.asInstanceOf[java.lang.Boolean]
      case v: java.lang.Byte => v.asInstanceOf[java.lang.Byte]
      case v: java.lang.Short => v.asInstanceOf[java.lang.Short]
      case v: java.lang.Long => v.asInstanceOf[java.lang.Long]
      case v: java.lang.Double => v.asInstanceOf[java.lang.Double]
      case _ => getInstanceForLeaf(typeData, value).asInstanceOf[AnyRef]
    }
  }
}
