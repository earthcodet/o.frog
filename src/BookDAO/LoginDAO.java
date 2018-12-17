package BookDAO;

import BookPOJO.Login;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import MongoConnect.Mongo;
import java.util.Iterator;
import org.bson.Document;
public class LoginDAO {
    public static Object[] AuthorizeLogin(Login lgInput) {
        MongoClient mongo = Mongo.Connector();
        MongoCollection<Document> dbColl = mongo.getDatabase("ebook").getCollection("User");
        FindIterable<Document> iterateDoc = dbColl.find();
	Iterator<Document> iterator = iterateDoc.iterator();
	while(iterator.hasNext()) {
          Document dc = iterator.next();
          String user = dc.getString("username");
          String pass = dc.getString("password");
          if(lgInput.CheckEqualObjects(user, pass)) {
              return new Object[]{true,user};
          }
	}
        return new Object[]{false};
    }
}
