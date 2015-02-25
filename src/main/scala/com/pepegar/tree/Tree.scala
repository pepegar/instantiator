package com.pepegar.tree

abstract class Tree
case class Branch[T, U](owner: T, properties: List[U]) extends Tree
case class Leaf[T](value: T) extends Tree
