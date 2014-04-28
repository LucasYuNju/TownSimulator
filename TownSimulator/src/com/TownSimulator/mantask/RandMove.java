package com.TownSimulator.mantask;

import java.util.Random;

import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.Man.State;
import com.TownSimulator.utility.GameMath;
import com.TownSimulator.utility.Settings;

public class RandMove extends Task{
	private static final 	float	STADING_MAX_TIME = 5.0f;
	private static final 	float	STADING_MIN_TIME = 1.0f;
	private 				Random 	mRand;
	private 				float 	mStadingTime;
	
	public RandMove(Man man) {
		super(man);
		
		mRand = new Random(System.currentTimeMillis());
		randStanding();
	}

	@Override
	public void update(float deltaTime) {
		if(mMan.getState() == State.MAN_MOVE)
		{
			if( !mMan.move(deltaTime) )
				randStanding();
		}
		else if(mMan.getState() == State.MAN_STADING)
		{
			mStadingTime -= deltaTime;
			if(mStadingTime <= 0)
				randDestination();
		}
		
		mMan.updateSprite(deltaTime);
	}
	
	private void randStanding()
	{
		mStadingTime = GameMath.lerp(STADING_MIN_TIME, STADING_MAX_TIME, mRand.nextFloat());
		mMan.setState(State.MAN_STADING);
	}
	
	private void randDestination()
	{
		Random rand = new Random();
		float randX = (rand.nextFloat() - 0.5f) * Settings.UNIT * 4.0f;
		float randY = (rand.nextFloat() - 0.5f) * Settings.UNIT * 4.0f;
		mMan.setMoveDestination(mMan.getPositionXWorld() + randX, mMan.getPositionYWorld() + randY);
		mMan.setState(State.MAN_MOVE);
	}

}
