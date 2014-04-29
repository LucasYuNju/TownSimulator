package com.TownSimulator.ai.btnimpls.construct;

import com.TownSimulator.ai.behaviortree.ActionNode;
import com.TownSimulator.ai.behaviortree.ExcuteResult;
import com.TownSimulator.ai.btnimpls.construct.ConstructProject.State;
import com.TownSimulator.entity.ManAnimeType;
import com.TownSimulator.entity.building.Building;
import com.TownSimulator.utility.quadtree.QuadTreeType;
import com.badlogic.gdx.math.Vector2;

public class ConstructExecuteBTN implements ActionNode{
	public static final int		BUILD_CONTRIBUTE_SPPED = 5;
	public static final float	BUILD_CONTRIBUTE_INTERVAL = 2.0f;
	private ConstructInfo		mConstructInfo;
	private float				mBuildContribueTimeAccum;
	
	public ConstructExecuteBTN(ConstructInfo constructInfo) {
		mConstructInfo = constructInfo; 
	}
	
	private boolean isAroundBuilding()
	{
		float buildingCenterX = mConstructInfo.proj.getBuilding().getAABBWorld(QuadTreeType.COLLISION).getCenterX();
		float buildingCenterY = mConstructInfo.proj.getBuilding().getAABBWorld(QuadTreeType.COLLISION).getCenterY();
		double dist = Math.pow(mConstructInfo.man.getPositionXWorld() - buildingCenterX, 2)
				    + Math.pow(mConstructInfo.man.getPositionYWorld() - buildingCenterY, 2);
		double radius =  Math.pow(mConstructInfo.proj.getBuilding().getAABBWorld(QuadTreeType.COLLISION).getWidth() * 0.5f, 2)
			    		  + Math.pow(mConstructInfo.proj.getBuilding().getAABBWorld(QuadTreeType.COLLISION).getHeight() * 0.5f, 2);
		return dist <= radius + 10.0f;
	}
	
	private void moveToBuilding(float deltaTime)
	{
		float buildingCenterX = mConstructInfo.proj.getBuilding().getAABBWorld(QuadTreeType.COLLISION).getCenterX();
		float buildingCenterY = mConstructInfo.proj.getBuilding().getAABBWorld(QuadTreeType.COLLISION).getCenterY();
		double radius = Math.pow(mConstructInfo.proj.getBuilding().getAABBWorld(QuadTreeType.COLLISION).getWidth() * 0.5f, 2)
	    		  		 + Math.pow(mConstructInfo.proj.getBuilding().getAABBWorld(QuadTreeType.COLLISION).getHeight() * 0.5f, 2);
		radius = Math.pow(radius, 0.5);
		Vector2 moveVec = new Vector2();
		moveVec.x = buildingCenterX - mConstructInfo.man.getPositionXWorld();
		moveVec.y = buildingCenterY - mConstructInfo.man.getPositionYWorld();
		
		Vector2 destination = new Vector2(moveVec);
		moveVec.scl(1.0f / moveVec.len());
		destination.x = destination.x - moveVec.x * (float)radius + mConstructInfo.man.getPositionXWorld();
		destination.y = destination.y - moveVec.y * (float)radius + mConstructInfo.man.getPositionYWorld();
		
		mConstructInfo.man.setMoveDestination(destination.x, destination.y);
		mConstructInfo.man.getInfo().animeType = ManAnimeType.MOVE;
		mConstructInfo.man.move(deltaTime);
	}
	
	private void finished()
	{
		mConstructInfo.proj = null;
		mConstructInfo.man.getInfo().constructProj = null;
		mConstructInfo.man.getInfo().bIdle = true;
	}
	
	@Override
	public ExcuteResult execute(float deltaTime) {
		if(!isAroundBuilding())
		{
			moveToBuilding(deltaTime);
			return ExcuteResult.RUNNING;
		}
		
		if(mConstructInfo.proj.getState() == State.CONSTRUCT_PROJ_TRANSPORT)
			return ExcuteResult.RUNNING;
		
		if(mConstructInfo.proj.isFinished())
			return ExcuteResult.TRUE;
		
		mBuildContribueTimeAccum += deltaTime;
		int cnt = 0;
		while(mBuildContribueTimeAccum >= BUILD_CONTRIBUTE_INTERVAL)
		{
			mBuildContribueTimeAccum -= BUILD_CONTRIBUTE_INTERVAL;
			cnt++;
		}
		
		if(cnt > 0)
		{
			mConstructInfo.proj.getBuilding().incrementConstructionContribute(cnt * BUILD_CONTRIBUTE_SPPED);
			Building building = mConstructInfo.proj.getBuilding();
			System.out.println("Build " + building.getFinishedConstructionContribute() + "/" + building.getUnfinishedConstructionContributes() + building.getFinishedConstructionContribute());
		}
		
		if(mConstructInfo.proj.getBuilding().isConstructionFinished())
		{
			mConstructInfo.proj.finished();
			finished();
		}
		
		return ExcuteResult.RUNNING;
	}

}
