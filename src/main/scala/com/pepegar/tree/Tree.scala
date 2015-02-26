package com.pepegar.tree

abstract class Tree
case class Branch[T](properties: List[T]) extends Tree {
  def map[U](f: T => U) = {
    Branch(properties.map(f))
  }
}
case class Leaf[T](value: T) extends Tree
