instantiator
============
_Disclaimer: this is non functional yet._

So, you have that problem again, you need to instantiate a huge class
in your tests, but you don't care too much about the values of its properties.

Just use Instantiator!

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
Instantiator uses the Scala reflection API to inspect the types of the properties and then generates
random data for them based on their types.
