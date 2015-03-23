instantiator
============
[![Build Status](https://travis-ci.org/pepegar/instantiator.svg?branch=master)](https://travis-ci.org/pepegar/instantiator)

```Instantiator``` is a library that comes to help you testing your Scala code.
It is able to instantiate any scala class with just a line of code:

```scala
import com.pepegar.Instantiator

class MyClass(paramOne: Int, paramTwo: String)

val myInstance = Instantiator.createInstance[MyClass]
```

But that is not the best point of ```Instantiator```.  It can create instances
of really big classes, for which creating a generator would be a pain in the
ass.  For example, take a look at the following classes, particularly
```HellOnEarth```:

```scala
import com.pepegar.Instantiator

case class A()
case class B()
case class C()
case class OtherClass(a:A, b: B, c: C)
case class HellOnEarth(
	a: Int,
	b: String,
	c: Double,
	d: OtherClass,
	e: A,
	f: B,
	g: C,
	h: Int,
	i: String,
	j: Double,
	k: OtherClass,
	l: A,
	m: B,
	n: C,
	o: Int,
	p: String,
	q: Double,
	s: OtherClass,
	t: A,
	u: B,
	v: C,
	w: Int,
	x: String,
	y: Double,
	z: OtherClass
	)

val superShinyObject = Instantiator.createInstance[HellOnEarth]
```

And that's it, you have a perfect ```superShinyObject``` that you can pass around in your tests.

How it works?
-------------
```Instantiator``` uses the Scala reflection API to inspect the types of the
properties and then generates random data for them based on their types.

First of all, it generates a Types tree based on the classname you pass to
createInstance.  In charge of generating the Types tree is the [```TypesTreeMaker``` trait](https://github.com/pepegar/instantiator/blob/master/src/main/scala/com/pepegar/instantiator/utils/TypesTreeMaker.scala)

After that, we generate random values for that types using the Scala Random
API.  That is done by the [```TypesToValuesMapper``` trait](https://github.com/pepegar/instantiator/blob/master/src/main/scala/com/pepegar/instantiator/utils/TypesToValuesMapper.scala).

The last step in the process is parsing the Values tree to get the instance of
the class.  It is done in the [```ValueTreeParser``` trait](https://github.com/pepegar/instantiator/blob/master/src/main/scala/com/pepegar/instantiator/utils/ValueTreeParser.scala)

All these traits are mixed into the ```Instantiator``` object, which is the
entry point of the library.

Restrictions
------------

* Right now, ```Instantiator``` will use the first constructor it will found, so
it's better not to try to instantiat classes with more than one constructor.

* It is not tested against collections.  I don't know what approach to follow in
in the number of elements to generate for the collection.  Probably a random
integer from 1 to 100 would be enough.

* Type erasure.  In the JVM, type parameters are erased at compile time, and
this library runs at runtime.  What the f*ck can I do with that?  I mean, it is
possible to kind of infer the inner type of any parametric data type by
inspecting one element of it and getting it type.  However, that raises two more
questions:
  - The type of that element may lead you to a wrong type, for example:
  ```
  val a: List[Any] = List(1)
  ```
  if we inspect the type of the element in the list, we can think that this list
  would be a ```List[Int]```, or a ```List[Number]```...  Noooope.
  - I don't know any element of the parametric data type BECAUSE IS MY JOB TO
  GENERATE IT...
