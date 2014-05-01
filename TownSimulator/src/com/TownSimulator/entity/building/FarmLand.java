package com.TownSimulator.entity.building;

import com.TownSimulator.entity.Entity;
import com.TownSimulator.entity.World;
import com.TownSimulator.utility.GameMath;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class FarmLand extends Entity{
	public static final float MAX_CROP_AMOUNT = 2000.0f;
	//private CropType cropType;
	private float curCropAmount;
	private boolean bCropDieStart = false;
	private float cropDieSpeed = 0.0f;
	private Sprite soil;
	//private float cropStartDieAmount = 0.0f;
	
	public FarmLand() {
		super((Sprite)null);
		
		soil = ResourceManager.getInstance(ResourceManager.class).createSprite("farmland_soil");
		
		float pad = Settings.UNIT * 0.1f;
		setDrawAABBLocal(pad, pad, Settings.UNIT - pad, Settings.UNIT - pad);
		setCollisionAABBLocal(0.0f, 0.0f, Settings.UNIT, Settings.UNIT);
		setUseDrawMinYAsDepth(false);
		setDepth(Float.MAX_VALUE - 1.0f);
	}
	
	public void setCropType(CropType type)
	{
		//cropType = type;
		setTextureName(type.getTextureName());
		updateView();
	}
	
	
	
	@Override
	public void setPositionWorld(float x, float y) {
		super.setPositionWorld(x, y);
		soil.setPosition(x, y);
	}

	@Override
	public void drawSelf(SpriteBatch batch) {
		soil.draw(batch);
		super.drawSelf(batch);
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
			addCropAmount(-deltaTime * cropDieSpeed);
			if( curCropAmount <= 0 )
			{
				bCropDieStart = false;
				cropDieSpeed = 0.0f;
			}
			
		}
		else
		{
			if( curCropAmount <=0 )
				return;
			
			bCropDieStart = true;
			float timeSpeed = 365.0f / World.SecondPerYear;
			cropDieSpeed = curCropAmount / (30.0f / timeSpeed);
		}
	}
	
	public void updateView()
	{
		float size = GameMath.lerp(0.0f, 1.0f, curCropAmount / MAX_CROP_AMOUNT);
		mSprite.setSize(size * mDrawAABBLocal.getWidth(), size * mDrawAABBLocal.getHeight());
		mSprite.setPosition(mDrawAABBWorld.getCenterX() - mSprite.getWidth() * 0.5f, mDrawAABBWorld.minY);
	}

}
