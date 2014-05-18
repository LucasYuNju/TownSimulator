package com.TownSimulator.entity.building;

import java.util.Random;

import com.TownSimulator.entity.Entity;
import com.TownSimulator.utility.AxisAlignedBoundingBox;
import com.TownSimulator.utility.GameMath;
import com.TownSimulator.utility.Settings;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class RanchAnimal extends Entity{
	private static final long serialVersionUID = 7682483193432919985L;
	private static final 	Random 					rand = new Random(System.nanoTime());
	private static final 	float 					MOVE_SPEED = 20.0f;
	private static final 	float 					MAX_STAING_TIME = 10.0f;
	private static final 	float 					MIN_STAING_TIME = 5.0f;
	private 				AxisAlignedBoundingBox 	moveRange;
	private 				Vector2 				moveDest;
	private 				Vector2					moveDir;
	private					float					moveTime;
	private					float					stadingTime;
	private					boolean					isMove = false;
	private 				boolean 				drawFlip = false;
	private					RanchAnimalType			type;
	private					Sprite					spNoFlip;
	private					Sprite					spFlip;
	
	public RanchAnimal() {
		super(null);
		setDrawAABBLocal(0.0f, 0.0f, Settings.UNIT, Settings.UNIT);
		moveDest = new Vector2();
		moveDir = new Vector2();
	}
	
	public void setMoveRange(AxisAlignedBoundingBox range)
	{
		this.moveRange = range;
	}
	
	public void setType(RanchAnimalType type)
	{
		this.type = type;
		if(type != null)
		{
			setTextureName(type.getTextureName());
			spNoFlip = mSprite;
			spFlip = new Sprite(mSprite);
			spFlip.setFlip(true, false);
		}
		else
		{
			setSprite(null);
			spNoFlip = null;
			spFlip = null;
		}
	}

	@Override
	public void drawSelf(SpriteBatch batch) {
		super.drawSelf(batch);
	}

	public RanchAnimalType getType()
	{
		return type;
	}
	
	private void setFlip(boolean flip)
	{
		this.drawFlip = flip;
		if(flip)
			setSprite(spFlip);
		else
			setSprite(spNoFlip);
	}
	
	private void setMoveDest(float x, float y)
	{
		if(x == moveDest.x && y == moveDest.y)
			return;
		
		if(x > mPosXWorld)
			setFlip(true);
		else
			setFlip(false);
		
		moveDest.x = x;
		moveDest.y = y;
		
		moveDir.x = x - mPosXWorld;
		moveDir.y = y - mPosYWorld;
		float destLen = moveDir.len();
		moveTime = destLen / MOVE_SPEED;
		moveDir.scl(1.0f / destLen);
	}
	
	private boolean move(float deltaTime)
	{
		if(moveTime <= 0)
			return false;
		else
		{
			float tranlateTime = Math.min(moveTime, deltaTime);
			translate(tranlateTime * moveDir.x * MOVE_SPEED, tranlateTime * moveDir.y * MOVE_SPEED);
			moveTime -= tranlateTime;
			return true;
		}
	}
	
	private boolean standing(float deltaTime)
	{
		stadingTime -= deltaTime;
		if(stadingTime <= 0)
			return false;
		else
			return true;
	}
	
	public void update(float deltaTime)
	{
		if(type == null)
			return;
		
		if(moveRange == null)
			return;
		
		if(isMove)
		{
			if( !move(deltaTime) )
			{
				stadingTime = GameMath.lerp(MIN_STAING_TIME, MAX_STAING_TIME, rand.nextFloat());
				isMove = false;
			}
		}
		else
		{
			if( !standing(deltaTime) )
			{
				float randX = GameMath.lerp(moveRange.minX, moveRange.maxX, rand.nextFloat());
				float randY = GameMath.lerp(moveRange.minY, moveRange.maxY, rand.nextFloat());
				setMoveDest(randX, randY);
				isMove = true;
			}
		}
	}
}
