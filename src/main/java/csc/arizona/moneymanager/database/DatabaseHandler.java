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



    public DatabaseHandler(){
        URI = "mongodb+srv://root:root@moneymanagerdata.v0ezf.mongodb.net/myFirstDatabase?retryWrites=true&w=majority";
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
        MongoCollection<Document> users = mongoClient.getDatabase("users").getCollection("user_data");
        List<Document> usersList = users.find().into(new ArrayList<>());
        for (Document doc: usersList){
            if(doc.get("username").equals(username)){
                if(doc.get("password").equals(password)){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean userExists(String username){
        MongoCollection<Document> users = mongoClient.getDatabase("users").getCollection("user_data");
        List<Document> usersList = users.find().into(new ArrayList<>());
        for(Document doc: usersList){
            if(doc.get("username").equals(username)){
                return true;
            }
        }
        return false;
    }

    public boolean deleteUser(String username, String password){
        MongoCollection<Document> users = mongoClient.getDatabase("users").getCollection("user_data");
        List<Document> usersList = users.find().into(new ArrayList<>());
        for (Document doc: usersList){
            if(doc.get("username").equals(username)){
                if(doc.get("password").equals(password)){
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
        MongoCollection<Document> users = mongoClient.getDatabase("users").getCollection("user_data");
        Document doc = new Document("username", username);
        doc.append("password", password);
        try {
            users.insertOne(doc);
        } catch (Exception e){
            return false;
        }
        return true;
    }


}
