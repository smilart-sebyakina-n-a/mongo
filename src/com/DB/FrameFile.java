package com.DB;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.google.common.io.Files;

public class FrameFile {
	private File file;
	public FrameFile(File file){
		this.file = file;
	}
	public FrameFile(String  name){
		this.file = new File(name);
	}
	public Frame toFrame() throws IOException{
		byte[] bytes = Files.toByteArray(file); 
		BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes)); 
		Frame frame = new Frame(System.currentTimeMillis(), bytes, image.getWidth(), image.getHeight());
		frame.setSource("file");
		return frame;
	}
	
	public void writeToMongo(IDBManager mongo) throws IOException {
		Frame frame = toFrame();
		mongo.WriteFrame(frame);
	}
	
	public static void main(String[] args) throws IOException {
		FrameFile f = new FrameFile("/tmp/0/1.jpg");
		Frame frame = f.toFrame();
		IDBManager mongo = new MongoManager();
		mongo.WriteFrame(frame,1000);
	}

}
