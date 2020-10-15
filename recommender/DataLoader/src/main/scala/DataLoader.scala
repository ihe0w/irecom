import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.mongodb.scala.{Document, MongoClient, MongoCollection, MongoDatabase}

//定义数据以及数据config model
case class Post(postId:Long,postUrl:String,imgUrl: String)

case class Comment(postId:Long,userId:Long,commentText:String,polarity:Float)

case class Like(postId:Long,userId:Long)

case class Tag(postId:Long,tag:String)

/**
 * @param uri MongoDb connection
 * @param db MongoDb database
 * */
case class MongoConfig(uri:String,db:String)

/**
 *
 * @param httpHosts       http主机列表，逗号分隔
 * @param transportHosts  transport主机列表
 * @param index            需要操作的索引
 * @param clusterName      集群名称，默认elasticsearch
 */
case class ESConfig(httpHosts:String,transportHosts:String,index:String,clusterName:String)


object DataLoader {
  //define constant

  // define file path
  val POST_DATA_PATH="src/main/resources/post.csv"
  val COMMENT_DATA_PATH=""
  val TAG_DATA_PATH=""
  val LIKE_DATA_PATH=""
  //define db constant
  val MONGODB_POST_COLLECTION="Post"
  val MONGODB_COMMENT_COLLECTION="Comment"
  val MONGODB_LIKE_COLLECTION="Like"
  val MONGODB_TAG_COLLECTION="Tag"
  val ES_POST_INDEX="Post"

  def main(args: Array[String]): Unit = {
    val config=Map(
      "spark.cores" -> "local[*]",
      "mongo.uri" -> "mongodb://localhost:27017/recommender",
      "mongo.db" -> "recommender",
      "es.httpHosts" -> "localhost:9200",
      "es.transportHosts" -> "localhost:9300",
      "es.index" -> "recommender",
      "es.cluster.name" -> "elasticsearch"
    )


    val sparkConf=new SparkConf().setAppName("DataLoader").setMaster(config("spark.cores"))

    val spark=SparkSession.builder().config(sparkConf).getOrCreate()

    val postRDD=spark.sparkContext.textFile(POST_DATA_PATH)

    val postDF=postRDD.map(
      item=>{
        val attr=item.split(",")
        Post(attr(0).toLong,attr(1).trim,attr(2).trim)
      }
    ).toDF()

    implicit val mongoConfig=MongoConfig(config("mongo.uri"),config("mongo.db"))

//    storeDataInMongoDB(postDF)
    //todo preprocess data. put comment,like,tag into post
    val postVO=None

//    implicit val esConfig=ESConfig(config("es.httpHosts"),config("es.transportHosts"), config("es.index"), config("es.cluster.name"))
//
//    storeDataInES(postVO)

//    spark.stop()

  }

  def storeDataInMongoDB(postDF: DataFrame)(implicit mongoConfig: MongoConfig): Unit ={
    val mongoClient=MongoClient(mongoConfig.uri)

    val recommendDB:MongoDatabase=mongoClient.getDatabase(mongoConfig.db)

    val postCollection:MongoCollection[Document]=recommendDB.getCollection(MONGODB_POST_COLLECTION)

//     todo Error:(103, 30) type mismatch;
//     found   : org.apache.spark.sql.DataFrame
//        (which expands to)  org.apache.spark.sql.Dataset[org.apache.spark.sql.Row]
//     required: org.mongodb.scala.bson.collection.immutable.Document
//        postCollection.insertOne(postDF).results()


    postDF.write
      .format("mongo")
      .mode("append")
      .option("database",mongoConfig.db)
      .option("collection",MONGODB_POST_COLLECTION)
      .save()



    postCollection.insertOne(postDF).results()

  }

  def storeDataInES(postDF: DataFrame)(implicit eSConfig: ESConfig):Unit={
    val settings:Settings=Settings.builder().put("cluster.name",eSConfig.clusterName).build()

    val esClient=new PreBuiltTransportClient(settings)

    val REGEX_HOST_PORT = "(.+):(\\d+)".r
    eSConfig.transportHosts.split(",").foreach{
      case REGEX_HOST_PORT(host: String, port: String) => {
        esClient.addTransportAddress(new InetSocketTransportAddress( InetAddress.getByName(host), port.toInt ))
      }
    }

    if(esClient.admin().indices().exists(new IndicesExistsRequest(eSConfig.index))
      .actionGet()
      .isExists
    ){
      esClient.admin().indices().delete(new DeleteIndexRequest(eSConfig.index))
    }

    esClient.admin().indices().create(new CreateIndexRequest(eSConfig.index))

    postDF.write
      .option("es.nodes",eSConfig.httpHosts)
      .option("es.http.timeout", "100m")
      .option("es.mapping.id", "postId")
      .mode("overwrite")
      .format("org.elasticsearch.spark.sql")
      .save(eSConfig.index + "/" + ES_POST_INDEX)


  }


}
