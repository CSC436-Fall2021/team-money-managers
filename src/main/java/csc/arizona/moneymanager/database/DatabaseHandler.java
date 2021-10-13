package csc.arizona.moneymanager.database;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import com.mongodb.*;
import csc.arizona.moneymanager.TransactionUI.Transaction;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.mongodb.client.*;
import org.bson.Document;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {

    protected MongoClient mongoClient;
    private String URI;
    private PasswordUtilities pu;
    private boolean logged_in = false;



    public DatabaseHandler(){
        URI = "mongodb+srv://root:root@moneymanagerdata.v0ezf.mongodb.net/myFirstDatabase?retryWrites=true&w=majority";
        pu = new PasswordUtilities();
    }

    public boolean connectToDatabase(){
        try {
            ConnectionString connectionString = new ConnectionString(URI);
            CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
            CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
            MongoClientSettings clientSettings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .codecRegistry(codecRegistry)
                    .build();
            mongoClient = MongoClients.create(URI);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean validateUser(String username, String password){
        MongoCollection<Document> users = mongoClient.getDatabase("users").getCollection("credentials");
        List<Document> usersList = users.find().into(new ArrayList<>());
        for (Document doc: usersList){
            if(doc.get("username").equals(username)){
                if(pu.verifyUserPassword(password, doc.get("secure_password").toString(), doc.get("salt").toString())){
                    doc.put("logged_in", "true");
                    logged_in = true;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean userExists(String username){
        MongoCollection<Document> users = mongoClient.getDatabase("users").getCollection("credentials");
        List<Document> usersList = users.find().into(new ArrayList<>());
        for(Document doc: usersList){
            if(doc.get("username").equals(username)){
                return true;
            }
        }
        return false;
    }

    public boolean deleteUser(String username, String password){
        MongoCollection<Document> users = mongoClient.getDatabase("users").getCollection("credentials");
        List<Document> usersList = users.find().into(new ArrayList<>());
        for (Document doc: usersList){
            if(doc.get("username").equals(username)){
                if(pu.verifyUserPassword(password, doc.get("secure_password").toString(), doc.get("salt").toString())){
                    users.deleteOne(doc);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean addTransactions(String username, List<Transaction> transactions, boolean testing){
        if (logged_in(username) && logged_in || testing) {
            MongoDatabase userDb = mongoClient.getDatabase("users");
            MongoCollection<User> users = userDb.getCollection("user_data", User.class);

//
//            List<Document> usersList = user_data.find().into(new ArrayList<>());
//            MongoCollection<Transaction> curr = null;
//            BasicDBObject transactionObject = null;
//            for(Document doc: usersList){
//                if(doc.get("username").equals(username)){
//                    curr = doc.get("data");
//                    transactionObject = (BasicDBObject) doc.get("transactions");
//
//                }
//            }
//            if (curr == null) {
//                curr = new Document("username", username);
//                transactionObject = new BasicDBObject();
//                transactionObject.append("data", new ArrayList<Transaction>());
//            }
//            List<Transaction> current = (List<Transaction>) transactionObject.get("data");
//            current.addAll(transactions);
//            transactionObject.put("data", current);
//            curr.put("transactions", transactions);
//            user_data.insertOne(curr);
//            return true;
        }


        return false;
    }

    public boolean logged_in(String username){
        MongoCollection<Document> users = mongoClient.getDatabase("users").getCollection("credentials");
        List<Document> usersList = users.find().into(new ArrayList<>());
        for(Document doc: usersList){
            if(doc.get("username").equals(username)){
                if (doc.get("logged_in").equals("true")){
                    return true;
                }
            }
        }
        return false;
    }
//
//    public Object getData(String username, String key){
//        MongoCollection<Document> users = mongoClient.getDatabase("users").getCollection("user_data");
//        List<Document> usersList = users.find().into(new ArrayList<>());
//        for(Document doc: usersList){
//            if(doc.get("username").equals(username)){
//                if (doc.containsKey(key)){
//                    return doc.get(key);
//                }
//            }
//        }
//        return null;
//    }


    public boolean addUser(String username, String password){
        MongoCollection<Document> users = mongoClient.getDatabase("users").getCollection("credentials");
        Document doc = new Document("username", username);
        String salt = pu.getSalt(44);
        doc.append("salt", salt);
        doc.append("secure_password", pu.generateSecurePassword(password, salt));
        doc.append("logged_in", "false");
        try {
            users.insertOne(doc);
        } catch (Exception e){
            return false;
        }
        return true;
    }

    public static void turnLoggerOff(){
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLogger = loggerContext.getLogger("org.mongodb.driver");
        rootLogger.setLevel(Level.OFF);
    }


}
