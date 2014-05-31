package com.TownSimulator.entity;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Iterator;

import com.TownSimulator.ai.behaviortree.BehaviorTreeNode;
import com.TownSimulator.ai.btnimpls.idle.IdleBTN;
import com.TownSimulator.driver.Driver;
import com.TownSimulator.driver.DriverListener;
import com.TownSimulator.driver.DriverListenerBaseImpl;
import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.entity.building.School;
import com.TownSimulator.ui.UIManager;
import com.TownSimulator.utility.Animation;
import com.TownSimulator.utility.GameMath;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Man extends Entity{
	private static final long serialVersionUID = -2009342658748170922L;
	private static final 	float 						MOVE_SPEED = Settings.UNIT;
	private static final 	float						STATE_ICON_WIDTH = Settings.UNIT * 0.4f;
	private static final 	float						STATE_ICON_HEIGHT = Settings.UNIT * 0.4f;
	private static final 	float						STATE_ICON_UP_OFFSET = Settings.UNIT * 0.2f;
	private	transient		HashMap<ManStateType, Sprite> 		mStatesIcons;
	private transient		HashMap<ManAnimeType, Animation> 	mAnimesMap;
	private transient		HashMap<ManAnimeType, Animation> 	mAnimesMapFlipped;
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
		super("pixar_man_1");
		setDrawAABBLocal(0.0f, 0.0f, Settings.UNIT, Settings.UNIT);
		setCollisionAABBLocal(0, 0, 0, 0);
		
		mMoveDir = new Vector2();
		mDestination = new Vector2();
		mMoveTime = 0.0f;
		mBehavior = new IdleBTN(this);
		initInfo();
		initStatesIcons();
		initAnimes();
		listenToDriver();
	}
	
	public void listenToDriver() {
		mDriverListener = new DriverListenerBaseImpl()
		{
			private static final long serialVersionUID = -2810927274650172968L;
			private float dieElapseTime = 5.0f;
			private float dieAccum = 0.0f;
			
			@Override
			public void update(float deltaTime) {
				if(mInfo.isDead)
				{
					dieAccum += deltaTime;
					if(dieAccum >= dieElapseTime)
						destroy();
				}
				else{
					updateHungerPoints(deltaTime);
					updateTemparaturePoints(deltaTime);
					updateAge(deltaTime);
					updateHappinessPoints(deltaTime);
					updateHealtyPoints(deltaTime);
					
					updateStatesIcon();
				}
				
				if(mBehavior != null)
					mBehavior.execute(deltaTime);
				updateSprite(deltaTime);
			}

			
		};
		Driver.getInstance(Driver.class).addListener(mDriverListener);
	}


	@Override
	public void drawSelf(SpriteBatch batch) {
		super.drawSelf(batch);
		
		float width = STATE_ICON_WIDTH * mInfo.manStates.size() + Settings.MARGIN * (mInfo.manStates.size() - 1 );
		float x = mDrawAABBWorld.getCenterX() - width * 0.5f;
		float y = mDrawAABBWorld.maxY + STATE_ICON_UP_OFFSET;
//		System.out.println(mInfo.manStates.size());
		for (ManStateType state : mInfo.manStates) {
			Sprite stateIcon = mStatesIcons.get(state);
			if(stateIcon != null)
			{
				stateIcon.setBounds(x, y, STATE_ICON_WIDTH, STATE_ICON_HEIGHT);
				stateIcon.draw(batch);
				
				x += STATE_ICON_WIDTH + Settings.MARGIN;
			}
		}
		
	}
	
	

	@Override
	public void destroy() {
		super.destroy();
		EntityInfoCollector.getInstance(EntityInfoCollector.class).removeMan(this);
	}

	private void initInfo()
	{
		mInfo = new ManInfo();
	}
	
	private void initStatesIcons()
	{
		mStatesIcons = new HashMap<ManStateType, Sprite>();
		for(ManStateType state : ManStateType.values() )
		{
			mStatesIcons.put(state, ResourceManager.getInstance(ResourceManager.class).createSprite(state.getIconTex()));
		}
	}
	
	private void initAnimes()
	{
		mAnimesMap = new HashMap<ManAnimeType, Animation>();//正走
		mAnimesMapFlipped = new HashMap<ManAnimeType, Animation>();//逆走
		
		Animation standAnime = new Animation();
		standAnime.addFrame("pixar_man_1", 0.0f);
		mAnimesMap.put(ManAnimeType.Standing, standAnime);
		
		Animation moveAnime = new Animation();
		moveAnime.addFrame("pixar_man_1", 1.0f);
		moveAnime.addFrame("pixar_man_2", 1.0f);
		mAnimesMap.put(ManAnimeType.Move, moveAnime);
		
		Animation dieAnime = new Animation();
		dieAnime.addFrame("man_dead", 0.0f);
		mAnimesMap.put(ManAnimeType.Die, dieAnime);
		
		Animation workAnime = new Animation();
		workAnime.addFrame("pixar_man_1", 0.1f);
		workAnime.addFrame("pixar_man_2", 1.0f);
		mAnimesMap.put(ManAnimeType.Working, workAnime);
		
		Iterator<ManAnimeType> itr = mAnimesMap.keySet().iterator();
		while(itr.hasNext())
		{
			ManAnimeType key = itr.next();
			Animation anime = mAnimesMap.get(key);
			mAnimesMapFlipped.put(key, anime.flip());
		}
	}
	
	private void updateStatesIcon() {
		mInfo.manStates.clear();
		
		if(mInfo.hungerPoints <= ManInfo.HUNGER_POINTS_FIND_FOOD)
			mInfo.manStates.add( ManStateType.Hungry );
		
		if(mInfo.temperature <= ManInfo.TEMPERATURE_POINTS_FIND_COAT)
			mInfo.manStates.add( ManStateType.Cold );
		
		if(mInfo.isSick())
			mInfo.manStates.add( ManStateType.Sick );
		
		if(mInfo.isDepressed())
			mInfo.manStates.add( ManStateType.Depressed );
	}
	
	private void updateHungerPoints(float deltaTime)
	{
		mInfo.hungerPoints -= GameMath.lerp(ManInfo.HUNGER_DECRE_SPEED_MIN, ManInfo.HUNGER_DECRE_SPEED_MAX, mInfo.getAge() / ManInfo.AGE_MAX) * deltaTime;
		
		if(mInfo.hungerPoints <= ManInfo.HUNGER_POINTS_MIN)
			die(mInfo.getName() + " died : Hungry");
	}
	
	private void updateTemparaturePoints(float deltaTime) {
		if(World.getInstance(World.class).getCurSeason() == SeasonType.Autumn || World.getInstance(World.class).getCurSeason() == SeasonType.Winter)
		{
			mInfo.temperature -= ManInfo.TEMPERATURE_DECRE_SPEED * deltaTime;
		
			if(mInfo.temperature <= ManInfo.TEMPERATURE_POINTS_MIN)
				die(mInfo.getName() + " died : Cold");
		}
		else
			mInfo.temperature = ManInfo.TEMPERATURE_POINTS_MAX;
	}
	
	private void updateAge(float deltaTime){
		tempAcountTime += deltaTime;
		if(tempAcountTime >= World.SecondPerYear){
			int newAge = mInfo.getAge() + 2;
			mInfo.setAge(newAge);
			checkAgeEvent(newAge);
			if( mInfo.isOldEnough() )
				die(mInfo.getName() + " died : Too Old");
			
			tempAcountTime -= World.SecondPerYear;
		}
	}
	
	private void updateHappinessPoints(float deltaTime) {
		if(mInfo.home == null)
			mInfo.hpHomeless(deltaTime);
		else if(mInfo.home.getType() == BuildingType.LowCostHouse)
			mInfo.hpResideInLowCostHouse(deltaTime);
		else if(mInfo.home.getType() == BuildingType.Apartment)
			mInfo.hpResideInApartment(deltaTime);
		
		if(mInfo.job == JobType.NoJob)
			mInfo.hpWorkless(deltaTime);
	}
	
	private void updateHealtyPoints(float deltaTime)
	{
		mInfo.hpDrawHealthLottery(deltaTime);
	}
	
	private void checkAgeEvent(int newAge) {
		switch (newAge) {
		case ManInfo.AGE_MIN_STUDENT:
			School school=EntityInfoCollector.getInstance(EntityInfoCollector.class).
			          findNearestSchool(getPositionXWorld(),getPositionYWorld());
			if(school==null){
				break;
			}
			school.changeCurrentStudentNum(1);
			mInfo.setSchool(school);
			break;
		case ManInfo.AGE_ADULT:
			if(mInfo.getSchool()==null){
				break;
			}
			mInfo.getSchool().changeCurrentStudentNum(-1);
			mInfo.setSchool(null);
			break;

		default:
			break;
		}
	}

	public void die(String message)
	{
		if(mInfo.home != null)
			mInfo.home.removeResident(mInfo);
		
		if(mInfo.workingBuilding != null)
			mInfo.workingBuilding.removeWorker(this);
		
		if(mInfo.constructionInfo.proj != null)
			mInfo.constructionInfo.proj.removeWorker(this);
		
		EntityInfoCollector.getInstance(EntityInfoCollector.class).removeMan(this);
		
		setBehavior(null);
		mInfo.animeType = ManAnimeType.Die;
		mInfo.isDead = true;
		
		if(message != null)
			UIManager.getInstance(UIManager.class).getGameUI().getMessageBoard().showMessage(message);
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

	private void readObject(ObjectInputStream s) throws ClassNotFoundException, IOException {
		s.defaultReadObject();
		initStatesIcons();
		initAnimes();
		listenToDriver();
//		Singleton.getInstance(Renderer.class).attachDrawScissor(this);
	}
}
