package cn.programingmonkey.Utils;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

/**
 * Created by cai on 2017/3/28.
 */
public class MongoUtil {
    private static Morphia morphia;
    private static MongoClient  client;
    private static Datastore datastore;
    static {

        try {
            client = new MongoClient("localhost", 27017);
            morphia = new Morphia();
            morphia.mapPackage("cn.programingmonkey.Table");
            datastore = morphia.createDatastore(client,"fudaTour");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public  static Datastore getInstanceDataStore(){
        return datastore;
    }

    public static void closeDataStore(){
        if (client !=null) {
            client.close();
            client = null;
        }
    }
}
