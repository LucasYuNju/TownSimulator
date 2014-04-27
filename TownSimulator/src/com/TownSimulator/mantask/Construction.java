package com.TownSimulator.mantask;

import com.TownSimulator.entity.EntityInfoCollector;
import com.TownSimulator.entity.EntityInfoCollector.WareHouseFindResult;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.ResourceType;
import com.TownSimulator.entity.building.WareHouse;
import com.TownSimulator.utility.quadtree.QuadTreeType;

public class Construction extends Task{
	public static final int		MAX_TRANSPORT_RS_AMOUNT = 5;
	public static final int		BUILD_CONTRIBUTE_SPPED = 5;
	public static final float	BUILD_CONTRIBUTE_INTERVAL = 2.0f;
	private ConstructionProject mProject;
	private State				mCurState;
	private WareHouse			mTransportWareHouse;
	private ResourceType		mTransportResourceType;
	private int					mTransportResourceNeed;
	private int					mTransportResourceCur;
	private float				mBuildContribueTimeAccum;
	private RandMove			mRandMove;
	
	public enum State
	{
		CONSTRUCT_TRANSPORT_TO_WAREHOUSR, CONSTRUCT_TRANSPORT_TO_BUILDING, CONSTRUCT_TRANSPORT_NO_RESOURCE,
		CONSTRUCT_EXCUTE_WAIT, CONSTRUCT_EXCUTE_MOVE, CONSTRUCT_EXCUTE
	}
	
	public Construction(Man man, ConstructionProject project) {
		super(man);
		
		mProject = project;
		mRandMove = new RandMove(mMan);
		
		project.allocate(this);
	}
	
	private void findWareHouse()
	{
		float x = mProject.getBuilding().getPositionXWorld();
		float y = mProject.getBuilding().getPositionYWorld();
		WareHouseFindResult findResult = EntityInfoCollector.getInstance(EntityInfoCollector.class)
				.findNearestWareHouseWithRs(mTransportResourceType, mTransportResourceNeed - mTransportResourceCur, x, y);
		mTransportWareHouse = findResult.wareHouse;
	}
	
	public void startTransport(ResourceType type, int amount)
	{
		mTransportResourceType = type;
		mTransportResourceNeed = amount;
		mTransportResourceCur = 0;
		mTransportWareHouse = null;
		
		findWareHouse();
		if(mTransportWareHouse == null)
		{
			mCurState = State.CONSTRUCT_TRANSPORT_NO_RESOURCE;
			mMan.setState(Man.State.MAN_STADING);
		}
		else
		{
			mCurState = State.CONSTRUCT_TRANSPORT_TO_WAREHOUSR;
			mMan.setState(Man.State.MAN_MOVE);
			mMan.setMoveDestination(mTransportWareHouse.getPositionXWorld(), mTransportWareHouse.getPositionYWorld());
		}
	}
	
	public void finished()
	{
		mMan.setTask(new RandMove(mMan));
	}
	
//	public void excuteConstruct()
//	{
//		if(mCurState == State.CONSTRUCT_EXCUTE_MOVE)
//			return;
//		else if(mCurState == State.CONSTRUCT_EXCUTE_WAIT)
//			mCurState = State.CONSTRUCT_EXCUTE;
//	}
	
	
	public void goToConstructing(float x, float y)
	{
		mMan.setMoveDestination(x, y);
		mCurState = State.CONSTRUCT_EXCUTE_MOVE;
		mMan.setState(Man.State.MAN_MOVE);
		mMan.setMoveDestination(x, y);
	}
	
	private void fetchResource()
	{
		int availableAmount = mTransportWareHouse.getWareHousrResourceAmount(mTransportResourceType);
		int takeAmount = Math.min(availableAmount, mTransportResourceNeed - mTransportResourceCur);
		mTransportWareHouse.addCurBuildResource(mTransportResourceType, -takeAmount);
		mTransportResourceCur += takeAmount;
	}
	
	private void addResourceToBuilding()
	{
		mProject.getBuilding().addCurBuildResource(mTransportResourceType, mTransportResourceCur);
	}

	@Override
	public void update(float deltaTime) {
		
		switch (mCurState) {
		case CONSTRUCT_TRANSPORT_TO_WAREHOUSR:
			if( !mMan.move(deltaTime) )
			{
				fetchResource();
				if(mTransportResourceCur < mTransportResourceNeed)
				{
					findWareHouse();
					if(mTransportWareHouse == null)
					{
						mCurState = State.CONSTRUCT_TRANSPORT_NO_RESOURCE;
						mMan.setState(Man.State.MAN_STADING);
					}
					else
						mMan.setMoveDestination(mTransportWareHouse.getPositionXWorld(), mTransportWareHouse.getPositionYWorld());
				}
				else
				{
					mCurState = State.CONSTRUCT_TRANSPORT_TO_BUILDING;
					mMan.setState(Man.State.MAN_MOVE);
					mMan.setMoveDestination(mProject.getBuilding().getPositionXWorld(), mProject.getBuilding().getPositionYWorld());
				}
			}
			break;
			
		case CONSTRUCT_TRANSPORT_TO_BUILDING:
			if( !mMan.move(deltaTime) )
			{
				addResourceToBuilding();
				mProject.allocate(this);
			}
			break;
			
		case CONSTRUCT_TRANSPORT_NO_RESOURCE:
			break;
			
		case CONSTRUCT_EXCUTE_WAIT:
			mRandMove.update(deltaTime);
			return;
			
		case CONSTRUCT_EXCUTE_MOVE:
			if( !mMan.move(deltaTime) )
			{
				if(mProject.getState() == ConstructionProject.State.CONSTRUCT_PROJ_TRANSPORT)
					mCurState = State.CONSTRUCT_EXCUTE_WAIT;
				else if(mProject.getState() == ConstructionProject.State.CONSTRUCT_PROJ_DOBUILD)
				{
					mCurState = State.CONSTRUCT_EXCUTE;
					if(mMan.getPositionXWorld() < mProject.getBuilding().getAABBWorld(QuadTreeType.COLLISION).getCenterX())
						mMan.setFlip(true);
					else
						mMan.setFlip(false);
				}
				mMan.setState(Man.State.MAN_STADING);
			}
			break;
			
		case CONSTRUCT_EXCUTE:
			mBuildContribueTimeAccum += deltaTime;
			int cnt = 0;
			while(mBuildContribueTimeAccum >= BUILD_CONTRIBUTE_INTERVAL)
			{
				mBuildContribueTimeAccum -= BUILD_CONTRIBUTE_INTERVAL;
				cnt++;
			}
			
			if(cnt > 0)
			{
				mProject.getBuilding().addCurBuildContributes(cnt * BUILD_CONTRIBUTE_SPPED);
				System.out.println("Build " + mProject.getBuilding().getCurBuildContributes() + "/" + mProject.getBuilding().getNeededBuildContributes());
			}
			
			if(mProject.getBuilding().isBuildFinished())
				mProject.finished();
			break;
			
		default:
			break;
		}
		
		mMan.updateSprite(deltaTime);
	}

}
