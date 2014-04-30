package com.TownSimulator.entity;

import java.util.HashMap;
import java.util.Iterator;

import com.TownSimulator.ai.behaviortree.BehaviorTreeNode;
import com.TownSimulator.ai.btnimpls.IdleBTN;
import com.TownSimulator.driver.Driver;
import com.TownSimulator.driver.DriverListener;
import com.TownSimulator.driver.DriverListenerBaseImpl;
import com.TownSimulator.ui.Animation;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Man extends Entity{
	private static final 	float 						MOVE_SPEED = 20.0f;
	private 				HashMap<ManAnimeType, Animation> 	mAnimesMap;
	private 				HashMap<ManAnimeType, Animation> 	mAnimesMapFlipped;
	private 				Vector2						mMoveDir;
	private 				Vector2						mDestination;
	private					float						mMoveTime;
	private					DriverListener				mDriverListener;
	private					ManInfo						mInfo;
	private					BehaviorTreeNode			mBehavior;
	
	public enum State
	{
		MAN_MOVE, MAN_STADING
	}
	
	public Man() {
		super(ResourceManager.getInstance(ResourceManager.class).createSprite("pixar_man_1"));
		setDrawAABBLocal(0.0f, 0.0f, Settings.UNIT, Settings.UNIT);
		setCollisionAABBLocal(0, 0, 0, 0);
		
		mMoveDir = new Vector2();
		mDestination = new Vector2();
		mMoveTime = 0.0f;
//		mCurState = State.MAN_STADING;
		//mTaskController = new TaskController(this);
		mBehavior = new IdleBTN(this);
		initInfo();
		initAnimes();
		
		mDriverListener = new DriverListenerBaseImpl()
		{
			@Override
			public void update(float deltaTime) {
				//mTaskController.update(deltaTime);
				mBehavior.execute(deltaTime);
				updateSprite(deltaTime);
			}
		};
		Driver.getInstance(Driver.class).addListener(mDriverListener);
		
	}
	
	private void initInfo()
	{
		mInfo = new ManInfo();
		//mInfo.animeType = ManAnimeType.STANDING;
		//mInfo.animeFlip = false;
	}
	
	private void initAnimes()
	{
		mAnimesMap = new HashMap<ManAnimeType, Animation>();
		mAnimesMapFlipped = new HashMap<ManAnimeType, Animation>();
		
		Animation standAnime = new Animation(0.0f);
		standAnime.addSprite(ResourceManager.getInstance(ResourceManager.class).createSprite("pixar_man_1"));
		mAnimesMap.put(ManAnimeType.STANDING, standAnime);
		
		Animation moveAnime = new Animation(0.2f);
		moveAnime.addSprite(ResourceManager.getInstance(ResourceManager.class).createSprite("pixar_man_1"));
		moveAnime.addSprite(ResourceManager.getInstance(ResourceManager.class).createSprite("pixar_man_2"));
		mAnimesMap.put(ManAnimeType.MOVE, moveAnime);
		
		Iterator<ManAnimeType> itr = mAnimesMap.keySet().iterator();
		while(itr.hasNext())
		{
			ManAnimeType key = itr.next();
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

	public void setBehavior(BehaviorTreeNode behavior)
	{
		mBehavior = behavior;
	}
	
//	public void addTask(Task task)
//	{
//		mTaskController.addTask(task);
//	}
	
//	public boolean isIdle()
//	{
//		return mTaskController.getTaskList().size() == 1;
//	}
	
	public void setMoveDestination(float destX, float destY)
	{
		if(destX == mDestination.x && destY == mDestination.y)
			return;
		
		if(destX > mPosXWorld)
			mInfo.animeFlip = true;
		else
			mInfo.animeFlip = false;
		
		mDestination.x = destX;
		mDestination.y = destY;
		
		mMoveDir.x = destX - mPosXWorld;
		mMoveDir.y = destY - mPosYWorld;
		float destLen = mMoveDir.len();
		mMoveTime = destLen / MOVE_SPEED;
		mMoveDir.scl(1.0f / destLen);
	}
	
	public void stopMove()
	{
		mMoveTime = 0.0f;
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
			float tranlateTime = Math.min(mMoveTime, deltaTime);
			translate(tranlateTime * mMoveDir.x * MOVE_SPEED, tranlateTime * mMoveDir.y * MOVE_SPEED);
			mMoveTime -= tranlateTime;
			return true;
		}
	}
	
//	public void setState(State state)
//	{
//		mCurState = state;
//	}
	
//	public State getState()
//	{
//		return mCurState;
//	}
	
	public ManInfo getInfo()
	{
		return mInfo;
	}
	
//	public void setFlip(boolean value)
//	{
//		mbSpriteXFlipped = value;
//	}
	
	public void updateSprite(float deltaTime)
	{
		Animation anime;
		if(mInfo.animeFlip)
			anime = mAnimesMapFlipped.get(mInfo.animeType);
		else
			anime = mAnimesMap.get(mInfo.animeType);
		anime.update(deltaTime);
		setSprite(anime.getCurSprite());
	}

}