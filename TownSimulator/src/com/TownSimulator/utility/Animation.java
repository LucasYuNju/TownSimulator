package com.TownSimulator.utility;

import com.TownSimulator.utility.Animation.AnimationListener;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

public class Animation extends Publisher<AnimationListener>{
	private Array<AnimeFrame> 	mSprites;
	//private float			mFrameInterval;
	private float			mTimeAccum = 0.0f;
	private int				mSpriteIndex = 0;
	private float			animeLength = 0.0f;
	
	public class AnimeFrame
	{
		public Sprite sp;
		public float interval;
	}
	
	public interface AnimationListener
	{
		public void frameChanged(int curFrameIndex);
	}
	
	public Animation()
	{
		mSprites = new Array<AnimeFrame>();
		//mFrameInterval = frameInterval;
	}
	
	public void addFrame(String textureName, float frameInterval)
	{
		Sprite sp = ResourceManager.getInstance(ResourceManager.class).createSprite(textureName);
		AnimeFrame frame = new AnimeFrame();
		frame.sp = sp;
		frame.interval = frameInterval;
		mSprites.add(frame);
		
		animeLength += frameInterval;
	}
	
	public Array<AnimeFrame> getFrames()
	{
		return mSprites;
	}
	
	public float getLength()
	{
		return animeLength;
	}
	
	public Animation flip()
	{
		Animation animeFlip = new Animation();
		for (AnimeFrame frame : mSprites) {
			AnimeFrame frameFlip = new AnimeFrame();
			frameFlip.sp = new Sprite(frame.sp);
			frameFlip.sp.setFlip(true, false);
			frameFlip.interval = frame.interval;
			animeFlip.mSprites.add(frameFlip);
		}
		return animeFlip;
	}
	
//	public float getFrameInterval()
//	{
//		return mFrameInterval;
//	}
	
	public void update(float deltaTime)
	{
		AnimeFrame frame = mSprites.get(mSpriteIndex);
		if(frame.interval <= 0)
			return;
		
		if(mSprites.size == 0)
			return;
		
		mTimeAccum += deltaTime;
		while(mTimeAccum >= frame.interval)
		{
			mTimeAccum -= frame.interval;
			mSpriteIndex++;
			mSpriteIndex = mSpriteIndex % mSprites.size;
			frame = mSprites.get(mSpriteIndex);
			
			for (AnimationListener l : mListeners) {
				l.frameChanged(mSpriteIndex);
			}
		}
		//mSpriteIndex = mSpriteIndex % mSprites.size;
	}
	
	public Sprite getCurSprite()
	{
		return mSprites.get(mSpriteIndex).sp;
	}
}
