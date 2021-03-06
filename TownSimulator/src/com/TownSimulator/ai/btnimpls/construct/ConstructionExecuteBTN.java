package com.TownSimulator.ai.btnimpls.construct;

import com.TownSimulator.ai.behaviortree.ActionNode;
import com.TownSimulator.ai.behaviortree.ExecuteResult;
import com.TownSimulator.ai.btnimpls.construct.ConstructionProject.State;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.ManAnimeType;
import com.TownSimulator.entity.ManStateType;
import com.TownSimulator.utility.quadtree.QuadTreeType;
import com.badlogic.gdx.math.Vector2;

public class ConstructionExecuteBTN extends ActionNode{
	private static final long serialVersionUID = 1L;
	public static final float		BUILD_CONTRIBUTE_SPPED = 0.4f;
	private Man			mMan;
	
	public ConstructionExecuteBTN(Man man) {
		mMan = man;
	}
	
	private boolean isAroundBuilding()
	{
		float buildingCenterX = mMan.getInfo().constructionInfo.proj.getBuilding().getAABBWorld(QuadTreeType.COLLISION).getCenterX();
		float buildingCenterY = mMan.getInfo().constructionInfo.proj.getBuilding().getAABBWorld(QuadTreeType.COLLISION).getCenterY();
		double dist = Math.pow(mMan.getPositionXWorld() - buildingCenterX, 2)
				    + Math.pow(mMan.getPositionYWorld() - buildingCenterY, 2);
		double radius =  Math.pow(mMan.getInfo().constructionInfo.proj.getBuilding().getAABBWorld(QuadTreeType.COLLISION).getWidth() * 0.5f, 2)
			    		  + Math.pow(mMan.getInfo().constructionInfo.proj.getBuilding().getAABBWorld(QuadTreeType.COLLISION).getHeight() * 0.5f, 2);
		return dist <= radius + 10.0f;
	}
	
	private void moveToBuilding(float deltaTime)
	{
		float buildingCenterX = mMan.getInfo().constructionInfo.proj.getBuilding().getAABBWorld(QuadTreeType.COLLISION).getCenterX();
		float buildingCenterY = mMan.getInfo().constructionInfo.proj.getBuilding().getAABBWorld(QuadTreeType.COLLISION).getCenterY();
		double radius = Math.pow(mMan.getInfo().constructionInfo.proj.getBuilding().getAABBWorld(QuadTreeType.COLLISION).getWidth() * 0.5f, 2)
	    		  		 + Math.pow(mMan.getInfo().constructionInfo.proj.getBuilding().getAABBWorld(QuadTreeType.COLLISION).getHeight() * 0.5f, 2);
		radius = Math.pow(radius, 0.5);
		Vector2 moveVec = new Vector2();
		moveVec.x = buildingCenterX - mMan.getPositionXWorld();
		moveVec.y = buildingCenterY - mMan.getPositionYWorld();
		
		Vector2 destination = new Vector2(moveVec);
		moveVec.scl(1.0f / moveVec.len());
		destination.x = destination.x - moveVec.x * (float)radius + mMan.getPositionXWorld();
		destination.y = destination.y - moveVec.y * (float)radius + mMan.getPositionYWorld();
		
		mMan.setMoveDestination(destination.x, destination.y);
		mMan.getInfo().animeType = ManAnimeType.Move;
		mMan.move(deltaTime);
	}
	
	private void finished()
	{
		mMan.getInfo().constructionInfo.proj = null;
	}
	
	@Override
	public ExecuteResult execute(float deltaTime) {
		mMan.getInfo().manStates.add( ManStateType.Working );
		
		if(!isAroundBuilding())
		{
			moveToBuilding(deltaTime);
			return ExecuteResult.RUNNING;
		}
		
		if(mMan.getInfo().constructionInfo.proj.getState() == State.CONSTRUCT_PROJ_TRANSPORT)
			return ExecuteResult.RUNNING;
		
		if(mMan.getInfo().constructionInfo.proj.isFinished())
			return ExecuteResult.TRUE;
		
		mMan.getInfo().constructionInfo.proj.getBuilding().doConstructionWork(deltaTime * BUILD_CONTRIBUTE_SPPED);
		
		if(mMan.getInfo().constructionInfo.proj.getBuilding().isConstructionFinished())
		{
			mMan.getInfo().constructionInfo.proj.finished();
			finished();
		}
		
		return ExecuteResult.RUNNING;
	}

}
