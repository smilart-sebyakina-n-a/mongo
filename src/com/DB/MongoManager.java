package com.DB;

import java.net.UnknownHostException;
import java.util.ArrayList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoOptions;
import com.mongodb.MongoURI;
import com.mongodb.ServerAddress;

public class MongoManager implements IDBManager{
	private Mongo mongo;
	
	private  static final String db_name = "test_mongo";
	private  static final String db_collection = "simple_test";
	
	private DBCollection collection;
	
	private DB db;

	public MongoManager () throws UnknownHostException{
		this.mongo = new Mongo("192.168.0.122", 27017);
        this.db = mongo.getDB(db_name);
        this.collection = db.getCollection(db_collection);
	}
	
	public MongoManager (String url) throws UnknownHostException{
		this.mongo = new Mongo(new MongoURI(url));
        this.db = mongo.getDB(db_name);
        this.collection = db.getCollection(db_collection);
	}
	
	public MongoManager (String url, MongoOptions mo) throws UnknownHostException{
		
		ArrayList<ServerAddress> addr = new ArrayList<ServerAddress>();
		for (String s: url.split(",")) {
		    addr.add(new ServerAddress(s));
		}
		this.mongo = new Mongo(addr, mo);
		
//		may be different
/* 		WriteConcern w = new WriteConcern( 1, 2000 );
		mongo.setWriteConcern( w );*/

		this.db = mongo.getDB(db_name);
        this.collection = db.getCollection(db_collection);
	}
	

	@Override
	public void WriteFrame(Frame frame) {
		BasicDBObject document = frame.toDBObject();
		try {
			collection.insert(document);
		} catch (RuntimeException e) {
			System.out.println(e);
		}
	}
	
	@Override
	public void WriteFrame(Frame frame, Integer count) {
		for (int i=0; i<count; i++) {
			WriteFrame(frame);
		}
		
	}


}
