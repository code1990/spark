object Base05PrivateTest {

  def main(args: Array[String]): Unit = {
    println(123)
  }
}

//外部类
class Outer {

  //内部类
  class Inner {
    //内部类的私有方法
    private def f() {
      println("f")
    }

    //内部类的嵌套类 可以访问直接父类的私有方法
    class InnerMost {
      f() // 正确
    }

  }

  //在内部类的外部无法直接访问其私有方法
  //  (new Inner).f() //错误
}