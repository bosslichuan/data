import java.io.File

import scala.io.Source
import scala.util.Try

/**
  *
  * @author Bossli
  * @date 2018/7/10
  */
object FileKit {


  /**
    * csv文件读取解析到集合
    *
    * @param csvFile
    * @return
    */
  def loadFile(csvFile: File): Seq[Map[String, Any]] = {

    val file = Source.fromFile(csvFile, "utf-8")
    val fileName = csvFile.getName().substring(0, csvFile.getName().lastIndexOf("."))


    file.getLines().flatMap(l => {
      val line = l.split(",")
      val stock_code = Try(line(0)).getOrElse("")
      val item1 = Try(line(1)).getOrElse(0)
      val item2 = Try(line(2)).getOrElse(0)
      val item3 = Try(line(3)).getOrElse(0)

      Seq(
        Map(
          "item_id" -> "uuid1",
          "date" -> fileName,
          "stock_code" -> stock_code,
          "item_value" -> item1
        ),
        Map(
          "item_id" -> "uuid2",
          "date" -> fileName,
          "stock_code" -> stock_code,
          "item_value" -> item2
        ),
        Map(
          "item_id" -> "uuid3",
          "date" -> fileName,
          "stock_code" -> stock_code,
          "item_value" -> item3
        )
      )
    }).toSeq
  }

  /**
    * 保存数据导mysql数据库
    *
    * @param sql
    */
  def saveMysql(sql: String): Unit = {
    val conn = DBKit.getConn
    conn.prepareStatement(sql).executeUpdate()
    DBKit.close(conn)
  }


  def main(args: Array[String]): Unit = {
    println("hello world")
    println(PorpKit.get("jdbc.username"))


    val csvFile = new File("C:\\Users\\Bossli\\Desktop\\2018-07-11.csv")
    if (null == csvFile || !csvFile.exists()) {
      System.exit(1)
    }

    val vals = loadFile(csvFile).map(l => {

      val itemId = l.get("item_id").get
      val date = l.get("date").get
      val stock_code = l.get("stock_code").get
      val item_value = l.get("item_value").get
      val str =
        s"""
           |( '${itemId}', '${date}', ${stock_code}, ${item_value})
       """.stripMargin
      str
    }).mkString(",")

    val sql =
      s"""
         |insert into time_series_data (item_id ,trading_date ,stock_code ,item_value ) values
         | ${vals}
       """.stripMargin

    println(sql)

    saveMysql(sql)
  }
}
