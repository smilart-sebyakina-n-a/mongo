package com.DB;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.common.primitives.Longs;
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
			System.out.println(file.toString() + " " + frame.getSource());
		}
	}
	
	public static void main(String[] args) throws IOException {
//		FrameDirectory folder = new FrameDirectory("/tmp/0");
		FrameDirectory folder = new FrameDirectory("/home/user/Изображения/Настройка для кластера");

		IDBManager mongo = new MongoManager();
		while  (true){
			folder.writeToMongo(mongo);
		}
//		System.out.println(folder.files.toString());
	}

}
