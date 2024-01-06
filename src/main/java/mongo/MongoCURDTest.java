package mongo;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.Iterator;
import java.util.function.Consumer;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

public class MongoCURDTest {
    public static void main(String[] args) {
        MongoClient mongoClient = MongoClients.create();


        MongoCollection<Document> collection = mongoClient.getDatabase("test").getCollection("user");
        //查询全部
        System.out.println("查询全部");
        FindIterable<Document> documents = collection.find(Document.parse("{}"));
        documents.forEach((Consumer<? super Document>) x->{
            System.out.println(x.get("name"));
        });
        printJson(documents);

        System.out.println("in查询");
        FindIterable<Document> inSelectDocs = collection.find(in("status", "A", "D"));
        printJson(inSelectDocs);

        System.out.println("and查询");
        printJson(collection.find(and(eq("status", "A"), lt("qty", 30))));

        System.out.println("or查询");
        printJson(collection.find(or(eq("status", "A"), eq("qty", 30))));

        System.out.println("and/or查询");
        printJson(collection.find(
                and(eq("status", "A"),
                        or(lt("qty", 30), regex("item", "^p")))));


        //update
        collection.updateOne(eq("item", "paper"),
                combine(set("size.uom", "cm"), set("status", "P"), currentDate("lastModified")));


    }

    public static void printJson(Iterable<Document> iterable){
        Iterator<Document> iterator = iterable.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next().toJson());
        }
    }
}
