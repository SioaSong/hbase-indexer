package tool.data.processor

import java.text.SimpleDateFormat
import java.util
import java.util.{Calendar, Locale}

import com.ngdata.hbaseindexer.indexer.RowData
import org.apache.solr.common.SolrInputDocument

import scala.collection.JavaConverters
import scala.util.matching.Regex

/**
  * This class is used to modify the dataType from Hbase BYTE Array to more
  * kinds of solr data type.
  * */

/**
  *Two Maven pom fields are modified:
  * First: the main pom.xml under the project. A dependency sentence is added as "org.scala-lang".
  * Second: a package pom.xml under the hbase-indexer-engine. Two dependency sentences are added as "org.scala-lang"
  * and "org.scala-lang.modules". Besides, the plugin content between <build> && </build> are added content.
  * */

/**
  * Indexer.java is modified to adjust the function of this Class.(In function indexRowData)
  * */

class DataTypeProcessor(rowDataList: util.List[RowData]){

  var dataList: util.List[RowData] = rowDataList
  var contentData: scala.collection.mutable.Map[String , SolrInputDocument] = null
  val timePattern: Regex = "(\\d{4}(-|/)\\d{2}(-|/)\\d{2}\\s\\d{2}:\\d{2}:\\d{2}\\s?)".r

  def this(data: scala.collection.mutable.Map[String , SolrInputDocument], dataList: util.List[RowData]) {
    this(dataList)
    contentData = data
  }

  def dealWithInputData(): scala.collection.mutable.Map[String , SolrInputDocument] = {
    var modifiedDataMap : scala.collection.mutable.Map[String , SolrInputDocument] = null
    contentData.foreach(item => {
      val name = item._1
      val document = new SolrInputDocument
      val keyNames = item._2.getFieldNames
      
      for(name : String <- keyNames){
        val itemValue = item._2.getFieldValue(name.toString).toString
        if(timePattern.findAllIn(itemValue).nonEmpty){
          val formatDateTime = Convert2Solr(itemValue,"yyyy-MM-dd HH:mm:ss")
          document.addField(name, formatDateTime)
        }else{
          document.addField(name, item._2.getFieldValue(name.toString))
        }
      }
      modifiedDataMap += (name -> document)
    })
    return modifiedDataMap
  }

  def dealWithRowData(rowDataList: util.List[RowData]) : Unit = {
    /*
    * This function is waiting for completed, which is used to deal with
    * the original data that is got from hbase and used to add indexer in
    * solr.
    * */
    val valueList = rowDataList
    for(item: RowData <- valueList){
      item.getRow
    }
  }

  def Convert2Solr(date: String, format: String): String = {
    var value = ""
    try {
      val fmt = new SimpleDateFormat(format)
      val _date = fmt.parse(date)
      val calendar = Calendar.getInstance
      calendar.setTime(_date)
      calendar.add(10, -8)
      val df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ROOT)
      value = df.format(calendar.getTime)
    } catch {
      case var7: Exception =>
    }
    value
  }

}
