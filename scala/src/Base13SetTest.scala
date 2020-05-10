object Base13SetTest {

  def main(args: Array[String]): Unit = {
    //Scala Set(集合)是没有重复的对象集合，所有的元素都是唯一的。
    //
    //Scala 集合分为可变的和不可变的集合。
    //
    //默认情况下，Scala 使用的是不可变集合，
    // 如果你想使用可变集合，需要引用 scala.collection.mutable.Set 包。
    //
    //默认引用 scala.collection.immutable.Set
    test01()
    test02()
    test03()
    test04()
    test05()
    test06()

    //1
    //
    //def +(elem: A): Set[A]
    //
    //为集合添加新元素，x并创建一个新的集合，除非元素已存在
    //2
    //
    //def -(elem: A): Set[A]
    //
    //移除集合中的元素，并创建一个新的集合
    //3
    //
    //def contains(elem: A): Boolean
    //
    //如果元素在集合中存在，返回 true，否则返回 false。
    //4
    //
    //def &(that: Set[A]): Set[A]
    //
    //返回两个集合的交集
    //5
    //
    //def &~(that: Set[A]): Set[A]
    //
    //返回两个集合的差集
    //6
    //
    //def +(elem1: A, elem2: A, elems: A*): Set[A]
    //
    //通过添加传入指定集合的元素创建一个新的不可变集合
    //7
    //
    //def ++(elems: A): Set[A]
    //
    //合并两个集合
    //8
    //
    //def -(elem1: A, elem2: A, elems: A*): Set[A]
    //
    //通过移除传入指定集合的元素创建一个新的不可变集合
    //9
    //
    //def addString(b: StringBuilder): StringBuilder
    //
    //将不可变集合的所有元素添加到字符串缓冲区
    //10
    //
    //def addString(b: StringBuilder, sep: String): StringBuilder
    //
    //将不可变集合的所有元素添加到字符串缓冲区，并使用指定的分隔符
    //11
    //
    //def apply(elem: A)
    //
    //检测集合中是否包含指定元素
    //12
    //
    //def count(p: (A) => Boolean): Int
    //
    //计算满足指定条件的集合元素个数
    //13
    //
    //def copyToArray(xs: Array[A], start: Int, len: Int): Unit
    //
    //复制不可变集合元素到数组
    //14
    //
    //def diff(that: Set[A]): Set[A]
    //
    //比较两个集合的差集
    //15
    //
    //def drop(n: Int): Set[A]]
    //
    //返回丢弃前n个元素新集合
    //16
    //
    //def dropRight(n: Int): Set[A]
    //
    //返回丢弃最后n个元素新集合
    //17
    //
    //def dropWhile(p: (A) => Boolean): Set[A]
    //
    //从左向右丢弃元素，直到条件p不成立
    //
    //18
    //
    //def equals(that: Any): Boolean
    //
    //equals 方法可用于任意序列。用于比较系列是否相等。
    //19
    //
    //def exists(p: (A) => Boolean): Boolean
    //
    //判断不可变集合中指定条件的元素是否存在。
    //20
    //
    //def filter(p: (A) => Boolean): Set[A]
    //
    //输出符合指定条件的所有不可变集合元素。
    //21
    //
    //def find(p: (A) => Boolean): Option[A]
    //
    //查找不可变集合中满足指定条件的第一个元素
    //22
    //
    //def forall(p: (A) => Boolean): Boolean
    //
    //查找指定条件是否适用于此集合的所有元素
    //23
    //
    //def foreach(f: (A) => Unit): Unit
    //
    //将函数应用到不可变集合的所有元素
    //24
    //
    //def head: A
    //
    //获取不可变集合的第一个元素
    //25
    //
    //def init: Set[A]
    //
    //返回所有元素，除了最后一个
    //26
    //
    //def intersect(that: Set[A]): Set[A]
    //
    //计算两个集合的交集
    //27
    //
    //def isEmpty: Boolean
    //
    //判断集合是否为空
    //28
    //
    //def iterator: Iterator[A]
    //
    //创建一个新的迭代器来迭代元素
    //29
    //
    //def last: A
    //
    //返回最后一个元素
    //30
    //
    //def map[B](f: (A) => B): immutable.Set[B]
    //
    //通过给定的方法将所有元素重新计算
    //31
    //
    //def max: A
    //
    //查找最大元素
    //32
    //
    //def min: A
    //
    //查找最小元素
    //33
    //
    //def mkString: String
    //
    //集合所有元素作为字符串显示
    //34
    //
    //def mkString(sep: String): String
    //
    //使用分隔符将集合所有元素作为字符串显示
    //35
    //
    //def product: A
    //
    //返回不可变集合中数字元素的积。
    //36
    //
    //def size: Int
    //
    //返回不可变集合元素的数量
    //37
    //
    //def splitAt(n: Int): (Set[A], Set[A])
    //
    //把不可变集合拆分为两个容器，第一个由前 n 个元素组成，第二个由剩下的元素组成
    //38
    //
    //def subsetOf(that: Set[A]): Boolean
    //
    //如果集合中含有子集返回 true，否则返回false
    //39
    //
    //def sum: A
    //
    //返回不可变集合中所有数字元素之和
    //40
    //
    //def tail: Set[A]
    //
    //返回一个不可变集合中除了第一元素之外的其他元素
    //41
    //
    //def take(n: Int): Set[A]
    //
    //返回前 n 个元素
    //42
    //
    //def takeRight(n: Int):Set[A]
    //
    //返回后 n 个元素
    //43
    //
    //def toArray: Array[A]
    //
    //将集合转换为数组
    //44
    //
    //def toBuffer[B >: A]: Buffer[B]
    //
    //返回缓冲区，包含了不可变集合的所有元素
    //45
    //
    //def toList: List[A]
    //
    //返回 List，包含了不可变集合的所有元素
    //46
    //
    //def toMap[T, U]: Map[T, U]
    //
    //返回 Map，包含了不可变集合的所有元素
    //47
    //
    //def toSeq: Seq[A]
    //
    //返回 Seq，包含了不可变集合的所有元素
    //48
    //
    //def toString(): String
    //
    //返回一个字符串，以对象来表示
  }

  def test01(): Unit = {
    val set = Set(1, 2, 3)
    println(set.getClass.getName)

    println(set.exists(_ % 2 == 0))

    println(set.drop(1))
  }

  def test02(): Unit = {
    //对可变Set进行操作，改变的是该Set本身，与ListBuffer类似。
    import scala.collection.mutable.Set // 可以在任何地方引入 可变集合
    val mutableSet = Set(1, 2, 3)
    println(mutableSet.getClass.getName)
    mutableSet.add(4)
    mutableSet.remove(1)
    mutableSet += 5
    mutableSet -= 2

    println(mutableSet)
    val another = mutableSet.toSet
    println(another.getClass.getName)
  }

  def test03(): Unit = {
    //    head 返回集合第一个元素
    //    tail 返回一个集合，包含除了第一元素之外的其他元素
    //    isEmpty 在集合为空时返回true
    val site = Set("baidu", "Google")
    val nums: Set[Int] = Set()
    println(site.head)
    println(site.tail)
    println(site.isEmpty)
    println(nums.isEmpty)
  }

  def test04(): Unit = {
    // ++ 运算符或 Set.++() 方法来连接两个集合。如果元素有重复的就会移除重复的元素
    val site1 = Set("baidu", "google")
    val site2 = Set("baidu", "qq")
    //++ 作为运算符使用
    println(site1 ++ site2)
    //  ++ 作为方法使用
    println(site1.++(site2))
  }

  def test05(): Unit = {
    //Set.min 方法来查找集合中的最小元素
    // Set.max 方法查找集合中的最大元素
    val num = Set(1, 2, 3, 4, 5, 6, 7, 8)
    println(num.min)
    println(num.max)

  }

  def test06(): Unit = {
    //Set.& 方法或 Set.intersect 方法来查看两个集合的交集元素
    val num1 = Set(1, 2, 3)
    val num2 = Set(0, 2, 3)
    println(num1.&(num2))
    println(num1.intersect(num2))
  }
}