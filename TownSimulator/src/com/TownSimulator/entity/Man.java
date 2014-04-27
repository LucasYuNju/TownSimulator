package com.TownSimulator.entity;

import java.util.HashMap;
import java.util.Iterator;

import com.TownSimulator.driver.Driver;
import com.TownSimulator.driver.DriverListener;
import com.TownSimulator.driver.DriverListenerBaseImpl;
import com.TownSimulator.mantask.RandMove;
import com.TownSimulator.mantask.Task;
import com.TownSimulator.ui.Animation;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Man extends Entity{
	private static final 	float 						MOVE_SPEED = 20.0f;
	private 				HashMap<State, Animation> 	mAnimesMap;
	private 				HashMap<State, Animation> 	mAnimesMapFlipped;
	private 				Vector2						mMoveDir;
	private 				Vector2						mDestination;
	private					float						mMoveTime;
	private 				State						mCurState;
	private 				Task						mTask;
	private					DriverListener				mDriverListener;
	private					boolean						mbSpriteXFlipped;
	
	public enum State
	{
		MAN_MOVE, MAN_STADING
	}
	
	public Man() {
		super(ResourceManager.getInstance(ResourceManager.class).createSprite("pixar_man_1"));
		setDrawAABBLocal(0.0f, 0.0f, Settings.UNIT, Settings.UNIT);
		setCollisionAABBLocal(0, 0, 0, 0);
		
		mAnimesMap = new HashMap<Man.State, Animation>();
		mAnimesMapFlipped = new HashMap<Man.State, Animation>();
		initAnimes();
		mMoveDir = new Vector2();
		mDestination = new Vector2();
		mCurState = State.MAN_STADING;
		mTask = new RandMove(this);
		mbSpriteXFlipped = false;
		
		mDriverListener = new DriverListenerBaseImpl()
		{
			@Override
			public void update(float deltaTime) {
				if(mTask != null)
					mTask.update(deltaTime);
			}
		};
		Driver.getInstance(Driver.class).addListener(mDriverListener);
	}
	
	private void initAnimes()
	{
		Animation standAnime = new Animation(0.0f);
		standAnime.addSprite(ResourceManager.getInstance(ResourceManager.class).createSprite("pixar_man_1"));
		mAnimesMap.put(State.MAN_STADING, standAnime);
		
		Animation moveAnime = new Animation(0.2f);
		moveAnime.addSprite(ResourceManager.getInstance(ResourceManager.class).createSprite("pixar_man_1"));
		moveAnime.addSprite(ResourceManager.getInstance(ResourceManager.class).createSprite("pixar_man_2"));
		mAnimesMap.put(State.MAN_MOVE, moveAnime);
		
		Iterator<State> itr = mAnimesMap.keySet().iterator();
		while(itr.hasNext())
		{
			State key = itr.next();
			Animation anime = mAnimesMap.get(key);
			Animation animeFlip = new Animation(anime.getFrameInterval());
			for (Sprite sp : anime.getSprites()) {
				Sprite spFlip = new Sprite(sp);
				spFlip.flip(true, false);
				animeFlip.addSprite(spFlip);
			}
			mAnimesMapFlipped.put(key, animeFlip);
		}
	}
	
	public void setTask(Task task)
	{
		if(mTask == task)
			return;
		
		mTask = task;
		EntityInfoCollector.getInstance(EntityInfoCollector.class).manTaskChanged(this);
	}
	
	public Task getTask()
	{
		return mTask;
	}
	
	public void setMoveDestination(float destX, float destY)
	{
		if(destX == mDestination.x && destY == mDestination.y)
			return;
		
		if(destX > mPosXWorld)
			mbSpriteXFlipped = true;
		else
			mbSpriteXFlipped = false;
		
		mDestination.x = destX;
		mDestination.y = destY;
		
		mMoveDir.x = destX - mPosXWorld;
		mMoveDir.y = destY - mPosYWorld;
		float destLen = mMoveDir.len();
		mMoveTime = destLen / MOVE_SPEED;
		mMoveDir.scl(1.0f / destLen);
	}
	
	/**
	 * Man move.
	 * @param deltaTime
	 * @return if man moved return true else return false 
	 */
	public boolean move(float deltaTime) {
		if(mMoveTime <= 0)
			return false;
		else
		{
			translate(deltaTime * mMoveDir.x * MOVE_SPEED, deltaTime * mMoveDir.y * MOVE_SPEED);
			mMoveTime -= deltaTime;
			return true;
		}
	}
	
	public void setState(State state)
	{
		mCurState = state;
	}
	
	public State getState()
	{
		return mCurState;
	}
	
	public void setFlip(boolean value)
	{
		mbSpriteXFlipped = value;
	}
	
	public void updateSprite(float deltaTime)
	{
		Animation anime;
		if(mbSpriteXFlipped)
			anime = mAnimesMapFlipped.get(mCurState);
		else
			anime = mAnimesMap.get(mCurState);
		anime.update(deltaTime);
		setSprite(anime.getCurSprite());
	}

}