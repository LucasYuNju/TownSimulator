package com.TownSimulator.utility.ls;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import com.TownSimulator.entity.EntityInfoCollector;
import com.TownSimulator.entity.ResourceInfoCollector;
import com.TownSimulator.map.Map;
import com.TownSimulator.utility.Singleton;

/**
 * 需要序列化的类：
 * EntityInfoCollector, ResourceInfoCollector, Map.trees, 
 * 反序列化之后需要初始化的类：
 * Renderer, CollisionDector
 */
public class LoadSave {
	private String dataPath = "/mnt/sdcard/ts.dat";
	
	public void save() {
		try {
			File file = new File(dataPath);
			file.createNewFile();
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(Singleton.getInstance(EntityInfoCollector.class));
//			oos.writeObject(Singleton.getInstance(ResourceInfoCollector.class));
//			oos.writeObject(Singleton.getInstance(Map.class));
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (StackOverflowError e) {
			e.printStackTrace();
		}
		
	}
	
	public void load() {
		
	}
}
