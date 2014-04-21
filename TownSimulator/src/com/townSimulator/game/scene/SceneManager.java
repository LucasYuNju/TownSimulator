package com.townSimulator.game.scene;

import com.townSimulator.utility.Settings;

public class SceneManager {
	private static final 	int					MAP_WIDTH  = 512;
	private static final 	int					MAP_HEIGHT = 512;
	private 				Map 				mMap;
	private 				CollisionDetector	mCollsionDetector;
	
	public SceneManager()
	{
		mCollsionDetector = new CollisionDetector(0.0f, 0.0f, MAP_WIDTH * Settings.UNIT, MAP_HEIGHT * Settings.UNIT);
	}
	
	public void initMap()
	{
		mMap = new Map(MAP_WIDTH, MAP_HEIGHT, 100, this);
	}
	
	public void addCollidable(Collidable obj)
	{
		mCollsionDetector.addCollidable(obj);
	}
	
	public Map getMap()
	{
		return mMap;
	}
	
	public CollisionDetector getCollisionDetector()
	{
		return mCollsionDetector;
	}
	
}
