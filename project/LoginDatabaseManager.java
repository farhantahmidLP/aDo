package project;

import com.mongodb.*;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.logging.Logger;
import java.util.logging.Level;

public class LoginDatabaseManager {

    String URI = "mongodb://admin:admin@cluster0-shard-00-00.snqfj.mongodb.net:27017,cluster0-shard-00-01.snqfj.mongodb.net:27017,cluster0-shard-00-02.snqfj.mongodb.net:27017/<dbname>?ssl=true&replicaSet=atlas-5dm0f0-shard-0&authSource=admin&retryWrites=false&w=majority";

    public int verifyLogin(String name, String password) {

        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);

        MongoClientURI clientURI = new MongoClientURI(URI);
        MongoClient client = new MongoClient(clientURI);
        MongoDatabase DB = client.getDatabase("log_in");
        MongoCollection collection = DB.getCollection("user_info");
        Document user;
        user = (Document) collection.find(new Document("name", name)).first();
        if (user != null) {
            String verified_pass = user.get("password").toString();
            if (verified_pass.equals(password)) {
                return 2;
            } else {
                return 1;
            }
        } else if (user == null) {
            return 0;
        } else {
            return -1;
        }
    }

}
