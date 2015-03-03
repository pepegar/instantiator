package com.pepegar.instantiator.tree

abstract class Tree[T] {
  def scan[U](fn: T => U): Tree[U]
}

case class Branch[T](children: List[Tree[T]]) extends Tree[T] {
  def scan[U](f: T => U): Tree[U] = {
    def treeToTree(tree: Tree[T], fn: T => U): Tree[U] = {
      tree match {
        case Leaf(v) => Leaf(fn(v))
        case Branch(ch) => {
          val newProps = ch.map { t =>
            treeToTree(t, f)
          }

          Branch(newProps)
        }
      }
    }

    val newProperties = children.map { tree =>
      treeToTree(tree, f)
    }

    Branch(newProperties)
  }
}

case class Leaf[T](value: T) extends Tree[T] {
  def scan[U](fn: T => U): Tree[U] = {
    Leaf(fn(value))
  }
}
