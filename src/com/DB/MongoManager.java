package com.DB;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

public class MongoManager implements IDBManager{
	private Mongo mongo;
	
	private  static final String db_name = "test_mongo";
	private  static final String db_collection = "simple_test";
	
	private DBCollection collection;
	
	private DB db;

	public MongoManager () throws UnknownHostException{
		this.mongo = new Mongo("127.0.0.1", 27017);
        this.db = mongo.getDB(db_name);
        this.collection = db.getCollection(db_collection);
	}

	@Override
	public void WriteFrame(Frame frame) {
		BasicDBObject document = frame.toDBObject();
		collection.insert(document);
	}
	
	@Override
	public void WriteFrame(Frame frame, Integer count) {
		for (int i=0; i<count; i++) {
			WriteFrame(frame);
		}
		
	}


}
