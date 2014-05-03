package com.TownSimulator.ai.btnimpls.general;

import java.util.Random;

import com.TownSimulator.ai.behaviortree.ActionNode;
import com.TownSimulator.ai.behaviortree.ExecuteResult;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.ManAnimeType;
import com.TownSimulator.entity.ManInfo;
import com.TownSimulator.utility.GameMath;
import com.TownSimulator.utility.Settings;

public class RandomMoveBTN implements ActionNode{
	private static final 	float	STADING_MAX_TIME = 5.0f;
	private static final 	float	STADING_MIN_TIME = 1.0f;
	private static			Random 	mRand = new Random(System.nanoTime());
	private 				float 	mStadingTime;
	private					Man		mMan;
	
	public RandomMoveBTN(Man man)
	{
		mMan = man;
	}
	
	@Override
	public ExecuteResult execute(float deltaTime) {
		if( mMan.move(deltaTime) )
			return ExecuteResult.RUNNING;
		else
		{
			
			if( mStadingTime <=0 )
			{
				mStadingTime = GameMath.lerp(STADING_MIN_TIME, STADING_MAX_TIME, mRand.nextFloat());
				
				ManInfo info = mMan.getInfo();
				info.animeType = ManAnimeType.MOVE;
				float randX = (mRand.nextFloat() - 0.5f) * Settings.UNIT * 4.0f;
				float randY = (mRand.nextFloat() - 0.5f) * Settings.UNIT * 4.0f;
				mMan.setMoveDestination(mMan.getPositionXWorld() + randX, mMan.getPositionYWorld() + randY);
				
				//return ExcuteResult.TRUE;
			}
			else
			{
				mStadingTime -= deltaTime;
				ManInfo info = mMan.getInfo();
				info.animeType = ManAnimeType.STANDING;
			}
		}
		
		return ExecuteResult.RUNNING;
	}

}
