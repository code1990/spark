object Base07Test {

  def main(args: Array[String]): Unit = {
    test01()
    test02()
    test03()
    test04()

  }

  //if 语句
  def test01(): Unit = {
    var x = 10
    if (x < 20) {
      println(x + 10)
    }
  }

  //if...else 语句
  def test02(): Unit = {
    var x = 10
    if (x % 2 == 0) {
      println("it is odd")
    } else {
      println("it is even")
    }
  }

  //if...else if...else 语句
  def test03(): Unit = {
    var x = 30
    if (x == 10) {
      println(10)
    } else if (x == 20) {
      println(20)
    } else if (x == 30) {
      println(30)
    } else {
      println(-1)
    }
  }

  //if...else 嵌套语句
  def test04(): Unit = {
    var x = 10
    var y = 20
    if (x == 10) {
      if (y == 20) {
        println(x + y)
      }
    }
  }
}
