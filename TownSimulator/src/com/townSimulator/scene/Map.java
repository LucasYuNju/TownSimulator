package com.townSimulator.scene;


import java.util.Random;

import com.townSimulator.entity.MapEntity;
import com.townSimulator.entity.MapEntityType;
import com.townSimulator.entity.EntityFactory;
import com.townSimulator.utility.Settings;

/**
 * 
 * Annotate me!!！
 *
 */

public class Map{
	private int 						mWidthInUnits;
	private int 						mHeightInUnits;
	private float[][] 					mNoiseMap;
	private MapEntity[][] 				mObjsMap;
	private float[] 					mTreeScaleMap = { 1.0f, 0.8f, 0.6f, 0.0f, 0.0f, 0.0f };
	private SceneManager				mSceneMgr;
	
	public Map(int unitWidth, int unitHeight, int seed, SceneManager sceneMgr)
	{
		mWidthInUnits = unitWidth;
		mHeightInUnits = unitHeight;
		mSceneMgr = sceneMgr;
		
		init(seed);
	}
	
	private void init(int seed)
	{
		mNoiseMap = new float[mHeightInUnits][mWidthInUnits];
		mObjsMap  = new MapEntity[mHeightInUnits][mWidthInUnits];
		SimplexNoise noiseGenerator = new SimplexNoise(128, 0.5, seed);
		Random rand = new Random();
		for (int y = 0; y < mHeightInUnits; y++) {
			for (int x = 0; x < mWidthInUnits; x++) {
				float value = (float) (noiseGenerator.getNoise(x, y) + 1.0) * 0.5f; 
				mNoiseMap[x][y] = value; 
				if(value > 0.6f)
				{
					float scale = mTreeScaleMap[rand.nextInt(mTreeScaleMap.length)];
					if( scale == 0.0f )
						continue;
					
					MapEntity obj = EntityFactory.createMapObj(MapEntityType.TREE);
					obj.setDrawSize(Settings.UNIT * 1.5f * scale, Settings.UNIT * 2.0f * scale);
					float originOffsetX = obj.getDrawWidth() * 0.5f;
					float randX = (rand.nextFloat() - 0.5f) * Settings.UNIT * 0.2f;
					float randY = (rand.nextFloat() - 0.5f) * Settings.UNIT * 0.2f;
					obj.setDrawPosition(	Settings.UNIT * x 					  + Settings.UNIT * 0.5f - originOffsetX + randX,
											Settings.UNIT * (mHeightInUnits - y - 1) + Settings.UNIT * 0.5f + randY);
					float collideSize = obj.getDrawWidth() * 0.2f;
					float collideMinX = obj.getDrawX() + obj.getDrawWidth() * 0.5f - collideSize;
					float collideMaxX = obj.getDrawX() + obj.getDrawWidth() * 0.5f + collideSize;
					float collideMinY = obj.getDrawY() - collideSize;
					float collideMaxY = obj.getDrawY() + collideSize;
					obj.setCollisionBounds(collideMinX, collideMinY, collideMaxX, collideMaxY);
					mObjsMap[x][y] = obj;
					mSceneMgr.addMapObjs(obj);
				}
			}
		}
	}
}
