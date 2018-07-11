import java.sql.{Connection, DriverManager}

/**
  *
  * @author Bossli
  * @date 2018/7/10
  */
object DBKit {

  def getConn: Connection = {

    val url = PorpKit.get("jdbc.url").toString
    val username = PorpKit.get("jdbc.username").toString
    val password = PorpKit.get("jdbc.password").toString
    val connection = DriverManager.getConnection(url, username, password)
    connection
  }

  def close(conn: Connection): Unit = {
    try {
      if (null != conn && !conn.isClosed) {
        conn.close()
      }
    } catch {
      case e: Throwable => {
        e.printStackTrace()
      }
    }
  }
}
