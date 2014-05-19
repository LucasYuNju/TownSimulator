package com.TownSimulator.entity;

import java.util.Random;

import com.TownSimulator.driver.Driver;
import com.TownSimulator.driver.DriverListener;
import com.TownSimulator.driver.DriverListenerBaseImpl;
import com.TownSimulator.utility.GameMath;
import com.TownSimulator.utility.Settings;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Tree extends MapEntity{
	private static final long serialVersionUID = -8752992565837306424L;
	public static final float MAX_SCALE = 1.0f;
	public static final float MAX_WIDTH = Settings.UNIT * 1.5f;
	public static final float MAX_HEIGHT = Settings.UNIT * 2.0f;
	public static final float MAX_HELTH = 100.0f;
	public static final float GROW_TO_MAX_TIME_BASE = World.SecondPerYear * 0.5f;
	private static final float GROW_ANIME_TIME = 1.0f;
	public float growToMaxTime = World.SecondPerYear * 0.5f;
	private float scale;
	private float scaleAccum = 0.0f;
	private float growAnimeScaleBegin = 0.0f;
	private float growAnimeScaleEnd = 0.0f;
	private boolean growAnimeStart = false;
	private float growAnimeAccum = 1.0f;
	private float health;
	private boolean bActive = false;
	private DriverListener driverListener;
	private boolean bCutting = false;
	private boolean bShake = false;
	private int shakeCntRemain = 0;
	private float shakeAngle = 0.0f;
	private static final int SHAKE_CNT = 3;
	private static final float SHAKE_ANGLE = (float) (Math.PI * 0.01f);
	private float shakeSpeed = SHAKE_ANGLE * 30.0f;
	
	private static final Random rand = new Random(System.nanoTime());
	
	public Tree() {
		super("map_tree");
		
		driverListener = new DriverListenerBaseImpl()
		{
			private static final long serialVersionUID = -5196416861466739634L;

			@Override
			public void update(float deltaTime) {
				Tree.this.update(deltaTime);
			}
		};
		growToMaxTime = GROW_TO_MAX_TIME_BASE * ( 1.0f + rand.nextFloat() * 0.5f);
	}
	
	public void setCutting(boolean value)
	{
		bCutting = value;
	}
	
	public boolean isCutting()
	{
		return bCutting;
	}
	
	public void setActive(boolean value)
	{
		if(bActive == false && value == true)
			Driver.getInstance(Driver.class).addListener(driverListener);
		
		if(bActive == true && value == false)
			Driver.getInstance(Driver.class).removeListener(driverListener);
		bActive = value;
	}
	
	public void shake()
	{
		bShake = true;
		shakeCntRemain = SHAKE_CNT;
		shakeAngle = -SHAKE_ANGLE;
	}
	
	public void setScale(float s)
	{
		scale = s;
		resetSize();
	}
	
	public float getScale()
	{
		return scale;
	}
	
	private void beCutDown()
	{
		setScale(0.0f);
		growToMaxTime = GROW_TO_MAX_TIME_BASE * ( 1.0f + rand.nextFloat() * 0.5f);
	}
	
	public void addHealth(float amount)
	{
		health += amount;
		health = MathUtils.clamp(health, 0.0f, MAX_HEIGHT);
		if(health == 0.0f)
			beCutDown();
	}
	
	private void doGrowAnime(float deltaTime)
	{
		growAnimeAccum -= deltaTime;
		growAnimeAccum = Math.max(0.0f, growAnimeAccum);
		setScale(GameMath.lerp(growAnimeScaleBegin, growAnimeScaleEnd, 1.0f - growAnimeAccum / GROW_ANIME_TIME));
		
		if(growAnimeAccum <= 0.0f)
		{
			growAnimeStart = false;
		}
	}
	
	private void updateShake(float deltaTime)
	{
		shakeAngle += shakeSpeed * deltaTime;
		if(Math.abs(shakeAngle) > SHAKE_ANGLE )
		{
			shakeAngle = SHAKE_ANGLE * Math.signum(shakeAngle);
			shakeSpeed = shakeSpeed * -1.0f;
			shakeCntRemain --;
		}
		
		if(shakeCntRemain == 0)
		{
			shakeAngle = 0.0f;
			bShake = false;
		}
	}
	
	private void update(float deltaTime)
	{
		if(bShake)
			updateShake(deltaTime);
		grow(deltaTime);
	}
	
	private void grow(float deltaTime)
	{
		if(scale < MAX_SCALE)
		{
			scaleAccum += 1.0f / growToMaxTime * deltaTime;
			if(scaleAccum >= 0.2f)
			{
				scaleAccum = Math.min(MAX_SCALE, scale + scaleAccum) - scale;
				growAnimeScaleBegin = scale;
				growAnimeScaleEnd = scale + scaleAccum;
				growAnimeStart = true;
				growAnimeAccum = GROW_ANIME_TIME;
				scaleAccum = 0.0f;
			}
		}
		
		if(health < MAX_HELTH)
		{
			addHealth( MAX_HELTH / growToMaxTime * deltaTime );
		}
		
		if(growAnimeStart)
			doGrowAnime(deltaTime);
	}
	
	@Override
	public void drawSelf(SpriteBatch batch) {
		mSprite.setOrigin(mSprite.getWidth() * 0.5f, 0.0f);
		mSprite.setRotation( (float) (shakeAngle / Math.PI * 180.0f) );
		super.drawSelf(batch);
	}
	
	private void resetSize()
	{
		float width = MAX_WIDTH * scale;
		float height = MAX_HEIGHT * scale;
		setDrawAABBLocal(-width * 0.5f, 0.0f, width * 0.5f, height);
		float collideSize = getDrawAABBLocal().getWidth() * 0.2f;
		float collideMinX = -collideSize;
		float collideMinY = -collideSize;
		float collideMaxX = collideSize;
		float collideMaxY = collideSize;
		setCollisionAABBLocal(collideMinX, collideMinY, collideMaxX, collideMaxY);
	}
}
