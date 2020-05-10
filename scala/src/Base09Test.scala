object Base09Test {

  //Scala 方法是类的一部分
  //Scala 函数其实就是继承了 Trait 的类的对象

  //def 语句定义方法
  def m(x: Int) = x + 3

  //val 语句可以定义函数
  val f = (x: Int) => x + 3

  def test01(): Unit = {
    //直接调用 或者通过实例对象.方法名来调用
    println(addInt(1, 1))

    //方法定义
    //定义方法名称 参数名：参数类型 返回值类型
    def addInt(a: Int, b: Int): Int = {
      var sum: Int = 0
      sum = a + b
      return sum
    }
  }

  def test02(): Unit = {

    //定义一个方法m1
    //方法的参数是一个函数，该函数的返回值是int
    //该函数的参数值分别为Int
    def m1(f: (Int, Int) => Int): Int = {
      //方法的内部调用该函数
      f(2, 3)


    }

    //定义一个函数
    val f1 = (x: Int, y: Int) => x + y
    val f2 = (x: Int, y: Int) => x * y

    //定义一个与函数f2相同实现的m2
    //尝试把方法转换为函数
    def m2(x: Int, y: Int): Int = {
      return x * y
    }

    //调用方法
    println(m1(f1))
    println(m1(f2))

    //如果直接传递的是方法名称，scala相当于是把方法转成了函数
    println(m1(m2))
    //空格和下划线告诉编译器将方法m转换成函数
    val f3 = m2 _
    println(m1(f3))
  }

  def test03(): Unit = {
    printMe()

    //无返回值的方法
    //如果方法没有返回值，可以返回为 Unit，这个类似于 Java 的 void
    def printMe(): Unit = {
      println("Hello scala")
    }
  }

  def main(args: Array[String]): Unit = {
    //方法的调用
    test01()
    test02()
    test03()
    //函数必须要有参数列表，而方法可以没有参数列表
  }


}
