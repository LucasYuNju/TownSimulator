package com.TownSimulator.entity.building;

import com.TownSimulator.entity.Entity;
import com.TownSimulator.utility.Settings;
import com.badlogic.gdx.math.MathUtils;

public class FarmLand extends Entity{
	public static final float MAX_CROP_AMOUNT = 2000.0f;
	//private CropType cropType;
	private float curCropAmount;
	
	public FarmLand() {
		super("farmland_soil");
		
		float pad = Settings.UNIT * 0.1f;
		setDrawAABBLocal(pad, pad, Settings.UNIT - pad, Settings.UNIT - pad);
		setCollisionAABBLocal(0.0f, 0.0f, Settings.UNIT, Settings.UNIT);
		setUseDrawMinYAsDepth(false);
		setDepth(Float.MAX_VALUE - 1.0f);
	}
	
	public void setCropType(CropType type)
	{
		//cropType = type;
		setTextureName(CropType.getTextureName(type));
	}
	
	public void addCropAmount(float amount)
	{
		curCropAmount += amount;
		curCropAmount = MathUtils.clamp(curCropAmount, 0.0f, MAX_CROP_AMOUNT);
	}
	
	public float getCurCropAmount()
	{
		return curCropAmount;
	}

}
