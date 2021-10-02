package csc.arizona.moneymanager.database;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mongodb.client.*;
import com.mongodb.MongoClientSettings;
import com.mongodb.ConnectionString;
import com.mongodb.ServerAddress;
import com.mongodb.MongoCredential;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {

    protected MongoClient mongoClient;
    private String URI;
    private PasswordUtilities pu;



    public DatabaseHandler(){
        URI = "mongodb+srv://root:root@moneymanagerdata.v0ezf.mongodb.net/myFirstDatabase?retryWrites=true&w=majority";
        pu = new PasswordUtilities();
    }

    public boolean connectToDatabase(){
        try {
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

//    public boolean addData(String username, String key, Object value){
//        Gson gson = new Gson();
//        String json = gson.toJson(value);
//        MongoCollection<Document> users = mongoClient.getDatabase("users").getCollection("user_data");
//        List<Document> usersList = users.find().into(new ArrayList<>());
//        for(Document doc: usersList){
//            if(doc.get("username").equals(username)){
//                users.updateOne(doc, doc.append(key, json));
//                return true;
//            }
//        }
//        return false;
//    }
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


}
