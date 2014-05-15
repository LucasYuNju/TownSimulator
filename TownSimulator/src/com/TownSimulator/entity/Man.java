package com.TownSimulator.entity;

import java.util.HashMap;
import java.util.Iterator;

import com.TownSimulator.ai.behaviortree.BehaviorTreeNode;
import com.TownSimulator.ai.btnimpls.idle.IdleBTN;
import com.TownSimulator.driver.Driver;
import com.TownSimulator.driver.DriverListener;
import com.TownSimulator.driver.DriverListenerBaseImpl;
import com.TownSimulator.render.Renderer;
import com.TownSimulator.utility.Animation;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
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
	private                 float                       tempAcountTime=0.0f;
	
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
		mBehavior = new IdleBTN(this);
		initInfo();
		initAnimes();
		
		mDriverListener = new DriverListenerBaseImpl()
		{
			private float dieElapseTime = 5.0f;
			private float dieAccum = 0.0f;
			
			@Override
			public void update(float deltaTime) {
				if(mInfo.isDead)
				{
					dieAccum += deltaTime;
					if(dieAccum >= dieElapseTime)
						Renderer.getInstance(Renderer.class).dettachDrawScissor(Man.this);;
				}
				else{
					updateManPoints(deltaTime);
					updateAge(deltaTime);
				}
				
				if(mBehavior != null)
					mBehavior.execute(deltaTime);
				updateSprite(deltaTime);
			}
		};
		Driver.getInstance(Driver.class).addListener(mDriverListener);
		
	}
	
	@SuppressWarnings("deprecation")
	private void initInfo()
	{
		mInfo = new ManInfo();
		//mInfo.animeType = ManAnimeType.STANDING;
		//mInfo.animeFlip = false;
	}
	
	
	
	private void initAnimes()
	{
		mAnimesMap = new HashMap<ManAnimeType, Animation>();//正走
		mAnimesMapFlipped = new HashMap<ManAnimeType, Animation>();//逆走
		
		Animation standAnime = new Animation();
		standAnime.addFrame("pixar_man_1", 0.0f);
		mAnimesMap.put(ManAnimeType.STANDING, standAnime);
		
		Animation moveAnime = new Animation();
		moveAnime.addFrame("pixar_man_1", 0.1f);
		moveAnime.addFrame("pixar_man_2", 1.0f);
		mAnimesMap.put(ManAnimeType.MOVE, moveAnime);
		
		Animation dieAnime = new Animation();
		dieAnime.addFrame("man_dead", 0.0f);
		mAnimesMap.put(ManAnimeType.DIE, dieAnime);
		
		Animation workAnime = new Animation();
		workAnime.addFrame("pixar_man_1", 0.1f);
		workAnime.addFrame("pixar_man_2", 1.0f);
		mAnimesMap.put(ManAnimeType.WORK, workAnime);
		
		Iterator<ManAnimeType> itr = mAnimesMap.keySet().iterator();
		while(itr.hasNext())
		{
			ManAnimeType key = itr.next();
			Animation anime = mAnimesMap.get(key);
//			Animation animeFlip = new Animation();
//			for (AnimeFrame frame : anime.getFrames()) {
//				Sprite spFlip = new Sprite(sp);
//				spFlip.flip(true, false);
//				animeFlip.addFrame(spFlip);
//			}
			mAnimesMapFlipped.put(key, anime.flip());
		}
	}
	
	private void updateManPoints(float deltaTime)
	{
		mInfo.hungerPoints -= ManInfo.HUNGER_DECRE_SPEED * deltaTime;
		if(mInfo.hungerPoints <= ManInfo.HUNGER_POINTS_MIN)
			die();
		
		//System.out.println(mInfo.hungerPoints);
	}
	
	public void updateAge(float deltaTime){
		tempAcountTime+=deltaTime;
		if(tempAcountTime>=World.SecondPerYear){
			mInfo.setAge(mInfo.getAge()+1);
			if(mInfo.isOldEnough(mInfo.getAge())){
				mInfo.setIsDead(true);
			}
			tempAcountTime-=World.SecondPerYear;
		}
	}
	
	public void die()
	{
		if(mInfo.home != null)
			mInfo.home.removeResident(mInfo);
		
		if(mInfo.workingBuilding != null)
			mInfo.workingBuilding.removeWorker(this);
		
		if(mInfo.constructionInfo.proj != null)
			mInfo.constructionInfo.proj.removeWorker(this);
		
		EntityInfoCollector.getInstance(EntityInfoCollector.class).removeMan(this);
		
		setBehavior(null);
		mInfo.animeType = ManAnimeType.DIE;
		mInfo.isDead = true;
	}

	public void setBehavior(BehaviorTreeNode behavior)
	{
		if(behavior != mBehavior && mBehavior != null)
			mBehavior.destroy();
		
		mBehavior = behavior;
	}
	
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
	 * @param deltaTime
	 * @return return false when man stop
	 *  <p>and it happens when man reach destination 
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

	public ManInfo getInfo()
	{
		return mInfo;
	}
	
	public Animation getAnimation(ManAnimeType type, boolean flip)
	{
		if(flip)
			return mAnimesMapFlipped.get(type);
		else
			return mAnimesMap.get(type);
	}

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
