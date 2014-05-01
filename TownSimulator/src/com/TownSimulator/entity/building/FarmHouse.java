package com.TownSimulator.entity.building;

import com.TownSimulator.driver.Driver;
import com.TownSimulator.driver.DriverListenerBaseImpl;
import com.TownSimulator.entity.JobType;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.World;
import com.TownSimulator.entity.World.SeasonType;
import com.TownSimulator.utility.AxisAlignedBoundingBox;
import com.TownSimulator.utility.GameMath;
import com.TownSimulator.utility.Settings;
import com.badlogic.gdx.utils.Array;

public class FarmHouse extends WorkingBuilding{
	private static final int MAX_WORKER_CNT = 4;
	private CropType curCropType;
	private CropType nextCropType;
	private boolean bSowStart = false;
	private int sowedLandCnt;
	private boolean bReapStart = false; 
	private int reappedLandCnt;
	private boolean bSowed;
	private Array<FarmLand> farmLands;
	
	public FarmHouse() {
		super("building_farm_house", BuildingType.FARM_HOUSE, JobType.FARMER, MAX_WORKER_CNT);
		
		initFarmLands();
	}
	
	private void initFarmLands()
	{
		farmLands = new Array<FarmLand>();
		for (int i = 0; i < 9; i++) {
			FarmLand farmLand = new FarmLand();
			farmLands.add(farmLand);
		}
		
		updateFarmLandsPos();
	}
	
	private void updateFarmLandsPos()
	{
		int i = 0;
		for (float y = mPosYWorld - Settings.UNIT; y >= mPosYWorld - 3 * Settings.UNIT; y -= Settings.UNIT) {
			for (float x = mPosXWorld; x <= mPosXWorld + 2 * Settings.UNIT; x += Settings.UNIT) {
				farmLands.get(i).setPositionWorld(x, y);
				i++;
			}
		}
		
	}
	
	private void cropGrow(float deltaTime)
	{
		float efficiency = 0.0f;
		for (Man man : workers) {
			efficiency += man.getInfo().workEfficency;
		}
		efficiency /= curWorkerCnt;
		float timeSpeed = 365.0f / World.SecondPerYear;// day/second
		float fullNeedDays = GameMath.lerp(12.0f * 30.0f, 6.0f * 30.0f, curWorkerCnt / maxJobCnt);
		float speed = FarmLand.MAX_CROP_AMOUNT / (fullNeedDays / timeSpeed );
		for (FarmLand land : farmLands) {
			land.addCropAmount(speed * efficiency * deltaTime);
		}
	}
	
	private void cropDie(float deltaTime)
	{
		for (FarmLand land : farmLands) {
			land.cropDie(deltaTime);
		}
	}
	
	@Override
	public void setState(State state) {
		super.setState(state);
		if(state == State.Constructed)
		{
			Driver.getInstance(Driver.class).addListener(new DriverListenerBaseImpl()
			{

				@Override
				public void update(float deltaTime) {
					if(bSowed)
					{
						if(World.getInstance(World.class).getCurSeason() != SeasonType.Winter && bReapStart == false)
							cropGrow(deltaTime);
						else
						{
							if(World.getInstance(World.class).getCurMonth() == 12 
									|| World.getInstance(World.class).getCurMonth() == 1)
								cropDie(deltaTime);
						}
						
						for (FarmLand land : farmLands) {
							land.updateView();
						}
					}
				}
				
			});
		}
	}

	public Array<FarmLand> getFarmLands()
	{
		return farmLands;
	}
	
	public AxisAlignedBoundingBox resetCollisionAABBLocalWithFarmlands()
	{
		AxisAlignedBoundingBox aabb = super.getCollisionAABBLocal();
		aabb.minX = Math.min(0.0f, aabb.minX);
		aabb.minY = aabb.minY - 3 * Settings.UNIT;
		aabb.maxX = Math.max(aabb.minX + 3 * Settings.UNIT, aabb.maxX);
		return aabb;
	}

	@Override
	public void setPositionWorld(float x, float y) {
		super.setPositionWorld(x, y);
		updateFarmLandsPos();
	}

	public CropType getCurCropType()
	{
		return curCropType;
	}
	
	public void setCurCropType(CropType type)
	{
		curCropType = type;
		for (FarmLand land : farmLands) {
			land.setCropType(type);
		}
	}
	
	
	public void setSowStart(boolean value)
	{
		bSowStart = value;
	}
	
	public boolean isSowStart()
	{
		return bSowStart;
	}
	
	public void addSowedLand()
	{
		sowedLandCnt = Math.min(sowedLandCnt + 1, farmLands.size);
	}
	
	public int getSowedLandCnt()
	{
		return sowedLandCnt;
	}
	
	public void clearSowedLandCnt(){
		sowedLandCnt=0;
	}
	
	
	
	public void setReapStart(boolean value)
	{
		bReapStart = value;
	}
	
	public boolean isReapStart()
	{
		return bReapStart;
	}
	
	public void addReappedLand()
	{
		reappedLandCnt = Math.min(reappedLandCnt + 1, farmLands.size);
	}
	
	public void clearReappedLandCnt()
	{
		reappedLandCnt = 0;
	}
	
	public int getReappedLandCnt()
	{
		return reappedLandCnt;
	}
	
	public void setSowed(boolean value)
	{
		bSowed = value;
	}
	
	public boolean isSowed()
	{
		return bSowed;
	}
	
	public void setNextCropType(CropType cropType) {
		nextCropType = cropType;
	}
	
	
}
