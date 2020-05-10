object Base10Test {

  def main(args: Array[String]): Unit = {
    test01()
  }

  def test01(): Unit = {
    //  闭包:可以访问一个函数里面局部变量的另外一个函数。
    //匿名的函数
    val multiplier1 = (i: Int) => i * 10
    //定义一个闭包 因为 引用了函数外面定义的变量
    var factor = 3
    val multiplier = (i: Int) => i * factor

    println(multiplier1(10))
    println(multiplier(10))
  }

}
