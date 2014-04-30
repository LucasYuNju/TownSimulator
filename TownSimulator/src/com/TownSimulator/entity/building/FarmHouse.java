package com.TownSimulator.entity.building;

import com.TownSimulator.entity.JobType;
import com.TownSimulator.utility.AxisAlignedBoundingBox;
import com.TownSimulator.utility.Settings;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class FarmHouse extends WorkingBuilding{
	private static final int MAX_WORKER_CNT = 4;
	private CropType curCropType;
	private float sowProcess;
	private float reapProcess;
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
//		float posX = mPosXWorld;
//		float posY = mPosYWorld + Settings.UNIT;
		int i = 0;
		for (float y = mPosYWorld - Settings.UNIT; y >= mPosYWorld - 3 * Settings.UNIT; y -= Settings.UNIT) {
			for (float x = mPosXWorld; x <= mPosXWorld + 2 * Settings.UNIT; x += Settings.UNIT) {
				farmLands.get(i).setPositionWorld(x, y);
				i++;
			}
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

	public CropType getCurCropTyep()
	{
		return curCropType;
	}
	
	public void setCurCropType(CropType type)
	{
		for (FarmLand land : farmLands) {
			land.setCropType(type);
		}
	}
	
	public void addSowProcess(float amount)
	{
		sowProcess += amount;
		sowProcess = MathUtils.clamp(sowProcess, 0.0f, 100.0f);
	}
	
	public float getSowProcess()
	{
		return sowProcess;
	}
	
	public void addReapProcess(float amount)
	{
		reapProcess += amount;
		reapProcess = MathUtils.clamp(reapProcess, 0.0f, 100.0f);
	}
	
	public float getReapProcess()
	{
		return reapProcess;
	}
	
	public void setSowed(boolean value)
	{
		bSowed = value;
	}
	
	public boolean isSowed()
	{
		return bSowed;
	}
}
