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
