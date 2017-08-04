package tool.data.processor

import org.apache.solr.common.SolrInputDocument

import scala.collection.immutable.HashMap

/**
  * This class is used to modify the dataType from Hbase BYTE Array to more
  * kinds of solr data type.
  */

/**
  *Two Maven pom fields are modified:
  * First: the main pom.xml under the project. A dependency sentence is added as "org.scala-lang".
  * Second: a package pom.xml under the hbase-indexer-engine. Two dependency sentences are added as "org.scala-lang"
  * and "org.scala-lang.modules". Besides, the plugin content between <build> && </build> are added content.
  * */

class DataTypeProcessor(data: scala.collection.mutable.Map[String , SolrInputDocument]){
  var contentData = data

  def dealWithInputData(): Unit = {
    val valueList = contentData.values.toList
    
  }

}
