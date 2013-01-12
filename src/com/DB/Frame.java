package com.DB;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class Frame {
	private final long timestamp;
	
	private final byte[] image;
	
	private final int width;
	
	private final int height;
	
	private final int pixels;
	
	private String id;
	
	private String source;
	
	public void setId(String id) {
		this.id = id;
	}

	public Frame(long timestamp, byte[] image, int width, int height) {
		if(image == null)
			throw new IllegalArgumentException("image is null");
		
		this.timestamp = timestamp;
		this.image = image;
		this.width = width;
		this.height = height;
		this.pixels = width * height; 
		this.source = "";
	}
		
	public Frame(DBObject result) {
		this.id  = result.get("_id").toString();
		this.timestamp = (Long)result.get("timestamp");
		this.width = (Integer)result.get("width");
		this.height = (Integer)result.get("height");
		this.pixels = (Integer)result.get("pixels");
		this.image = (byte[])result.get("image");
		this.source = result.get("source").toString();
	}
	
	public BasicDBObject toDBObject() {
		BasicDBObject document = new BasicDBObject();
        
        document.put("timestamp", timestamp);
        document.put("image", image);
        document.put("width", width);
        document.put("height", height);
        document.put("pixels", pixels);
        document.put("source", source);
        
        return document;
    }

	public long getTimestamp() {
		return timestamp;
	}

	public byte[] getImage() {
		return image;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public int getPixels() {
		return pixels;
	}

	
	public String getId() {
		return id;
	}
	
	public void setSource(String source) {
		this.source = source;
	}
	
	public String getSource() {
		return source;
	}
	
	public String toString() {
		return String.format("%d %dx%d=%d:%s from %s", timestamp, width, height, pixels, id);
	}
}