package com.TownSimulator.map;


import java.util.Random;

import com.TownSimulator.collision.CollisionDetector;
import com.TownSimulator.entity.EntityFactory;
import com.TownSimulator.entity.MapEntity;
import com.TownSimulator.entity.MapEntityType;
import com.TownSimulator.render.Renderer;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.Singleton;
import com.TownSimulator.utility.simplex.SimplexNoise;

public class Map extends Singleton{
	public static final int				MAP_WIDTH = 256;
	public static final int				MAP_HEIGHT = 256;
	private float[][] 					mNoiseMap;
	private MapEntity[][] 				mObjsMap;
	private String[][]					mGroundMap;
	private float[] 					mTreeScaleMap = { 1.0f, 0.8f, 0.6f, 0.0f, 0.0f, 0.0f };
	
	private Map()
	{
	}
	
	public void init(int seed)
	{
		mNoiseMap = new float[MAP_WIDTH][MAP_HEIGHT];
		mObjsMap  = new MapEntity[MAP_WIDTH][MAP_HEIGHT];
		mGroundMap = new String[MAP_WIDTH][MAP_HEIGHT];
		SimplexNoise noiseGenerator = new SimplexNoise(128, 0.5, seed);
		Random rand = new Random();
		for (int x = 0; x < MAP_WIDTH; x++) {
			for (int y = 0; y < MAP_HEIGHT; y++) {
				float value = (float) (noiseGenerator.getNoise(x, y) + 1.0) * 0.5f; 
				mNoiseMap[x][y] = value;
				
				//Tree
				if(value > 0.6f)
				{
					float scale = mTreeScaleMap[rand.nextInt(mTreeScaleMap.length)];
					if( scale != 0.0f )
					{
						MapEntity tree = EntityFactory.createMapObj(MapEntityType.TREE);
						tree.setDrawAABBLocal(0.0f, 0.0f, Settings.UNIT * 1.5f * scale, Settings.UNIT * 2.0f * scale);
						float originOffsetX = tree.getDrawAABBLocal().getWidth() * 0.5f;
						float randX = (rand.nextFloat() - 0.5f) * Settings.UNIT * 0.2f;
						float randY = (rand.nextFloat() - 0.5f) * Settings.UNIT * 0.2f;
						tree.setPositionWorld(	Settings.UNIT * x + Settings.UNIT * 0.5f - originOffsetX + randX,
												Settings.UNIT * y + Settings.UNIT * 0.5f + randY);
						float collideSize = tree.getDrawAABBLocal().getWidth() * 0.2f;
						float collideMinX = tree.getDrawAABBLocal().getWidth() * 0.5f - collideSize;
						float collideMinY = -collideSize;
						float collideMaxX = tree.getDrawAABBLocal().getWidth() * 0.5f + collideSize;
						float collideMaxY = collideSize;
						tree.setCollisionAABBLocal(collideMinX, collideMinY, collideMaxX, collideMaxY);
						mObjsMap[x][y] = tree;
						CollisionDetector.getInstance(CollisionDetector.class).attachCollisionDetection(tree);
						Renderer.getInstance(Renderer.class).attachDrawScissor(tree);
					}
				}
				
				//Ground
				String textureName = null;
				if(value < 0.5f)
					textureName = "map_grass";
				else
					textureName = "map_soil";
				mGroundMap[x][y] = textureName;
			}
		}
	}
	
	public String[][] getGroundMap()
	{
		return mGroundMap;
	}
	
	public void setGroundTexture(String textureName, int x, int y)
	{
		mGroundMap[x][y] = textureName;
	}
	
	public void setGroundTexture(String textureName, float minX, float minY, float maxX, float maxY)
	{
		int l = (int)(minX / Settings.UNIT);
		int r = (int)(maxX / Settings.UNIT);
		int b = (int)(minY / Settings.UNIT);
		int u = (int)(maxY / Settings.UNIT);
		
		for (int x = l; x <= r; x ++) {
			for (int y = b; y <= u; y ++) {
				mGroundMap[x][y] = textureName;
			}
		}
	}
}
