package com.TownSimulator.entity.building;

import com.TownSimulator.entity.Entity;
import com.TownSimulator.entity.World;
import com.TownSimulator.utility.Settings;
import com.badlogic.gdx.math.MathUtils;

public class FarmLand extends Entity{
	public static final float MAX_CROP_AMOUNT = 2000.0f;
	//private CropType cropType;
	private float curCropAmount;
	private boolean bCropDieStart = false;
	private float cropDieSpeed = 0.0f;
	//private float cropStartDieAmount = 0.0f;
	
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
		setTextureName(CropType.Wheat.getTextureName());
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
	
	public void cropDie(float deltaTime)
	{
		if(bCropDieStart)
		{
			if( curCropAmount <= 0 )
				return;
			
			addCropAmount(-deltaTime * cropDieSpeed);
		}
		else
		{
			bCropDieStart = true;
			float timeSpeed = 365.0f / World.SecondPerYear;
			cropDieSpeed = curCropAmount / (30.0f / timeSpeed);
		}
	}

}
