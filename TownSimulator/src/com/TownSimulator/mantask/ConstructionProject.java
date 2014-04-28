package com.TownSimulator.mantask;

import java.util.Iterator;
import java.util.Random;

import com.TownSimulator.entity.EntityInfoCollector;
import com.TownSimulator.entity.EntityInfoCollectorListener;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.ResourceType;
import com.TownSimulator.entity.building.Building;
import com.TownSimulator.utility.AxisAlignedBoundingBox;
import com.TownSimulator.utility.quadtree.QuadTreeType;
import com.badlogic.gdx.utils.Array;

public class ConstructionProject {
	private int							mMaxBuildJobCnt = 4;
	private int							mOpenBuildJobCnt = 3;
	private Array<Construction> 		mConstructions;
	private State						mCurStage;
	private Building					mBuilding;
	private Iterator<ResourceType> 		mBuildResourceTypeItr;
	private ResourceType				mCurAllocRsType;
	private int							mCurAllocRsRemainNeedAmount;
	private Random						mRand;
	private EntityInfoCollectorListener mEntityInfoCollecListener;
	
	public enum State
	{
		CONSTRUCT_PROJ_TRANSPORT, CONSTRUCT_PROJ_DOBUILD
	}
	
	public ConstructionProject(Building building)
	{
		mConstructions = new Array<Construction>();
		mCurStage = State.CONSTRUCT_PROJ_TRANSPORT;
		mBuilding = building;
		mBuilding.setConstructionProject(this);
		mBuildResourceTypeItr = mBuilding.getNeededBuildResourceTypes().iterator();
		if(mBuildResourceTypeItr.hasNext())
		{
			mCurAllocRsType = mBuildResourceTypeItr.next();
			mCurAllocRsRemainNeedAmount = mBuilding.getNeededBuildResouceAmount(mCurAllocRsType);
		}
		mRand = new Random(System.currentTimeMillis());
		
		mEntityInfoCollecListener = new EntityInfoCollectorListener() {
			
			@Override
			public void manIdle(Man man) {
				if(mConstructions.size < mOpenBuildJobCnt)
					addMan(man);
			}
		};
		EntityInfoCollector.getInstance(EntityInfoCollector.class).addListener(mEntityInfoCollecListener);
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
	
	public int getAvailableBuildJobCnt() {
		return mOpenBuildJobCnt - mConstructions.size;
	}

	public void addMan(Man man)
	{
		Construction cons = new Construction(man, this);
		man.setTask(cons);
		mConstructions.add(cons);
	}
	
	private void allocateConstruct(Construction cons)
	{
		AxisAlignedBoundingBox aabb = mBuilding.getAABBWorld(QuadTreeType.COLLISION);
		float originX = aabb.getCenterX();
		float originY = aabb.getCenterY();
		float randX = (mRand.nextFloat() - 0.5f) * aabb.getWidth() * 0.1f;
		if(randX >= 0 )
			randX += aabb.getWidth() * 0.5f;
		else
			randX -= aabb.getWidth() * 0.5f;
		
		float randY = (mRand.nextFloat() - 0.5f) * aabb.getHeight() * 0.1f;
		if(randY >= 0 )
			randY += aabb.getHeight() * 0.5f;
		else
			randY -= aabb.getHeight() * 0.5f;
		
		cons.goToConstructing(originX + randX, originY + randY);
	}
	
	public void allocate(Construction cons)
	{
		if(mCurStage == State.CONSTRUCT_PROJ_TRANSPORT)
		{
			if( !allocateTransport(cons) )
			{
				if(mBuilding.isBuildResourceSufficient())
				{
					mCurStage = State.CONSTRUCT_PROJ_DOBUILD;
					for (int i = 0; i < mConstructions.size; i++) {
						allocateConstruct(mConstructions.get(i));
					}
				}
				else
					allocateConstruct(cons);
			}
		}
		else if(mCurStage == State.CONSTRUCT_PROJ_DOBUILD)
			allocateConstruct(cons);
	}
	
	int a = 0;
	private boolean allocateTransport(Construction cons)
	{
		if(mCurAllocRsRemainNeedAmount <= 0)
		{
			if(mBuildResourceTypeItr.hasNext())
			{
				mCurAllocRsType = mBuildResourceTypeItr.next();
				mCurAllocRsRemainNeedAmount = mBuilding.getNeededBuildResouceAmount(mCurAllocRsType);
			}
			else
				return false;
		}

		int amount = Math.min(mCurAllocRsRemainNeedAmount, Construction.MAX_TRANSPORT_RS_AMOUNT);
		cons.startTransport(mCurAllocRsType, amount);
		mCurAllocRsRemainNeedAmount -= amount;
		return true;
	}
	
	public void finished()
	{
		for (int i = 0; i < mConstructions.size; i++) {
			mConstructions.get(i).finished();
		}
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
