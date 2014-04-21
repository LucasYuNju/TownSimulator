package com.townSimulator.game;


import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.townSimulator.utility.ResourceManager;
import com.townSimulator.utility.Settings;

public class Map implements GameDrawableContainer{
	private int 						mWidthInUnits;
	private int 						mHeightInUnits;
	private float[][] 					mNoiseMap;
	private MapObject[][] 				mObjsMap;
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
		mObjsMap  = new MapObject[mHeightInUnits][mWidthInUnits];
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
					
					MapObject obj = ObjectsManager.createMapObj(MapObjectType.TREE);
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
					mSceneMgr.addCollidable(obj);
				}
			}
		}
	}
	

	@Override
	public void draw(RenderBatch batch, Rectangle scissorRect) {
		int l = (int)( scissorRect.x / Settings.UNIT );
		int r = (int)( (scissorRect.x + scissorRect.width) / Settings.UNIT );
		int u = mHeightInUnits - (int)( (scissorRect.y + scissorRect.height) / Settings.UNIT ) - 1;
		int b = mHeightInUnits - (int)( scissorRect.y / Settings.UNIT ) - 1;
		
		for (int y = u - 1; y <= b + 1; y++) {
			for (int x = l - 1; x <= r + 1; x++) {
				if( x < 0 || x >= mWidthInUnits 
					|| y < 0 || y >= mHeightInUnits)
					continue;
				
				if(mObjsMap[x][y] != null)
				{
					batch.addDrawable(mObjsMap[x][y]);
				}
			}
		}
	}
}
