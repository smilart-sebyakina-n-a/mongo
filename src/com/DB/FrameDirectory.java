package com.DB;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import com.google.common.primitives.Longs;
import com.mongodb.MongoOptions;

import java.util.Comparator;

public class FrameDirectory{

	public final ArrayList<File> files;
		
//	public FrameDirectory(File dir) {
//		File[] fileArray = dir.listFiles();
	public FrameDirectory(String nameDir) {
		File dir = new File(nameDir);
		File[] fileArray = dir.listFiles();

		Arrays.sort(fileArray, new Comparator<File>() {
			public int compare(File o1, File o2) {
				return Longs.compare(o1.lastModified(), o2.lastModified());
			}
		});

		files = new ArrayList<File>(Arrays.asList(fileArray));
		}
	
	public void writeToMongo(IDBManager mongo) throws IOException {
		FrameFile frameFile;
		for (File file : files){
			frameFile = new FrameFile(file);
			Frame frame = frameFile.toFrame();
			frame.setSource("directory");
			mongo.WriteFrame(frame);
			System.out.println(file.toString() + " " + frame.getTimestamp() + "(" + new Date(frame.getTimestamp()) + ") " + frame.getPixels());
		}
	}
	
	public static void main(String[] args) throws IOException {
		FrameDirectory folder = new FrameDirectory("/home/user/Изображения/Настройка для кластера");

//*		Single-node constructor, including, mongos-server (if tuned sharding from mongos-console)
//		IDBManager mongo = new MongoManager("mongodb://192.168.0.79:3000");
//		
//*		ReplicaSet constructor with default MongoOptions
//		IDBManager mongo = new MongoManager("mongodb://192.168.0.122:27017,192.168.0.79:27017,192.168.42.13:27017");

//*		ReplicaSet constructor with current MongoOptions
//		MongoOptions mo = new MongoOptions();
//		mo.connectTimeout = 500;
//		mo.wtimeout = 1000;
//		mo.w = 2;	
//		mo.autoConnectRetry = true;
//		mo.safe = true;
//		IDBManager mongo = new MongoManager("192.168.0.122:27017,192.168.0.79:27017,192.168.42.13:27017", mo);
		
//*		Mongos-node constructor, passes ip_mongos, two ip_shards and value key sharding (field "pixels")
//*		Including tuned sharding parameters.		
		IDBManager mongo = new MongoManager("192.168.0.79:3000", "192.168.0.122:27018", "192.168.42.13:27018", 500000);
		while  (true){
			folder.writeToMongo(mongo);
		}
		
//		System.out.println(folder.files.toString());
	}

}
