package com.pepegar.instantiator.utils

import com.pepegar.instantiator.tree._

trait ValueTreeParser {

  def instantiate(valueTree: Tree[Any]): Any = {
    valueTree match {
      case Leaf(_, value) => value
      case Branch(typeData, children) => {
        typeData match {
          case None => None
          case Some(s) =>{
            Class
              .forName(s.toString)
              .getConstructors()(0)
              .newInstance(1: java.lang.Integer, 2: java.lang.Integer)
          }
        }
      }
    }
  }

}
