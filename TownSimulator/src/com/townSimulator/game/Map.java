package com.townSimulator.game;


import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.townSimulator.utility.ResourceManager;
import com.townSimulator.utility.Settings;

public class Map implements GameDrawableContainer{
	private int 						mUnitWidth;
	private int 						mUnitHeight;
	private float[][] 					mNoiseMap;
	private GameDrawableObjects[][] 	mSpriteMap;
	private float[] 					mTreeScaleMap = { 1.0f, 0.8f, 0.6f, 0.0f, 0.0f, 0.0f };
	
	public Map(int unitWidth, int unitHeight, int seed)
	{
		mUnitWidth = unitWidth;
		mUnitHeight = unitHeight;
		
		init(seed);
	}
	
	private void init(int seed)
	{
		mNoiseMap = new float[mUnitHeight][mUnitWidth];
		mSpriteMap = new GameDrawableObjects[mUnitHeight][mUnitWidth];
		SimplexNoise noiseGenerator = new SimplexNoise(128, 0.5, seed);
		Random rand = new Random();
		for (int y = 0; y < mUnitHeight; y++) {
			for (int x = 0; x < mUnitWidth; x++) {
				float value = (float) (noiseGenerator.getNoise(x, y) + 1.0) * 0.5f; 
				mNoiseMap[x][y] = value; 
				if(value > 0.6f)
				{
					float scale = mTreeScaleMap[rand.nextInt(mTreeScaleMap.length)];
					if( scale == 0.0f )
						continue;
					Sprite sp = ResourceManager.createSprite("tree");
					sp.setSize(Settings.UNIT * 1.5f * scale, Settings.UNIT * 2.0f * scale);
					float originOffsetX = sp.getWidth() * 0.5f;
					float randX = (rand.nextFloat() - 0.5f) * Settings.UNIT * 0.2f;
					float randY = (rand.nextFloat() - 0.5f) * Settings.UNIT * 0.2f;
					sp.setPosition(	Settings.UNIT * x 					  + Settings.UNIT * 0.5f - originOffsetX + randX,
									Settings.UNIT * (mUnitHeight - y - 1) + Settings.UNIT * 0.5f + randY);
					mSpriteMap[x][y] = new GameDrawableObjects(sp, sp.getY());
				}
			}
		}
	}
	
//	public void draw(SpriteBatch batch, Rectangle scissorRect)
//	{
//		int l = (int)( scissorRect.x / Settings.UNIT );
//		int r = (int)( (scissorRect.x + scissorRect.width) / Settings.UNIT );
//		int u = mUnitHeight - (int)( (scissorRect.y + scissorRect.height) / Settings.UNIT ) - 1;
//		int b = mUnitHeight - (int)( scissorRect.y / Settings.UNIT ) - 1;
//		
//		for (int y = u - 1; y <= b + 1; y++) {
//			for (int x = l - 1; x <= r + 1; x++) {
//				if( x < 0 || x >= mUnitWidth 
//					|| y < 0 || y >= mUnitHeight)
//					continue;
//				
//				if(mSpriteMap[x][y] != null)
//				{
//					mSpriteMap[x][y].draw(batch);
//				}
//			}
//		}
//	}

	@Override
	public void draw(RenderBatch batch, Rectangle scissorRect) {
		int l = (int)( scissorRect.x / Settings.UNIT );
		int r = (int)( (scissorRect.x + scissorRect.width) / Settings.UNIT );
		int u = mUnitHeight - (int)( (scissorRect.y + scissorRect.height) / Settings.UNIT ) - 1;
		int b = mUnitHeight - (int)( scissorRect.y / Settings.UNIT ) - 1;
		
		for (int y = u - 1; y <= b + 1; y++) {
			for (int x = l - 1; x <= r + 1; x++) {
				if( x < 0 || x >= mUnitWidth 
					|| y < 0 || y >= mUnitHeight)
					continue;
				
				if(mSpriteMap[x][y] != null)
				{
					batch.addDrawable(mSpriteMap[x][y]);
				}
			}
		}
	}
}
