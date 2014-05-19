package com.TownSimulator.ai.btnimpls.construct;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.TownSimulator.entity.EntityInfoCollector;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.Resource;
import com.TownSimulator.entity.ResourceType;
import com.TownSimulator.entity.building.Building;

public class ConstructionProject implements Serializable{
	private static final long serialVersionUID = -4485826516375527453L;
	private int							mMaxBuildJobCnt;
	private int							mOpenBuildJobCnt;
	private int							mCurWorkingManCnt = 0;
	private State						mCurStage;
	private Building					mBuilding;
	private List<Resource>				mBuildResource;
	private Resource					mCurAllocRs;
	private boolean						mFinished = false;
	private List<Man>					mWorkers;
	
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
		mBuildResource = new ArrayList<Resource>();
		mWorkers = new ArrayList<Man>();
		
		EntityInfoCollector.getInstance(EntityInfoCollector.class).addConstructProj(this);
		
		Iterator<ResourceType> itr = mBuilding.getConstructionResourceTypes().iterator();
		while(itr.hasNext())
		{
			ResourceType type = itr.next();
			int amount = mBuilding.getNeededConstructionResouceAmount(type);
			mBuildResource.add(new Resource(type, amount));
		}
		
		mCurAllocRs = mBuildResource.remove(mBuildResource.size() - 1);
	}
	
	public void destroy()
	{
		for (Man man : mWorkers) {
			man.getInfo().constructionInfo = new ConstructionInfo();
		}
		mWorkers.clear();
		EntityInfoCollector.getInstance(EntityInfoCollector.class).removeConstructProj(this);
	}
	
	private void fireWorker(int cnt)
	{
		for (int i = 0; i < cnt; i++) {
			Man man = mWorkers.remove(mWorkers.size() - 1);
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
		System.out.println(openBuildJobCnt);
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
	
	public void removeWorker(Man man)
	{
		if(mWorkers.remove(man))
		{
			man.getInfo().constructionInfo.proj = null;
			if(man.getInfo().constructionInfo.transportNeededAmount > 0)
			{
				ResourceType type = man.getInfo().constructionInfo.transportRSType;
				int amount = man.getInfo().constructionInfo.transportNeededAmount;
				mBuildResource.add(new Resource(type, amount));
			}
		}
	}
	
	public boolean remainResourceToTrans()
	{
		//if(mCurAllocRsRemainNeedAmount > 0 || mBuildResourceTypeItr.hasNext())
		if( (mCurAllocRs != null && mCurAllocRs.getAmount() > 0)
				|| mBuildResource.size() > 0)
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
		
		if(mCurAllocRs.getAmount() <= 0)
		{
			if(mBuildResource.size() > 0)
			{
				mCurAllocRs = mBuildResource.remove(mBuildResource.size() - 1);
			}
			else
				return false;
		}

		int amount = Math.min(mCurAllocRs.getAmount(), ConstructionTransportBTN.MAX_TRANSPORT_RS_AMOUNT);
		cons.transportRSType = mCurAllocRs.getType();
		cons.transportNeededAmount = amount;
		cons.transportBuilding = mBuilding;
		mCurAllocRs.addAmount(-amount);
		
		//mCurAllocRsRemainNeedAmount -= amount;
		
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
