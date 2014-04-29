package com.TownSimulator.ai.btnimpls.construct;

import com.TownSimulator.ai.behaviortree.ActionNode;
import com.TownSimulator.ai.behaviortree.ExcuteResult;
import com.TownSimulator.entity.EntityInfoCollector;
import com.TownSimulator.entity.EntityInfoCollector.WareHouseFindResult;
import com.TownSimulator.entity.ManAnimeType;

public class ConstructTransportBTN implements ActionNode{
	public static final int		MAX_TRANSPORT_RS_AMOUNT = 5;
	private State				mCurState;
	private ConstructInfo		mConstructInfo;
	
	public enum State
	{
		CONSTRUCT_TRANSPORT_TO_WAREHOUSR, CONSTRUCT_TRANSPORT_TO_BUILDING, CONSTRUCT_TRANSPORT_NO_RESOURCE,
	}
	
	public ConstructTransportBTN(ConstructInfo constructInfo) {
		mConstructInfo = constructInfo;
		mCurState = State.CONSTRUCT_TRANSPORT_TO_WAREHOUSR;
	}
	
	private void fetchResource()
	{
		int availableAmount = mConstructInfo.transportWareHouse.getWareHousrResourceAmount(mConstructInfo.transportRSType);
		int takeAmount = Math.min(availableAmount, mConstructInfo.transportNeededAmount - mConstructInfo.curRSAmount);
		mConstructInfo.transportWareHouse.addConstructionResource(mConstructInfo.transportRSType, -takeAmount);
		mConstructInfo.curRSAmount += takeAmount;
	}
	
	private void addResourceToBuilding()
	{
		mConstructInfo.proj.getBuilding().addConstructionResource(mConstructInfo.transportRSType, mConstructInfo.curRSAmount);
	}
	
	private void findWareHouse()
	{
		float x = mConstructInfo.proj.getBuilding().getPositionXWorld();
		float y = mConstructInfo.proj.getBuilding().getPositionYWorld();
		WareHouseFindResult findResult = EntityInfoCollector.getInstance(EntityInfoCollector.class)
				.findNearestWareHouseWithRs(mConstructInfo.transportRSType, mConstructInfo.transportNeededAmount - mConstructInfo.curRSAmount, x, y);
		mConstructInfo.transportWareHouse = findResult.wareHouse;
	}

	@Override
	public ExcuteResult execute(float deltaTime) {
		
		if(mConstructInfo.transportNeededAmount == 0)
		{
			mConstructInfo.proj.allocateTransport(mConstructInfo);
			findWareHouse();
			if(mConstructInfo.transportWareHouse == null)
				mCurState = State.CONSTRUCT_TRANSPORT_NO_RESOURCE;
		}
		
		switch (mCurState) {
		case CONSTRUCT_TRANSPORT_TO_WAREHOUSR:
			mConstructInfo.man.setMoveDestination(mConstructInfo.transportWareHouse.getPositionXWorld(), mConstructInfo.transportWareHouse.getPositionYWorld());
			mConstructInfo.man.getInfo().animeType = ManAnimeType.MOVE;
			
			if( !mConstructInfo.man.move(deltaTime) )
			{
				fetchResource();
				if(mConstructInfo.curRSAmount < mConstructInfo.transportNeededAmount)
				{
					findWareHouse();
					if(mConstructInfo.transportWareHouse == null)
					{
						mCurState = State.CONSTRUCT_TRANSPORT_NO_RESOURCE;
						mConstructInfo.man.getInfo().animeType = ManAnimeType.STANDING;
					}
					else
						mConstructInfo.man.setMoveDestination(mConstructInfo.transportWareHouse.getPositionXWorld(), mConstructInfo.transportWareHouse.getPositionYWorld());
				}
				else
				{
					mCurState = State.CONSTRUCT_TRANSPORT_TO_BUILDING;
					mConstructInfo.man.getInfo().animeType = ManAnimeType.MOVE;
					mConstructInfo.man.setMoveDestination(mConstructInfo.proj.getBuilding().getPositionXWorld(), mConstructInfo.proj.getBuilding().getPositionYWorld());
				}
			}
			break;
			
		case CONSTRUCT_TRANSPORT_TO_BUILDING:
			mConstructInfo.man.setMoveDestination(mConstructInfo.proj.getBuilding().getPositionXWorld(), mConstructInfo.proj.getBuilding().getPositionYWorld());
			mConstructInfo.man.getInfo().animeType = ManAnimeType.MOVE;
			
			if( !mConstructInfo.man.move(deltaTime) )
			{
				addResourceToBuilding();
				mConstructInfo.transportWareHouse = null;
				mConstructInfo.transportRSType = null;
				mConstructInfo.transportNeededAmount = 0;
				mConstructInfo.proj.transportFinsihed();
				
				mCurState = State.CONSTRUCT_TRANSPORT_TO_WAREHOUSR;
				return ExcuteResult.TRUE;
			}
			break;
			
		case CONSTRUCT_TRANSPORT_NO_RESOURCE:
			break;
		}
		
		return ExcuteResult.RUNNING;
	}
	
	
}
