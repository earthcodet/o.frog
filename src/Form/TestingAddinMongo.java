import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static com.mongodb.client.model.Filters.*;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.Binary;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class TestingAddinMongo {
	public static void main(String[] args) {
		try {	
			MongoClient mongocon = new MongoClient(new MongoClientURI("mongodb://hanami:hanami02@ds247223.mlab.com:47223/ebook"));
			System.out.println("Connection success");
			MongoDatabase db = mongocon.getDatabase("ebook");
			MongoCollection<Document> DBCollection = db.getCollection("User");
			
			List<Document> user = (List<Document>) DBCollection.find().into(new ArrayList<>());
			
			/**MongoDB Querying myOrder(Set of Document) **/
			for(Document userDoc : user) {
				List<Document> myOrder = (List<Document>)userDoc.get("myOrder");
				for(Document order : myOrder) {
					//when select all
					//if(order.getInteger("order_id") == 1) { when select by id
						System.out.println(order.getInteger("order_id"));
						System.out.println(order.getString("date")+"\n");
						List<Document> orderDetail = (List<Document>)order.get("order_detail");
						for(Document book : orderDetail) {
							System.out.println(book.getInteger("book_ID"));
							System.out.println(book.getString("book_name"));
							System.out.println(book.getInteger("book_price"));
							System.out.println("--------------------------------------");
						}
						System.out.println("______________________________________________");
					//}
				}
			}
			
			/**MongoDB Pushing to a Document to Set(myOrder)**/
			Document order_doc = new Document("order_id",2);
			order_doc.append("date", "01/01/62");
			
			Document order_detail1 = new Document("book_ID",3);
			order_detail1.append("book_name", "The Things");
			order_detail1.append("book_price", 49);
			
			Document order_detail2 = new Document("book_ID",1);
			order_detail2.append("book_name", " —µ«Ï‚≈°¢Õß‡√“");
			order_detail2.append("book_price", 990);
			
			List<Document> order_detail = new ArrayList<>();
			order_detail.add(order_detail1);
			order_detail.add(order_detail2);
			
			order_doc.append("order_detail", order_detail);
			DBCollection.updateOne(eq("id", 1), new Document("$push", new Document("myOrder", order_doc)));
			
			/**MongoDB Pulling a Document in Set(myOrder)**/
			DBCollection.updateOne(eq("id", 1), new Document("$pull", new Document("myOrder", new Document("order_id",2))));
			
		} 
		catch(Exception eX) {
			System.err.println("Connection error");
			eX.printStackTrace();
		}
	}
}