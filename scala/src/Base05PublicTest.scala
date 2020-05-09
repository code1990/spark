object Base05PublicTest {
  def main(args: Array[String]): Unit = {
    println("111")
  }
}

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