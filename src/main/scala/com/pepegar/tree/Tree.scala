package com.pepegar.tree

abstract class Tree
case class Branch[T, U](owner: T, properties: List[U]) extends Tree {
  def map[V](f: U => V) = {
    Branch(owner,properties.map(f))
  }
}
case class Leaf[T](value: T) extends Tree
