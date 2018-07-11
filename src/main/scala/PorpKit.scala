import java.util.Properties

/**
  *
  * @author Bossli
  * @date 2018/7/10
  */
object PorpKit {

  var properties = new Properties()
  load

  def load: Unit={
      val stream = PorpKit.getClass.getClassLoader.getResourceAsStream("system.properties")
      properties.load(stream)
  }

  def get(key: String): AnyRef={
    properties.get(key)
  }

}
