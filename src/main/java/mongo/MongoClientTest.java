package mongo;

import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import static com.mongodb.client.model.Filters.*;

public class MongoClientTest {

    public static void main(String[] args) {
        /*
  For a replica set, include the replica set name and a seedlist of the members in the URI string; e.g.
  String uri = "mongodb://mongodb0.example.com:27017,mongodb1.example.com:27017/admin?replicaSet=myRepl";
  For a sharded cluster, connect to the mongos instances; e.g.
  String uri = "mongodb://mongos0.example.com:27017,mongos1.example.com:27017:27017/admin";
 */

        final MongoClient client = MongoClients.create();

/*
    Create collections.
 */

        client.getDatabase("mydb1").getCollection("foo").withWriteConcern(WriteConcern.MAJORITY).insertOne(new Document("abc", 0));
        client.getDatabase("mydb2").getCollection("bar").withWriteConcern(WriteConcern.MAJORITY).insertOne(new Document("xyz", 0));

        MongoCollection<Document> collection = client.getDatabase("").getCollection("");
        collection.find(and(eq("",""), eq("","")));
        /* Step 1: Start a client session. */

        final ClientSession clientSession = client.startSession();
        ClientSession clientSession1 = client.startSession(ClientSessionOptions.builder().causallyConsistent(true).build());

        /* Step 2: Optional. Define options to use for the transaction. */

        TransactionOptions txnOptions = TransactionOptions.builder().readPreference(ReadPreference.primary()).readConcern(ReadConcern.LOCAL).writeConcern(WriteConcern.MAJORITY).build();

        /* Step 3: Define the sequence of operations to perform inside the transactions. */

        TransactionBody txnBody = new TransactionBody<String>() {
            public String execute() {
                MongoCollection<Document> coll1 = client.getDatabase("mydb1").getCollection("foo");
                MongoCollection<Document> coll2 = client.getDatabase("mydb2").getCollection("bar");

        /*
           Important:: You must pass the session to the operations.
         */
                coll1.insertOne(clientSession, new Document("abc", 1));
                coll2.insertOne(clientSession, new Document("xyz", 999));
                return "Inserted into collections in different databases";
            }
        };
        try {
    /*
       Step 4: Use .withTransaction() to start a transaction,
       execute the callback, and commit (or abort on error).
    */

            clientSession.withTransaction(txnBody, txnOptions);
        } catch (RuntimeException e) {
            // some error handling
        } finally {
            clientSession.close();
        }
    }
}
