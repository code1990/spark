object Base13TupleTest {

  def main(args: Array[String]): Unit = {
    //元组可以包含不同类型的元素。
    //tuple2 _1(), _2()获取值
    //Tuple3 Tuple4 分别表示存放多少个元素 最大21
    test01()
    test02()
  }

  def test01(): Unit = {
    val t = (1, 3.14, "test")
    println(t)
    val t2 = new Tuple3(1, 2, 3)
    println(t2._1)
    println(t2._2)
    println(t2._3)
  }

  def test02(): Unit = {
    //Tuple.productIterator() 方法来迭代输出元组的所有元素
    val t = (1, 2, 3, 4, 5)
    t.productIterator.foreach(t => println(t))
    //Tuple.toString() 方法将元组的所有元素组合成一个字符串
    println(t.toString())
    //Tuple.swap 方法来交换元组的元素
    println((1,2).swap)
  }


}