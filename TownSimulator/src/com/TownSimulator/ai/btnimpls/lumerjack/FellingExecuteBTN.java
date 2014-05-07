package com.TownSimulator.ai.btnimpls.lumerjack;

import com.TownSimulator.ai.behaviortree.ActionNode;
import com.TownSimulator.ai.behaviortree.ExecuteResult;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.ManAnimeType;
import com.TownSimulator.entity.Tree;

public class FellingExecuteBTN implements ActionNode{
	public static float FELLING_INTERVAL = 2.0f;
	public static int FELLING_CNT = 4;// 每砍FELLING_CNT次获取一次木材
	public float fellingAccum = 0.0f;
	public int felllingCntAccum = 0;
	private Man man;
	private FellingInfo fellingInfo;
	
	public FellingExecuteBTN(Man man, FellingInfo fellingInfo)
	{
		this.man = man;
		this.fellingInfo = fellingInfo;
	}
	
	private void doFelling(float deltaTime)
	{
		fellingAccum += deltaTime;
		while(fellingAccum >= FELLING_INTERVAL)
		{
			fellingAccum -= FELLING_INTERVAL;
			felllingCntAccum ++;
			
			if(felllingCntAccum >= FELLING_CNT)
			{
				fellingAccum = 0.0f;
				felllingCntAccum = 0;
				
				fellingInfo.hasWood = true;
				fellingInfo.fellingTree.addHealth(-Tree.MAX_HELTH * 0.4f);
				if(fellingInfo.fellingTree.getScale() == 0.0f)
					fellingInfo.fellingTree = null;
				break;
			}
		}
	}
	
	private void felling(float deltaTime)
	{
		man.setMoveDestination(fellingInfo.fellingTree.getPositionXWorld(), fellingInfo.fellingTree.getPositionYWorld());
		
		if( !man.move(deltaTime) )
		{
			doFelling(deltaTime);
			man.getInfo().animeType = ManAnimeType.STANDING;
		}
		else
			man.getInfo().animeType = ManAnimeType.MOVE;
	}
	
	@Override
	public ExecuteResult execute(float deltaTime) {
		if(fellingInfo.hasWood)
			return ExecuteResult.FALSE;
		
		felling(deltaTime);
		return ExecuteResult.TRUE;
	}

}
