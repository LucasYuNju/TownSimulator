package com.TownSimulator.utility.ls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.TownSimulator.entity.EntityInfoCollector;
import com.TownSimulator.entity.ResourceInfoCollector;
import com.TownSimulator.entity.World;
import com.TownSimulator.map.Map;
import com.TownSimulator.utility.Singleton;
import com.badlogic.gdx.Gdx;

/**
 * 需要序列化的类：
 * EntityInfoCollector, ResourceInfoCollector, Map.trees, 
 * 反序列化之后需要初始化的类：
 * Renderer, CollisionDector
 */
public class LoadSave {
	private String dataPath = "/mnt/sdcard/ts.dat";
	private static LoadSave intance = new LoadSave();
	
	private LoadSave() {
	}
	
	public static LoadSave getInstance() {
		return intance;
	}
	
	public void save() {
		try {
			File file = new File(dataPath);
			file.delete();
			file.createNewFile();
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(Singleton.getInstance(Map.class));
			oos.writeObject(Singleton.getInstance(World.class));
			oos.writeObject(Singleton.getInstance(ResourceInfoCollector.class));
			oos.writeObject(Singleton.getInstance(EntityInfoCollector.class));
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (StackOverflowError e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return true if succeed to loading
	 */
	public boolean load() {
		File file = new File(dataPath);
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
			Map map = (Map) ois.readObject();
			Singleton.loadInstance(Map.class, map);
			Gdx.app.log("L/S", "Map read");
			World world = (World) ois.readObject();
			Singleton.loadInstance(World.class, world);
			Gdx.app.log("L/S", "World read");
			ResourceInfoCollector ris = (ResourceInfoCollector) ois.readObject();
			Singleton.loadInstance(ResourceInfoCollector.class, ris);
			Gdx.app.log("L/S", "ResourceInfoCollector read");
			EntityInfoCollector eis = (EntityInfoCollector) ois.readObject();
			Singleton.loadInstance(EntityInfoCollector.class, eis);
			Gdx.app.log("L/S", "EntityInfoCollector read");
			
			Singleton.getInstance(EntityInfoCollector.class).printBuilding();
			Singleton.getInstance(EntityInfoCollector.class).printMan();
			ois.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
			return false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
			return false;
		}
		return true;
	}
}
