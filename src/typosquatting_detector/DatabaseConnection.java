package typosquatting_detector;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
public class DatabaseConnection {
	
	private static MongoDatabase db = new MongoClient().getDatabase("test");
	
	public MongoDatabase getDb() {
		return this.db;
	}
	
	/*
	MongoClient mongoClient = new MongoClient();
	MongoDatabase db = mongoClient.getDatabase("test");
	MongoCollection<Document> collection = db.getCollection("test");
	Document obj = new Document("b", 2);
	//collection.insertOne(obj);
	MongoCursor<Document> cursor = collection.find().iterator();
	try {
	    while (cursor.hasNext()) {
	        System.out.println(cursor.next().toJson());
	    }
	} finally {
	    cursor.close();
	}
*/
	
	
	/*public Connection initializeDatabase() throws ClassNotFoundException, SQLException {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/";
		
		String name = "typos";
		String username = "root";
		String password = "331final";
		
		Class.forName(driver);
		return DriverManager.getConnection(url + name, username, password);
	}*/
	
	

}
