package com.TownSimulator.map;


import java.util.Random;

import com.TownSimulator.collision.CollisionDetector;
import com.TownSimulator.entity.EntityFactory;
import com.TownSimulator.entity.MapResource;
import com.TownSimulator.entity.MapResourceType;
import com.TownSimulator.render.Renderer;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.Singleton;
import com.TownSimulator.utility.simplex.SimplexNoise;

public class Map extends Singleton{
	public static final int				MAP_WIDTH = 512;
	public static final int				MAP_HEIGHT = 512;
	private float[][] 					mNoiseMap;
	private MapResource[][] 			mObjsMap;
	private float[] 					mTreeScaleMap = { 1.0f, 0.8f, 0.6f, 0.0f, 0.0f, 0.0f };
	
	private Map()
	{
	}
	
	public void init(int seed)
	{
		mNoiseMap = new float[MAP_HEIGHT][MAP_WIDTH];
		mObjsMap  = new MapResource[MAP_HEIGHT][MAP_WIDTH];
		SimplexNoise noiseGenerator = new SimplexNoise(128, 0.5, seed);
		Random rand = new Random();
		for (int y = 0; y < MAP_HEIGHT; y++) {
			for (int x = 0; x < MAP_WIDTH; x++) {
				float value = (float) (noiseGenerator.getNoise(x, y) + 1.0) * 0.5f; 
				mNoiseMap[x][y] = value; 
				if(value > 0.6f)
				{
					float scale = mTreeScaleMap[rand.nextInt(mTreeScaleMap.length)];
					if( scale == 0.0f )
						continue;
					
					MapResource obj = EntityFactory.createMapObj(MapResourceType.TREE);
					obj.setDrawAABBLocal(0.0f, 0.0f, Settings.UNIT * 1.5f * scale, Settings.UNIT * 2.0f * scale);
					float originOffsetX = obj.getDrawAABBLocal().getWidth() * 0.5f;
					float randX = (rand.nextFloat() - 0.5f) * Settings.UNIT * 0.2f;
					float randY = (rand.nextFloat() - 0.5f) * Settings.UNIT * 0.2f;
					obj.setPositionWorld(	Settings.UNIT * x 					  + Settings.UNIT * 0.5f - originOffsetX + randX,
											Settings.UNIT * (MAP_HEIGHT - y - 1) + Settings.UNIT * 0.5f + randY);
					//obj.setDepth(obj.getPositionYWorld());
					float collideSize = obj.getDrawAABBLocal().getWidth() * 0.2f;
					float collideMinX = obj.getDrawAABBLocal().getWidth() * 0.5f - collideSize;
					float collideMinY = -collideSize;
					float collideMaxX = obj.getDrawAABBLocal().getWidth() * 0.5f + collideSize;
					float collideMaxY = collideSize;
					obj.setCollisionAABBLocal(collideMinX, collideMinY, collideMaxX, collideMaxY);
					mObjsMap[x][y] = obj;
					CollisionDetector.getInstance(CollisionDetector.class).attachCollisionDetection(obj);
					Renderer.getInstance(Renderer.class).attachDrawScissor(obj);
				}
			}
		}
	}
}
