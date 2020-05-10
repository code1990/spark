object Base13ListTest {

  def main(args: Array[String]): Unit = {
    //Scala 列表类似于数组，它们所有元素的类型都相同
    test01()
    test02()
    test03()
    test04()
    test05()
    test06()

    //1
    //
    //def +:(elem: A): List[A]
    //
    //为列表预添加元素
    //
    //scala> val x = List(1)
    //x: List[Int] = List(1)
    //
    //scala> val y = 2 +: x
    //y: List[Int] = List(2, 1)
    //
    //scala> println(x)
    //List(1)
    //
    //2
    //
    //def ::(x: A): List[A]
    //
    //在列表开头添加元素
    //3
    //
    //def :::(prefix: List[A]): List[A]
    //
    //在列表开头添加指定列表的元素
    //4
    //
    //def :+(elem: A): List[A]
    //
    //复制添加元素后列表。
    //
    //scala> val a = List(1)
    //a: List[Int] = List(1)
    //
    //scala> val b = a :+ 2
    //b: List[Int] = List(1, 2)
    //
    //scala> println(a)
    //List(1)
    //
    //5
    //
    //def addString(b: StringBuilder): StringBuilder
    //
    //将列表的所有元素添加到 StringBuilder
    //6
    //
    //def addString(b: StringBuilder, sep: String): StringBuilder
    //
    //将列表的所有元素添加到 StringBuilder，并指定分隔符
    //7
    //
    //def apply(n: Int): A
    //
    //通过列表索引获取元素
    //8
    //
    //def contains(elem: Any): Boolean
    //
    //检测列表中是否包含指定的元素
    //9
    //
    //def copyToArray(xs: Array[A], start: Int, len: Int): Unit
    //
    //将列表的元素复制到数组中。
    //10
    //
    //def distinct: List[A]
    //
    //去除列表的重复元素，并返回新列表
    //11
    //
    //def drop(n: Int): List[A]
    //
    //丢弃前n个元素，并返回新列表
    //12
    //
    //def dropRight(n: Int): List[A]
    //
    //丢弃最后n个元素，并返回新列表
    //13
    //
    //def dropWhile(p: (A) => Boolean): List[A]
    //
    //从左向右丢弃元素，直到条件p不成立
    //14
    //
    //def endsWith[B](that: Seq[B]): Boolean
    //
    //检测列表是否以指定序列结尾
    //15
    //
    //def equals(that: Any): Boolean
    //
    //判断是否相等
    //16
    //
    //def exists(p: (A) => Boolean): Boolean
    //
    //判断列表中指定条件的元素是否存在。
    //
    //判断l是否存在某个元素:
    //
    //scala> l.exists(s => s == "Hah")
    //res7: Boolean = true
    //
    //17
    //
    //def filter(p: (A) => Boolean): List[A]
    //
    //输出符号指定条件的所有元素。
    //
    //过滤出长度为3的元素:
    //
    //scala> l.filter(s => s.length == 3)
    //res8: List[String] = List(Hah, WOW)
    //
    //18
    //
    //def forall(p: (A) => Boolean): Boolean
    //
    //检测所有元素。
    //
    //例如：判断所有元素是否以"H"开头：
    //scala> l.forall(s => s.startsWith("H")) res10: Boolean = false
    //19
    //
    //def foreach(f: (A) => Unit): Unit
    //
    //将函数应用到列表的所有元素
    //20
    //
    //def head: A
    //
    //获取列表的第一个元素
    //21
    //
    //def indexOf(elem: A, from: Int): Int
    //
    //从指定位置 from 开始查找元素第一次出现的位置
    //22
    //
    //def init: List[A]
    //
    //返回所有元素，除了最后一个
    //23
    //
    //def intersect(that: Seq[A]): List[A]
    //
    //计算多个集合的交集
    //24
    //
    //def isEmpty: Boolean
    //
    //检测列表是否为空
    //25
    //
    //def iterator: Iterator[A]
    //
    //创建一个新的迭代器来迭代元素
    //26
    //
    //def last: A
    //
    //返回最后一个元素
    //27
    //
    //def lastIndexOf(elem: A, end: Int): Int
    //
    //在指定的位置 end 开始查找元素最后出现的位置
    //28
    //
    //def length: Int
    //
    //返回列表长度
    //29
    //
    //def map[B](f: (A) => B): List[B]
    //
    //通过给定的方法将所有元素重新计算
    //30
    //
    //def max: A
    //
    //查找最大元素
    //31
    //
    //def min: A
    //
    //查找最小元素
    //32
    //
    //def mkString: String
    //
    //列表所有元素作为字符串显示
    //33
    //
    //def mkString(sep: String): String
    //
    //使用分隔符将列表所有元素作为字符串显示
    //34
    //
    //def reverse: List[A]
    //
    //列表反转
    //35
    //
    //def sorted[B >: A]: List[A]
    //
    //列表排序
    //36
    //
    //def startsWith[B](that: Seq[B], offset: Int): Boolean
    //
    //检测列表在指定位置是否包含指定序列
    //37
    //
    //def sum: A
    //
    //计算集合元素之和
    //38
    //
    //def tail: List[A]
    //
    //返回所有元素，除了第一个
    //39
    //
    //def take(n: Int): List[A]
    //
    //提取列表的前n个元素
    //40
    //
    //def takeRight(n: Int): List[A]
    //
    //提取列表的后n个元素
    //41
    //
    //def toArray: Array[A]
    //
    //列表转换为数组
    //42
    //
    //def toBuffer[B >: A]: Buffer[B]
    //
    //返回缓冲区，包含了列表的所有元素
    //43
    //
    //def toMap[T, U]: Map[T, U]
    //
    //List 转换为 Map
    //44
    //
    //def toSeq: Seq[A]
    //
    //List 转换为 Seq
    //45
    //
    //def toSet[B >: A]: Set[B]
    //
    //List 转换为 Set
    //46
    //
    //def toString(): String
    //
    //列表转换为字符串
  }

  def test01(): Unit = {
    //字符串列表
    val site: List[String] = List("baidu", "Google")

    //整数列表
    val nums: List[Int] = List(1, 2, 3, 4)

    //空列表
    val empty: List[Nothing] = List()

    //二维列表
    val dim: List[List[Int]] =
      List(
        List(1, 0, 0),
        List(0, 1, 0),
        List(0, 0, 1)
      )
  }

  def test02(): Unit = {
    //构造列表的两个基本单位是 Nil 和 ::
    //Nil 也可以表示为一个空列表。

    //字符串列表
    val site = "qq" :: ("google" :: ("baidu" :: Nil))
    //整形列表
    val nums = 1 :: (2 :: (3 :: Nil))
    //空列表
    val empty = Nil
    //二维列表
    val dim = (1 :: (0 :: (0 :: Nil))) ::
      (0 :: (1 :: (0 :: Nil))) ::
      (0 :: (0 :: (1 :: Nil))) :: Nil

  }

  def test03(): Unit = {
    //    head 返回列表第一个元素
    //    tail 返回一个列表，包含除了第一元素之外的其他元素
    //    isEmpty 在列表为空时返回true
    val site = List("baidu", "google")
    val nums = Nil
    println(site.head)
    println(site.tail)
    println(site.isEmpty)
    println(nums.isEmpty)
  }

  def test04(): Unit = {
    //连接列表
    //你可以使用 ::: 运算符或 List.:::() 方法或 List.concat() 方法来连接两个或多个列表
    val site1 = List("baidu")
    val site2 = List("google")
    //使用 concat 方法
    println(List.concat(site1, site2))
    //// 使用 ::: 运算符
    println(site1 ::: site2)
    // 使用 List.:::() 方法
    println(site1.:::(site2))
  }

  def test05(): Unit = {
    // List.fill() 方法来创建一个指定重复数量的元素列表
    val site = List.fill(3)("baidu") // 重复 baidu 3次
    println(site)
    val num = List.fill(10)(2) // 重复元素 2, 10 次
    println(num)
  }

  def test06(): Unit = {
    //List.tabulate() 方法是通过给定的函数来创建列表
    val squares = List.tabulate(6)(n => n * n) // 6个元素 每一个元素平方
    println(squares)
    //List(0, 1, 4, 9, 16, 25)
    val mul = List.tabulate(4, 5)(_ * _)
    println(mul)
    //List(List(0, 0, 0, 0, 0), List(0, 1, 2, 3, 4), List(0, 2, 4, 6, 8), List(0, 3, 6, 9, 12))

    //List.reverse 用于将列表的顺序反转
    val site = List("baidu", "google")
    println(site.reverse)
  }
}