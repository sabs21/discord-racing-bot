package com.group3.racingbot;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * Handles the requests and connections to the database.
 * @author Maciej Bregisz
 *
 */
public class DBHandler {
	private static ConnectionString connectionString;
	private static MongoClientSettings settings;
	private MongoClient mongoClient;
	private MongoDatabase database;
	private MongoCollection<Player> userCollection;
	private static ConfigPropertiesHandler configProperties;
	
	public DBHandler() {
		 CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
		 CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
		            MongoClientSettings.getDefaultCodecRegistry(),
		            CodecRegistries.fromProviders(pojoCodecProvider)
		    );
		configProperties = ConfigPropertiesHandler.getInstance();
		connectionString = new ConnectionString("mongodb://127.0.0.1:27017");
		//connectionString = new ConnectionString("mongodb+srv://"+configProperties.getProperty("mongoDBUsername") +":"+ configProperties.getProperty("mongoDBPass") +"@racingbot.rjpmq.mongodb.net/"+configProperties.getProperty("mongoDBDatabase")+"?retryWrites=true&w=majority");
		settings = MongoClientSettings.builder().applyConnectionString(connectionString).retryWrites(true).build();
				mongoClient = MongoClients.create(settings);
				database = mongoClient.getDatabase(configProperties.getProperty("mongoDBDatabase")).withCodecRegistry(codecRegistry);
				userCollection = database.getCollection("Users",Player.class).withCodecRegistry(codecRegistry);
		
				System.out.println(userCollection.countDocuments());
	}
	
	/**
	 * Gets a User record from the database by specified id. ID is the Discord User id.  
	 * @param id the Discord user ID
	 * @return whether or not the User with the given ID exists.
	 */
	public boolean userExists(String id) {
		if(userCollection.find(eq("_id",id)).first() != null) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * Inserts the Player object into the database collection.
	 * @param p Player Object being stored in database collection.
	 */
	public void insertUser(Player p) {
		userCollection.insertOne(p);
	}
	
	public void updateUser(Player p) {
		userCollection.findOneAndReplace(eq("_id",p.getId()), p);
	}
	
	public Player getPlayer(String id) {
		Player player = (Player) userCollection.find(eq("_id",id)).first();
		return player;
		
	}

	public static ConnectionString getConnectionString() {
		return connectionString;
	}

	public static MongoClientSettings getSettings() {
		return settings;
	}

	public MongoClient getMongoClient() {
		return mongoClient;
	}

	public MongoDatabase getUserDatabase() {
		return database;
	}

	public static ConfigPropertiesHandler getConfigProperties() {
		return configProperties;
	}

}
