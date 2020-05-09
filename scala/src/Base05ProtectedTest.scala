object Base05ProtectedTest {

  def main(args: Array[String]): Unit = {
    println(123)
  }
}
package p {

  //父类
  class Super {
    //受保护的父类方法
    protected def f() {
      println("f")
    }
  }

  //子类继承父类 可以访问父类受保护的方法
  class Sub extends Super {
    f()
  }

  //同一个包下的其他的类无法访问一个类的受保护的方法
  class Other {
//    (new Super).f() //错误
  }

}