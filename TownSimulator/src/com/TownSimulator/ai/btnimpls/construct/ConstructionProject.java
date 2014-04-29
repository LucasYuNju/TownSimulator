package com.TownSimulator.ai.btnimpls.construct;

import java.util.Iterator;

import com.TownSimulator.entity.EntityInfoCollector;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.ResourceType;
import com.TownSimulator.entity.building.Building;

public class ConstructionProject {
	private int							mMaxBuildJobCnt = 4;
	private int							mOpenBuildJobCnt = 3;
	private int							mCurWorkingManCnt = 0;
	private State						mCurStage;
	private Building					mBuilding;
	private Iterator<ResourceType> 		mBuildResourceTypeItr;
	private ResourceType				mCurAllocRsType;
	private int							mCurAllocRsRemainNeedAmount;
	private boolean						mFinished = false;
	
	public enum State
	{
		CONSTRUCT_PROJ_TRANSPORT, CONSTRUCT_PROJ_DOBUILD
	}
	
	public ConstructionProject(Building building)
	{
		mCurStage = State.CONSTRUCT_PROJ_TRANSPORT;
		mBuilding = building;
		mBuilding.setConstructionProject(this);
		mBuildResourceTypeItr = mBuilding.getNeededConstructionResourceTypes().iterator();
		
		EntityInfoCollector.getInstance(EntityInfoCollector.class).addConstructProj(this);
	}
	
	public int getMaxBuildJobCnt() {
		return mMaxBuildJobCnt;
	}

	public void setMaxBuildJobCn(int maxBuildJobCnt) {
		this.mMaxBuildJobCnt = maxBuildJobCnt;
	}

	public void setOpenBuildJobCnt(int openBuildJobCnt) {
		this.mOpenBuildJobCnt = openBuildJobCnt;
	}
	
	public int getOpenBuildJobCnt()
	{
		return mOpenBuildJobCnt;
	}
	
	public int getCurWorkingManCnt()
	{
		return mCurWorkingManCnt;
	}
	
	public int getAvailableBuildJobCnt() {
		return mOpenBuildJobCnt - mCurWorkingManCnt;
	}

	public void addMan(Man man)
	{
		man.getInfo().constructProj = this;
		mCurWorkingManCnt ++;
	}
	
	public boolean remainResourceToTrans()
	{
		if(mCurAllocRsRemainNeedAmount > 0 || mBuildResourceTypeItr.hasNext())
			return true;
		else
			return false;
	}
	
	public boolean isFinished()
	{
		return mFinished;
	}
	
	public void finished()
	{
		mFinished = true;
		mBuilding.setConstructionProject(null);
		EntityInfoCollector.getInstance(EntityInfoCollector.class).removeConstructProj(this);
	}
	
	public boolean allocateTransport(ConstructionInfo cons)
	{
		if(mCurAllocRsRemainNeedAmount <= 0)
		{
			if(mBuildResourceTypeItr.hasNext())
			{
				mCurAllocRsType = mBuildResourceTypeItr.next();
				mCurAllocRsRemainNeedAmount = mBuilding.getNeededConstructionResouceAmount(mCurAllocRsType);
			}
			else
				return false;
		}

		int amount = Math.min(mCurAllocRsRemainNeedAmount, ConstructionTransportBTN.MAX_TRANSPORT_RS_AMOUNT);
		cons.transportRSType = mCurAllocRsType;
		cons.transportNeededAmount = amount;
		mCurAllocRsRemainNeedAmount -= amount;
		
		return true;
	}
	
	public void transportFinsihed()
	{
		if(mBuilding.isConstructionResourceSufficient())
			mCurStage = State.CONSTRUCT_PROJ_DOBUILD;
	}
	
	public State getState()
	{
		return mCurStage;
	}
	
	public Building getBuilding()
	{
		return mBuilding;
	}
	
}
