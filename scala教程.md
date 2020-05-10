# 00Scala 简介

Scala 是 Scalable Language 的简写，是一门多范式的编程语言

------

## Scala 特性

**面向对象特性**/**函数式编程**/**静态类型**/**扩展性**/**并发性**

------

## Scala Web 框架

以下列出了两个目前比较流行的 Scala 的 Web应用框架：

- [Lift 框架](http://liftweb.net/)/[Play 框架](http://www.playframework.org/)

----------------

# 01Scala 安装

## window 上安装 Scala

### 第一步：Java 设置

参考我们的[Java 开发环境配置](https://www.runoob.com/java/java-environment-setup.html)。

从 Scala 官网地址 http://www.scala-lang.org/downloads 下载 Scala 二进制包

1.设置 SCALA_HOME 变量,同上；

2.设置 Path 变量：只需要设置%SCALA_HOME%\bin;即可

3.设置 Classpath 变量："变量值"：.;%SCALA_HOME%\bin;即可

------------------

# 02Scala 基础语法


## 第一个 Scala 程序

```scala
object HelloWorld {
   /* 这是我的第一个 Scala 程序
    * 以下程序将输出'Hello World!' 
    */
   def main(args: Array[String]) {
      // 输出 Hello World  
      println("Hello, world!") 
   }
}
```
## 基本语法

Scala 基本语法需要注意以下几点：

- Scala 语句末尾的分号 ; 是可选的。
- **对象 -**  对象有属性和行为。
- **类 -** 类是对象的抽象，而对象是类的具体实例。
- **方法 -** 方法描述的基本的行为，一个类可以包含多个方法。
- **字段 -** 对象的属性通过给字段赋值来创建。
- **Scala区分大小写**
- **类名首字母大写** -   示例：*class MyFirstScalaClass*
- **方法名首字母小写** -   示例：*def myMethodName()*
- **def main(args: Array[String])** - Scala程序从main()方法开始处理，这是每一个Scala程序的强制程序入口部分。
- Scala 可以使用两种形式的标志符，字符数字和符号。
- 避免使用"$"开始的标识符，以免造成冲突。
- yield 为 Scala 中的关键字， 你必须使用  Thread.`yield`()来使用这个方法。
- Scala 类似 Java 支持单行和多行注释。
- 一行中只有空格或者带有注释，Scala 会认为其是空行，会忽略它。标记可以被空格或者注释来分割。
- Scala是面向行的语言，语句可以用分号（;）结束或换行符。
- Scala 使用 package 关键字定义包，第一种方法和 Java 一样
- Scala 使用 import 关键字引用包。
- import语句可以出现在任何地方

```scala
//默认情况下，Scala 总会引入 java.lang._ 、 scala._ 和 Predef._
import java.awt.{Color, Font}
import java.awt._  // 引入包内所有成员
// 重命名成员
import java.util.{HashMap => JavaHashMap}
// 隐藏成员
import java.util.{HashMap => _, _} // 引入了util包的所有成员，但是HashMap被隐藏了
```

---------

# 03Scala 数据类型

Scala 与 Java有着相同的数据类型，下表列出了 Scala 支持的数据类型：
上表中列出的数据类型都是对象，也就是说scala没有java中的原生类型。

Byte/Short/Int/Long/Float/Double/Char/String 字符序列
Boolean  | true或false
Unit 表示无值，和其他语言中void等同。Unit只有一个实例值，写成()。
Null null 或空引用
Nothing  | Nothing是所有其他类型的子类型。
Any  Any是所有其他类的超类
AnyRef AnyRef类是Scala里所有引用类(reference class)的基类

多行字符串用三个双引号来表示分隔符，格式为：""" ... """。

空值是 scala.Null 类型。

Scala.Null和scala.Nothing是用统一的方式处理Scala面向对象类型系统的某些"边界情况"的特殊类型。

Null类是null引用对象的类型，它是每个引用类（继承自AnyRef的类）的子类。Null不兼容值类型。

Scala 转义字符

------------------

# 04Scala 变量

声明变量实例如下：

```scala
//变量修饰符 变量名称:变量类型=变量值
var myVar : String = "Foo"//变量 myVar，我们可以修改它
var myVal : String = "Too"//常量 myVal，它是不能修改的
var myVar1 = 10//会被推断为 Int 类型
val myVal2 = "Hello, Scala!"//会被推断为 String 类型
val xmax, ymax = 100  // 多个变量声明xmax, ymax都声明为100
val pa = (40,"Foo")//声明一个元组
```

--------------

# 05Scala 访问修饰符

Scala 访问修饰符基本和Java的一样，分别有：private，protected，public。

默认情况下，

Scala 对象的访问级别都是 public。

Scala 中的 private 限定符，外层类甚至不能访问被嵌套类的私有成员。

Scala 中的 protected限定符，只允许保护成员在定义了该成员的的类的子类中被访问

------

## 私有(Private)成员

```scala
//外部类
class Outer{
    //内部类
    class Inner{
        //内部类的私有方法
    	private def f(){
            println("f")
        }
        //内部类的嵌套类 可以访问直接父类的私有方法
    	class InnerMost{
        	f() // 正确
        }
    }
    //在内部类的外部无法直接访问其私有方法
    (new Inner).f() //错误
}
```

------

## 保护(Protected)成员

```scala
package p{
//父类    
class Super{
    //受保护的父类方法
    protected def f() {
        println("f")
    }
}
//子类继承父类 可以访问父类受保护的方法   
class Sub extends Super{
    f()
}
//同一个包下的其他的类无法访问一个类的受保护的方法
class Other{
    (new Super).f() //错误
}
}
```

------

## 公共(Public)成员

```scala
class Outer {
   class Inner {
      def f() { 
          println("f")             
      }
      class InnerMost {
         f() // 正确
      }
   }
   (new Inner).f() // 正确因为 f() 是 public
}
```

作用域保护(用的少)

---------------

# 06Scala 运算符

  一个运算符是一个符号，用于告诉编译器来执行指定的数学运算和逻辑运算。

 Scala 含有丰富的内置运算符，包括以下几种类型：

- 算术运算符 +-*/% 加减乘除取余
- 关系运算符 等于大于小于
- 逻辑运算符 && || ！
- 位运算符 ~,&,|,^
- 赋值运算符

```scala
object Base06Test {

  def main(args: Array[String]): Unit = {
    test01()
    test02()
    test03()
    test04()
    test05()
  }

  //算术运算符
  def test01(): Unit = {
    val a = 10
    val b = 2
    println(a + b)
    println(a - b)
    println(a * b)
    println(a / b)
    println(a % b)

  }

  //关系运算符
  def test02(): Unit = {
    val a = 10
    val b = 11
    println(a == b)
    println(a != b)
    println(a >= b)
    println(a > b)
    println(a <= b)
    println(a < b)
  }

  //逻辑运算符
  def test03(): Unit = {
    val a = true
    val b = false
    println(a && b)
    println(a || b)
    println(!a)
  }

  //位运算符
  def test04(): Unit = {
    val a = 1
    val b = 2
    println(a & b)
    println(a | b)
    println(a ^ b)
    println(b << 2)
    println(b >> 2)
    println(b >>> 2)
  }

  //赋值运算符
  def test05(): Unit = {
    var a = 1
    var b = 2
    println(a += 1)
    println(a -= 1)
    println(a *= 1)
    println(a /= 1)
    println(a %= 1)
    println(a <<= 1)
    println(a >>= 1)
    println(a &= 1)
    println(a ^= 1)
    println(a |= 1)
  }
}

```

# 07Scala IF...ELSE 语句

```scala
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

```

# 08Scala 循环

```scala
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

```

# 09Scala 方法与函数

**scala函数基础**

```scala
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

```

**scala高级函数**

```scala
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

```

# 10Scala 闭包

```scala
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
```

# 11Scala 字符串

```scala
object Base11Test {

  def main(args: Array[String]): Unit = {
    //在 Scala 中，字符串的类型实际上是 Java String，它本身没有 String 类。

    //1创建字符串
    var str1: String = "Hello scala"
    //自动推断出字符串的类型为 String
    var str2 = "Hello scala"

    //2字符串长度
    println(str1.length())

    //3字符串连接
    println(str1.concat(str2))

    //4.创建格式化字符串
    var fs = printf("%f,%d,%s", 12.11, 11, "scala")
    println(fs)

    //5.返回指定位置的字符
    println(str1.charAt(1))

    //6.按字典顺序比较两个字符串compareTo
    println(str1.compareTo(str2))

    //7.compareToIgnoreCase不考虑大小写比较
    println(str1.compareTo("Hello Scala"))

    //8.contentEquals
    println(str1.contentEquals(new StringBuffer("Hello scala")))

    //1
    //char charAt(int index)
    //返回指定位置的字符
    //2
    //int compareTo(Object o)
    //比较字符串与对象
    //3
    //int compareTo(String anotherString)
    //按字典顺序比较两个字符串
    //4
    //int compareToIgnoreCase(String str)
    //按字典顺序比较两个字符串，不考虑大小写
    //5
    //String concat(String str)
    //将指定字符串连接到此字符串的结尾
    //6
    //boolean contentEquals(StringBuffer sb)
    //将此字符串与指定的 StringBuffer 比较。
    //7
    //static String copyValueOf(char[] data)
    //返回指定数组中表示该字符序列的 String
    //8
    //static String copyValueOf(char[] data, int offset, int count)
    //返回指定数组中表示该字符序列的 String
    //9
    //boolean endsWith(String suffix)
    //测试此字符串是否以指定的后缀结束
    //10
    //boolean equals(Object anObject)
    //将此字符串与指定的对象比较
    //11
    //boolean equalsIgnoreCase(String anotherString)
    //将此 String 与另一个 String 比较，不考虑大小写
    //12
    //byte getBytes()
    //使用平台的默认字符集将此 String 编码为 byte 序列，并将结果存储到一个新的 byte 数组中
    //13
    //byte[] getBytes(String charsetName
    //使用指定的字符集将此 String 编码为 byte 序列，并将结果存储到一个新的 byte 数组中
    //14
    //void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin)
    //将字符从此字符串复制到目标字符数组
    //15
    //int hashCode()
    //返回此字符串的哈希码
    //16
    //int indexOf(int ch)
    //返回指定字符在此字符串中第一次出现处的索引
    //17
    //int indexOf(int ch, int fromIndex)
    //返回在此字符串中第一次出现指定字符处的索引，从指定的索引开始搜索
    //18
    //int indexOf(String str)
    //返回指定子字符串在此字符串中第一次出现处的索引
    //19
    //int indexOf(String str, int fromIndex)
    //返回指定子字符串在此字符串中第一次出现处的索引，从指定的索引开始
    //20
    //String intern()
    //返回字符串对象的规范化表示形式
    //21
    //int lastIndexOf(int ch)
    //返回指定字符在此字符串中最后一次出现处的索引
    //22
    //int lastIndexOf(int ch, int fromIndex)
    //返回指定字符在此字符串中最后一次出现处的索引，从指定的索引处开始进行反向搜索
    //23
    //int lastIndexOf(String str)
    //返回指定子字符串在此字符串中最右边出现处的索引
    //24
    //int lastIndexOf(String str, int fromIndex)
    //返回指定子字符串在此字符串中最后一次出现处的索引，从指定的索引开始反向搜索
    //25
    //int length()
    //返回此字符串的长度
    //26
    //boolean matches(String regex)
    //告知此字符串是否匹配给定的正则表达式
    //27
    //boolean regionMatches(boolean ignoreCase, int toffset, String other, int ooffset, int len)
    //测试两个字符串区域是否相等
    //28
    //boolean regionMatches(int toffset, String other, int ooffset, int len)
    //测试两个字符串区域是否相等
    //29
    //String replace(char oldChar, char newChar)
    //返回一个新的字符串，它是通过用 newChar 替换此字符串中出现的所有 oldChar 得到的
    //30
    //String replaceAll(String regex, String replacement
    //使用给定的 replacement 替换此字符串所有匹配给定的正则表达式的子字符串
    //31
    //String replaceFirst(String regex, String replacement)
    //使用给定的 replacement 替换此字符串匹配给定的正则表达式的第一个子字符串
    //32
    //String[] split(String regex)
    //根据给定正则表达式的匹配拆分此字符串
    //33
    //String[] split(String regex, int limit)
    //根据匹配给定的正则表达式来拆分此字符串
    //34
    //boolean startsWith(String prefix)
    //测试此字符串是否以指定的前缀开始
    //35
    //boolean startsWith(String prefix, int toffset)
    //测试此字符串从指定索引开始的子字符串是否以指定前缀开始。
    //36
    //CharSequence subSequence(int beginIndex, int endIndex)
    //返回一个新的字符序列，它是此序列的一个子序列
    //37
    //String substring(int beginIndex)
    //返回一个新的字符串，它是此字符串的一个子字符串
    //38
    //String substring(int beginIndex, int endIndex)
    //返回一个新字符串，它是此字符串的一个子字符串
    //39
    //char[] toCharArray()
    //将此字符串转换为一个新的字符数组
    //40
    //String toLowerCase()
    //使用默认语言环境的规则将此 String 中的所有字符都转换为小写
    //41
    //String toLowerCase(Locale locale)
    //使用给定 Locale 的规则将此 String 中的所有字符都转换为小写
    //42
    //String toString()
    //返回此对象本身（它已经是一个字符串！）
    //43
    //String toUpperCase()
    //使用默认语言环境的规则将此 String 中的所有字符都转换为大写
    //44
    //String toUpperCase(Locale locale)
    //使用给定 Locale 的规则将此 String 中的所有字符都转换为大写
    //45
    //String trim()
    //删除指定字符串的首尾空白符
    //46
    //static String valueOf(primitive data type x)
    //返回指定类型参数的字符串表示形式

  }

}

```

# 12Scala 数组