package com.TownSimulator.entity;

import java.util.Random;

import com.TownSimulator.driver.Driver;
import com.TownSimulator.driver.DriverListener;
import com.TownSimulator.driver.DriverListenerBaseImpl;
import com.TownSimulator.utility.GameMath;
import com.TownSimulator.utility.Settings;
import com.badlogic.gdx.math.MathUtils;


public class Tree extends MapEntity{
	public static final float MAX_SCALE = 1.0f;
	public static final float MAX_WIDTH = Settings.UNIT * 1.5f;
	public static final float MAX_HEIGHT = Settings.UNIT * 2.0f;
	public static final float MAX_HELTH = 100.0f;
	public static final float GROW_TO_MAX_TIME_BASE = World.SecondPerYear * 0.5f;
	public float growToMaxTime = World.SecondPerYear * 0.5f;
	private float scale;
	private float scaleAccum = 0.0f;
	private float growAnimeScaleBegin = 0.0f;
	private float growAnimeScaleEnd = 0.0f;
	private boolean growAnimeStart = false;
	private static final float GROW_ANIME_TIME = 1.0f;
	private float growAnimeAccum = 1.0f;
	private float health;
	private boolean bActive = false;
	private DriverListener driverListener;
	
	private static final Random rand = new Random(System.nanoTime());
	
	public Tree() {
		super("map_tree");
		
		driverListener = new DriverListenerBaseImpl()
		{

			@Override
			public void update(float deltaTime) {
				grow(deltaTime);
			}
			
		};
		
		growToMaxTime = GROW_TO_MAX_TIME_BASE * ( 1.0f + rand.nextFloat() * 0.5f);
	}
	
	public void setActive(boolean value)
	{
		if(bActive == false && value == true)
			Driver.getInstance(Driver.class).addListener(driverListener);
		
		if(bActive == true && value == false)
			Driver.getInstance(Driver.class).removeListener(driverListener);
		bActive = value;
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
