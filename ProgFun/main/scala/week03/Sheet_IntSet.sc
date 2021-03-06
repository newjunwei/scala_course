package week03

object Sheet_IntSet {
	val s1 = Empty                            //> s1  : week03.Empty.type = .
	var s2 = new NonEmpty(5, Empty, Empty)    //> s2  : week03.NonEmpty = {.5.}
	s2 = s2 incl 3
	s2 = s2 incl 67
	s2                                        //> res0: week03.NonEmpty = {{.3.}5{.67.}}
	
	var s3 = new NonEmpty(7, Empty, Empty)    //> s3  : week03.NonEmpty = {.7.}
	s3 = s3 incl 3
	s3 = s3 incl 5
	s3 = s3 incl 1
	s3                                        //> res1: week03.NonEmpty = {{{.1.}3{.5.}}7.}
	
	s2 union s3                               //> res2: week03.IntSet = {{{.1.}3{.5.}}7{.67.}}
}

abstract class IntSet {
  def incl(x: Int): IntSet
  def contains(x: Int): Boolean
  def union(other: IntSet): IntSet
}

object Empty extends IntSet {
  def incl(x: Int) = new NonEmpty(x, Empty, Empty)

  def contains(x: Int) = false

  def union(other: IntSet) = other

  override def toString() = "."
}

class NonEmpty(elem: Int, left: IntSet, right: IntSet) extends IntSet {
  def contains(x: Int) = {
    if (x < elem) left contains x
    else if (x > elem) right contains (x)
    else true
  }

  def incl(x: Int) = {
    if (x < elem) new NonEmpty(elem, left.incl(x), right)
    else if (x > elem) new NonEmpty(elem, left, right.incl(x))
    else this
  }

  def union(other: IntSet) = {
    /*var unionSet: IntSet = other
    if (!other.contains(elem)) unionSet = other.incl(elem)
    unionSet = left union unionSet
    unionSet = right union unionSet
    unionSet*/
    ((left union right) union other) incl elem
  }

  override def toString() = "{" + left + elem + right + "}"
}