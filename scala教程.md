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

