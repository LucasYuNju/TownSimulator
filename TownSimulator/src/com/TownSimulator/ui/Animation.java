package com.TownSimulator.ui;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

public class Animation {
	private Array<Sprite> 	mSprites;
	private float			mFrameInterval;
	private float			mTimeAccum = 0.0f;
	private int				mSpriteIndex = 0;
	
	public Animation(float frameInterval)
	{
		mSprites = new Array<Sprite>();
		mFrameInterval = frameInterval;
	}
	
	public void addSprite(Sprite sp)
	{
		mSprites.add(sp);
	}
	
	public void update(float deltaTime)
	{
		if(mFrameInterval <= 0)
			return;
		
		if(mSprites.size == 0)
			return;
		
		mTimeAccum += deltaTime;
		while(mTimeAccum >= mFrameInterval)
		{
			mTimeAccum -= mFrameInterval;
			mSpriteIndex++;
		}
		mSpriteIndex = mSpriteIndex % mSprites.size;
	}
	
	public Sprite getCurSprite()
	{
		return mSprites.get(mSpriteIndex);
	}
}
