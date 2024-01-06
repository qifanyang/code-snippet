package mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Indexes;
import org.bson.Document;

public class MongoIndexTest {
    public static void main(String[] args) {
        MongoClient mongoClient = MongoClients.create();


        MongoCollection<Document> collection = mongoClient.getDatabase("test").getCollection("user");
        collection.createIndex(Indexes.descending("name"));
        System.out.println(collection.listIndexes());
    }
}
