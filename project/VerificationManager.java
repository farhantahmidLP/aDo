package project;

import com.mongodb.*;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import javafx.scene.control.Label;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

public class VerificationManager {

    final String URI = "mongodb://admin:admin@cluster0-shard-00-00.snqfj.mongodb.net:27017,cluster0-shard-00-01.snqfj.mongodb.net:27017,cluster0-shard-00-02.snqfj.mongodb.net:27017/<dbname>?ssl=true&replicaSet=atlas-5dm0f0-shard-0&authSource=admin&retryWrites=true&w=majority";
    public static String userName, userEmail, censoredEmail, verificationCode;

    MongoClientURI clientURI = new MongoClientURI(URI);
    MongoClient client = new MongoClient(clientURI);
    MongoDatabase DB = client.getDatabase("log_in");
    MongoCollection collection = DB.getCollection("user_info");

    public boolean onUsernameEntered(String enteredName) {

        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);
        Document user;
        user = (Document) collection.find(new Document("name", enteredName)).first();
        if (user != null) {
            censoredEmail = user.get("email").toString();
            censoredEmail = emailEncrypt(censoredEmail);
            userName = user.get("name").toString();
            userEmail = user.get("email").toString();
        }
        return (user != null);

    }

    public boolean onEmalEntered(String enteredEmail) {
        return (userEmail.equals(enteredEmail));
    }

    public boolean on_Code_entered(String enteredCode) {
        Document code;
        code = (Document) collection.find(new Document("verificationCode", enteredCode)).first();
        return (code != null);
    }

    public String emailEncrypt(String email) {
        String checked = "";
        int check = 0;
        for (int i = 0; i <= (email.length() - 1); i++) {
            if (email.charAt(i) == '@') {
                check = -50;
            } else if (i >= 3 && check != -50) {
                checked += '*';
                continue;
            } else if (email.charAt(i) == '@') {
                check = -50;
            }
            checked += email.charAt(i);
        }
        return checked;
    }

    public void changeServerCode(String userName, String userEmail, String MailStatus) {

        System.out.println(userName + " : this is user name");

        String code = generate_code('@');
        Document user;
        user = (Document) collection.find(new Document("name", userName)).first();
        Bson updateValue = new Document("verificationCode", code);
        System.out.print(userEmail + " <- this is emaile \n");

        Bson updateOperation = new Document("$set", updateValue);
        collection.updateOne(user, updateOperation);
        if (!MailStatus.equals("noMail")) {
            sendMail(userName, userEmail, code);
        }
        System.out.println(code);
        code = "BestOfLuck";
        generate_code('`');
    }

    public void sendMail(String userName, String userEmail, String code) {;
        EmailManager emailManager = new EmailManager();
        emailManager.generatedCode = code;
        try {
            emailManager.sendMail(userName, userEmail, "verificationMail");
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.print("ending email sending process\n");

    }

    public String getCensoredEmail() {
        String str = censoredEmail;
        return str;
    }

    public String generate_code(char x) {
        String code = "";
        char digit;
        if (x == '@') {
            for (int i = 0; i < 6; i++) {
                digit = (char) ((int) (Math.random() * ('Z' - 'A' + 1) + 'A'));
                code += digit;
            }
        } else if (x == '`') {
            code = "BestOfLuck";
        } else {
            code = "Eh?!";
        }

        return code;

    }

    public int time = 180, cycle = 180;
    String minuteString, secondsString;
    String timeString = "";

    public void reset_timer() {
        this.time = 183;
        this.cycle = 0;
    }

    public boolean change = false;

    public void begin_timer(Label timer_label) {
        change = false;
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {

            if (time > 0) {
                time--;
                minuteString = (time / 60 < 10) ? "0" + (Integer.toString(time / 60)) : (Integer.toString(time / 60));
                secondsString = (time % 60 < 10) ? "0" + (Integer.toString(time % 60)) : (Integer.toString(time % 60));
                timeString = minuteString + ":" + secondsString;
                timer_label.setText(timeString);
            }
            if (time == 3) {
                changeServerCode(userName, userEmail, "noMail");
            }
        }));
        timeline.setCycleCount(cycle);
        timeline.play();
        if (time <= 0) {
            timeline.stop();
        }
    }

    public void changePassword(String password) {
        Document user;
        user = (Document) collection.find(new Document("name", userName)).first();
        Bson updateValue = new Document("password", password);
        Bson updateOperation = new Document("$set", updateValue);
        collection.updateOne(user, updateOperation);
    }

}
