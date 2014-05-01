package com.TownSimulator.ai.btnimpls.construct;

import java.util.Iterator;

import com.TownSimulator.entity.EntityInfoCollector;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.ResourceType;
import com.TownSimulator.entity.building.Building;
import com.badlogic.gdx.utils.Array;

public class ConstructionProject {
	private int							mMaxBuildJobCnt;
	private int							mOpenBuildJobCnt;
	private int							mCurWorkingManCnt = 0;
	private State						mCurStage;
	private Building					mBuilding;
	private Iterator<ResourceType> 		mBuildResourceTypeItr;
	private ResourceType				mCurAllocRsType;
	private int							mCurAllocRsRemainNeedAmount;
	private boolean						mFinished = false;
	private Array<Man>					mWorkers;
	
	public enum State
	{
		CONSTRUCT_PROJ_TRANSPORT, CONSTRUCT_PROJ_DOBUILD
	}
	
	public ConstructionProject(Building building)
	{
		mCurStage = State.CONSTRUCT_PROJ_TRANSPORT;
		mBuilding = building;
		mMaxBuildJobCnt = mBuilding.getMaxAllowdBuilderCnt();
		mOpenBuildJobCnt = mMaxBuildJobCnt;
		mBuilding.setConstructionProject(this);
		mBuildResourceTypeItr = mBuilding.getConstructionResourceTypes().iterator();
		mWorkers = new Array<Man>();
		
		EntityInfoCollector.getInstance(EntityInfoCollector.class).addConstructProj(this);
	}
	
	private void fireWorker(int cnt)
	{
		for (int i = 0; i < cnt; i++) {
			Man man = mWorkers.pop();
			man.getInfo().constructionInfo.bCancel = true;
		}
		mCurWorkingManCnt -= cnt;
	}
	
	public int getMaxWorkerCnt() {
		return mMaxBuildJobCnt;
	}

	public void setMaxWorkerCnt(int maxBuildJobCnt) {
		this.mMaxBuildJobCnt = maxBuildJobCnt;
	}

	public void setOpenWorkJobCnt(int openBuildJobCnt) {
		if(openBuildJobCnt < mCurWorkingManCnt)
			fireWorker(mCurWorkingManCnt - openBuildJobCnt);
		
		this.mOpenBuildJobCnt = openBuildJobCnt;
	}
	
	public int getOpenWorkJobCnt()
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

	public void addWorker(Man man)
	{
		mWorkers.add(man);
		man.getInfo().constructionInfo.proj = this;
		mCurWorkingManCnt ++;
		mBuilding.addBuilder();
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
		mBuilding.setState(Building.State.Constructed);
		EntityInfoCollector.getInstance(EntityInfoCollector.class).removeConstructProj(this);
		
		for (Man man : mWorkers) {
			man.getInfo().constructionInfo.proj = null;
			//man.getInfo().bIdle = true;
		}
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
		cons.transportBuilding = mBuilding;
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
