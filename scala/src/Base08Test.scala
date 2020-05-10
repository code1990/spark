object Base08Test {

  def main(args: Array[String]): Unit = {
    test01()
    test02()
    test03()
    test04()
    test05()
    test06()
    test07()
    test08()
    test09()
    test10()

  }

  //while 循环
  def test01(): Unit = {
    var a = 10
    while (a < 20) {
      println(a)
      a = a + 1
    }
  }

  //do...while 循环
  def test02(): Unit = {
    var a = 10
    do {
      println(a)
      a = a + 1
    } while (a < 20)
  }

  //for循环 i to j 表示包含j
  def test03(): Unit = {
    var a = 0
    for (a <- 1 to 10) {
      println(a)
    }
  }

  //i util j 表示不包含j
  def test04(): Unit = {
    var a = 0
    for (a <- 1 until 10) {
      println(a)
    }
  }

  //双重for循环
  def test05(): Unit = {
    var a = 0
    var b = 1
    for (a <- 1 to 3; b <- 1 until 3) {
      println("+++++++++++++")
      //a 循环一次 b循环多次
      println(a + "\t" + b)
    }
  }

  //遍历List集合
  def test06(): Unit = {
    // a 先初始化 a 表示集合里面的每一个元素
    var a = 0
    var numberList = List(1, 2, 3, 4, 5)
    for (a <- numberList) {
      println(a)
    }
  }

  //for 循环过滤
  def test07(): Unit = {
    var a = 0
    var numberList = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    for (a <- numberList; if a != 3; if a < 8) {
      println(a)
    }
  }

  // 使用 yield 获取循环的返回值
  def test08(): Unit = {
    var a = 0
    var numberList = List(1, 2, 3, 4, 5)
    var retVal = for (a <- numberList; if a != 3) yield a
    println(retVal)
  }

  //Scala 不支持 break 或 continue 语句
  def test09(): Unit = {
    // 导入以下包
    import scala.util.control._
    // 创建 Breaks 对象
    val loop = new Breaks()
    // 在 breakable 中循环
    loop.breakable {
      // 循环
      var a = 0
      for (a <- 1 until 10) {
        if (a == 5) {
          // 循环中断
          loop.break()
        }
        println(a)
      }
    }
  }

  //中断嵌套循环
  def test10(): Unit = {
    import scala.util.control._
    val outer = new Breaks()
    val inner = new Breaks()
    var a = 0
    var b = 0
    outer.breakable {
      for (a <- 1 to 10) {
        inner.breakable {
          for (b <- 1 to 10) {
            if (b == 5) {
              inner.break()
            }
            println(a + "\t" + b)
          }
        }

      }
    }
  }

  //无限循环
  def test11(): Unit = {
    var a = 10
    while (true) {
      println(a)
    }
  }
}
