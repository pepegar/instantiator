package com.pepegar.instantiator.tree

abstract class Tree[T](metadata: Option[Any]) {
  def lift[U](fn: T => U): Tree[U]
}

case class Branch[T](metadata: Option[Any], children: List[Tree[T]]) extends Tree[T](metadata) {
  def lift[U](f: T => U): Tree[U] = {
    def treeToTree(tree: Tree[T], fn: T => U): Tree[U] = {
      tree match {
        case Leaf(_, v) => Leaf(None, fn(v))
        case Branch(_, ch) => {
          val newProps = ch.map { t =>
            treeToTree(t, f)
          }

          Branch(metadata, newProps)
        }
      }
    }

    val newProperties = children.map { tree =>
      treeToTree(tree, f)
    }

    Branch(metadata, newProperties)
  }
}

case class Leaf[T](metadata: Option[Any], value: T) extends Tree[T](metadata) {
  def lift[U](fn: T => U): Tree[U] = {
    Leaf(metadata, fn(value))
  }
}
