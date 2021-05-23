package project;

import com.mongodb.*;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.logging.Logger;
import java.util.logging.Level;

public class SignUpDatabaseManager {

    String URI = "mongodb://admin:admin@cluster0-shard-00-00.snqfj.mongodb.net:27017,cluster0-shard-00-01.snqfj.mongodb.net:27017,cluster0-shard-00-02.snqfj.mongodb.net:27017/<dbname>?ssl=true&replicaSet=atlas-5dm0f0-shard-0&authSource=admin&retryWrites=true&w=majority";

    public int signUpProcess(String entered_name, String entered_pass, String entered_email) {

        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);

        MongoClientURI clientURI = new MongoClientURI(URI);
        MongoClient client = new MongoClient(clientURI);
        MongoDatabase DB = client.getDatabase("log_in");
        MongoCollection collection = DB.getCollection("user_info");
        Document user, email;
        user = (Document) collection.find(new Document("name", entered_name)).first();
        email = (Document) collection.find(new Document("email", entered_email)).first();
        if (user != null) {
            return 1;
        }
        if (email != null) {
            return 2;
        } else {
            Document document = new Document("name", entered_name).append("password", entered_pass).append("email", entered_email).append("verificationCode", "12u313nabsjdb912!");
            collection.insertOne(document);
            try {
                EmailManager mail_manager = new EmailManager();
                mail_manager.sendMail(entered_name, entered_email, "welcomeMail");
            } catch (Exception ex) {
               
            }
        }
        return 0;
    }
}
