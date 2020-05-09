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