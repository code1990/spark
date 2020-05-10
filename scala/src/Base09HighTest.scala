import java.util.Date

object Base09HighTest {


  def main(args: Array[String]): Unit = {
    test01() //函数传名调用
    test02() // 指定函数参数名
    test03() //函数 - 可变参数
    test04() // 递归函数
    test05() //默认参数值
    test06() // 高阶函数
    test07() //内嵌函数
    test08() // 	匿名函数
    test09() //偏应用函数
    test10() // 函数柯里化(Function Currying)
  }

  def test01(): Unit = {
    //传值调用：先计算参数表达式的值，再应用到函数内部；
    //传名调用：将未计算的参数表达式直接应用到函数内部
    delayed(time())


    def time(): Long = {
      println("获取时间，单位为纳秒")
      System.nanoTime()
    }

    //使用 => 符号来设置传名调用
    def delayed(t: => Long) = {
      println("在delayed方法内，参数" + t)
      t
    }
  }

  def test02(): Unit = {
    //指定函数参数名来传递参数
    printInt(b = 2, a = 1)
    //而不是通过参数顺序来传递参数
    printInt(2, 1)

    //
    def printInt(a: Int, b: Int) = {
      println(a + "\t" + b)
    }
  }

  def test03(): Unit = {
    //在参数的类型之后放一个星号来设置可变参数
    printStrings("java", "scala", "kotlin")

    def printStrings(args: String*): Unit = {
      var i: Int = 0
      for (arg <- args) {
        println("i" + i + "\t" + arg)
        i = i + 1
      }
    }
  }

  def test04(): Unit = {
    //递归函数意味着函数可以调用它本身。
    factorial(4)

    //计算阶乘
    def factorial(n: BigInt): BigInt = {
      if (n <= 1) {
        1
      } else {
        n * factorial(n - 1)
      }
    }
  }

  def test05(): Unit = {
    //Scala 可以为函数参数指定默认参数值，使用了默认参数，你在调用函数的过程中可以不需要传递参数

    //不传递参数 使用默认参数
    println(addInt())
    //传递参数 参数覆盖默认参数
    println(addInt(1, 2))
    //无默认值的参数在前，默认参数在后
    println(addInt2(2))

    def addInt(a: Int = 10, b: Int = 20): Int = {
      var sum: Int = 0
      sum = a + b
      return sum
    }

    //包含普通参数时，无默认值的参数在前，默认参数在后
    def addInt2(c: Int, a: Int = 10, b: Int = 20): Int = {
      var sum: Int = 0
      sum = a + b + c
      return sum
    }
  }

  def test06(): Unit = {
    //高阶函数可以使用其他函数作为参数，或者使用函数作为输出结果。
    println(apply(layout, 10))

    // 函数 f 和 值 v 作为参数，而函数 f 又调用了参数 v
    //下面函数的意思就是传递一个Int类型的参数v 返回一个String类型
    def apply(f: Int => String, v: Int) = f(v)

    //传递一个参数 把传递打印出来
    def layout[A](x: A) = "[" + x.toString() + "]"
  }

  def test07(): Unit = {
    //Scala 函数内定义函数，定义在函数内的函数称之为局部函数。
    def factorial(i: Int): Int = {
      def fact(i: Int, accumulator: Int): Int = {
        if (i <= 1) {
          accumulator
        } else {
          fact(i - 1, i * accumulator)
        }
      }

      fact(i, 1)
    }

    factorial(3)

  }

  def test08(): Unit = {
    //Scala 中定义匿名函数的语法很简单，箭头左边是参数列表，右边是函数体。
    var inc = (x: Int) => x + 1
    var mul = (x: Int, y: Int) => x * y
    println(inc(7))
    println(mul(7, 2))
  }

  def test09(): Unit = {
    //Scala 偏应用函数是一种表达式，你不需要提供函数需要的所有参数，只需要提供部分，或不提供所需参数。

    def log(date: Date, message: String): Unit = {
      print(date + "\t" + message)
    }


    val date = new Date()
    log(date, "message")
    //使用偏应用函数优化以上方法
    //下划线(_)替换缺失的参数列表
    //把这个新的函数值的索引的赋给变量
    val logWithDateBound = log(date, _: String)
    logWithDateBound("message")
  }

  def test10(): Unit = {
    //柯里化(Currying)指的是将原来接受两个参数的函数变成新的接受一个参数的函数的过程。
    // 新的函数返回一个以原有第二个参数为参数的函数。
    def add(x: Int, y: Int) = x + y

    //add(1)(2),最后结果都一样是3，这种方式（过程）就叫柯里化。
    def add2(x: Int)(y: Int) = x + y

    //实质上最先演变成这样一个方法：
    def add3(x: Int) = (y: Int) => x + y

    println(add(1, 2))
    println(add2(1)(2))
    println(add3(1)(2))
  }

}
