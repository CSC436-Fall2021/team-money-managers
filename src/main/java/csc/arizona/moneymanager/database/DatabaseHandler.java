package csc.arizona.moneymanager.database;
import com.mongodb.*;
import com.mongodb.client.model.Filters;
import csc.arizona.moneymanager.TransactionUI.Transaction;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import com.mongodb.client.*;
import org.bson.Document;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.List;
import java.util.ArrayList;

public class DatabaseHandler {

    protected MongoClient mongoClient;
    private CodecRegistry pojoCodecRegistry;
    private String URI;
    private PasswordUtilities pu;
    private boolean logged_in = false;



    public DatabaseHandler(){
        URI = "mongodb+srv://root:root@moneymanagerdata.v0ezf.mongodb.net/myFirstDatabase?retryWrites=true&w=majority";
        pu = new PasswordUtilities();
        pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
    }

    public boolean connectToDatabase(){
        try {
            ConnectionString connectionString = new ConnectionString(URI);
            MongoClientSettings clientSettings = MongoClientSettings.builder()
                                                                    .applyConnectionString(connectionString)
                                                                    .codecRegistry(pojoCodecRegistry)
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

    public boolean updateUserData(User user, boolean testing){
        if (logged_in(user.getUsername()) && logged_in || testing) {
            MongoDatabase userDb = mongoClient.getDatabase("users").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<User> users = userDb.getCollection("user_data", User.class);
            List<User> usersList = users.find().into(new ArrayList<>());
            User found = null;
            for(User curr : usersList){
                if (curr.getUsername().equals(user.getUsername())){
                    found = curr;
                }
            }
            if (found == null){
                users.insertOne(user);
            } else {
                users.replaceOne(Filters.eq("username", user.getUsername()), user);
            }
            return true;
        }

        return false;
    }

    public User getUserData(String username, boolean testing) throws Exception {
        if (logged_in(username) && logged_in || testing) {
            MongoDatabase userDb = mongoClient.getDatabase("users").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<User> users = userDb.getCollection("user_data", User.class);
            List<User> usersList = users.find().into(new ArrayList<>());
            User found = null;
            for(User curr : usersList){
                if (curr.getUsername().equals(username)){
                    found = curr;
                }
            }
            return found;
        }
        throw new Exception("Tried to access user data without being logged in.");
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

//    public static void turnLoggerOff(){
//        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
//        Logger rootLogger = loggerContext.getLogger("org.mongodb.driver");
//        rootLogger.setLevel(Level.OFF);
//    }


}
