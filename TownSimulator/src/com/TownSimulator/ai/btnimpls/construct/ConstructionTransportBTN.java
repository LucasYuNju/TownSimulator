package com.TownSimulator.ai.btnimpls.construct;

import com.TownSimulator.ai.behaviortree.ActionNode;
import com.TownSimulator.ai.behaviortree.ExcuteResult;
import com.TownSimulator.entity.EntityInfoCollector;
import com.TownSimulator.entity.EntityInfoCollector.WareHouseFindResult;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.ManAnimeType;
import com.TownSimulator.entity.ResourceInfoCollector;
import com.TownSimulator.entity.building.Building;
import com.TownSimulator.entity.building.Warehouse;

public class ConstructionTransportBTN implements ActionNode{
	public static final int		MAX_TRANSPORT_RS_AMOUNT = 5;
	private State				mCurState;
	//private ConstructionInfo		mMan.getInfo().constructionInfo;
	private Man mMan;
	
	public enum State
	{
		CONSTRUCT_TRANSPORT_TO_WAREHOUSR, CONSTRUCT_TRANSPORT_TO_BUILDING, CONSTRUCT_TRANSPORT_NO_RESOURCE,
	}
	
	public ConstructionTransportBTN(Man man) {
		//mMan.getInfo().constructionInfo = constructInfo;
		mMan = man;
		mCurState = State.CONSTRUCT_TRANSPORT_TO_WAREHOUSR;
	}
	
	private void fetchResource()
	{
		int availableAmount = mMan.getInfo().constructionInfo.transportWareHouse.getStoredResourceAmount(mMan.getInfo().constructionInfo.transportRSType);
		int takeAmount = Math.min(availableAmount, mMan.getInfo().constructionInfo.transportNeededAmount - mMan.getInfo().constructionInfo.curRSAmount);
		mMan.getInfo().constructionInfo.transportWareHouse.addConstructionResource(mMan.getInfo().constructionInfo.transportRSType, -takeAmount);
		mMan.getInfo().constructionInfo.curRSAmount += takeAmount;
		
		ResourceInfoCollector.getInstance(ResourceInfoCollector.class)
		.addResourceAmount(mMan.getInfo().constructionInfo.transportRSType, -takeAmount);
	}
	
	private void addResourceToBuilding()
	{
		Building building = mMan.getInfo().constructionInfo.transportBuilding;
		building.addConstructionResource(mMan.getInfo().constructionInfo.transportRSType, mMan.getInfo().constructionInfo.curRSAmount);
	}
	
	private void findWareHouse()
	{
		ConstructionInfo constructInfo = mMan.getInfo().constructionInfo;
		float x = constructInfo.proj.getBuilding().getPositionXWorld();
		float y = constructInfo.proj.getBuilding().getPositionYWorld();
		WareHouseFindResult findResult = EntityInfoCollector.getInstance(EntityInfoCollector.class)
				.findNearestWareHouseWithRs(constructInfo.transportRSType, constructInfo.transportNeededAmount - constructInfo.curRSAmount, x, y);
		constructInfo.transportWareHouse = findResult.wareHouse;
	}

	@Override
	public ExcuteResult execute(float deltaTime) {
		
		if(mMan.getInfo().constructionInfo.transportNeededAmount == 0)
		{
			mMan.getInfo().constructionInfo.proj.allocateTransport(mMan.getInfo().constructionInfo);
			findWareHouse();
			if(mMan.getInfo().constructionInfo.transportWareHouse == null)
				mCurState = State.CONSTRUCT_TRANSPORT_NO_RESOURCE;
		}
		
		switch (mCurState) {
		case CONSTRUCT_TRANSPORT_TO_WAREHOUSR:
			Warehouse wareHouse = mMan.getInfo().constructionInfo.transportWareHouse;
			mMan.setMoveDestination(wareHouse.getPositionXWorld(), wareHouse.getPositionYWorld());
			mMan.getInfo().animeType = ManAnimeType.MOVE;
			
			if( !mMan.move(deltaTime) )
			{
				fetchResource();
				if(mMan.getInfo().constructionInfo.curRSAmount < mMan.getInfo().constructionInfo.transportNeededAmount)
				{
					findWareHouse();
					if(mMan.getInfo().constructionInfo.transportWareHouse == null)
					{
						mCurState = State.CONSTRUCT_TRANSPORT_NO_RESOURCE;
						mMan.getInfo().animeType = ManAnimeType.STANDING;
					}
					//else
					//	mMan.setMoveDestination(mMan.getInfo().constructionInfo.transportWareHouse.getPositionXWorld(), mMan.getInfo().constructionInfo.transportWareHouse.getPositionYWorld());
				}
				else
					mCurState = State.CONSTRUCT_TRANSPORT_TO_BUILDING;
			}
			break;
			
		case CONSTRUCT_TRANSPORT_TO_BUILDING:
			Building building = mMan.getInfo().constructionInfo.transportBuilding;
			mMan.setMoveDestination(building.getPositionXWorld(), building.getPositionYWorld());
			mMan.getInfo().animeType = ManAnimeType.MOVE;
			
			if( !mMan.move(deltaTime) )
			{
				//fetchResource();
				addResourceToBuilding();
				mMan.getInfo().constructionInfo.transportBuilding = null;
				mMan.getInfo().constructionInfo.transportWareHouse = null;
				mMan.getInfo().constructionInfo.transportRSType = null;
				mMan.getInfo().constructionInfo.transportNeededAmount = 0;
				mMan.getInfo().constructionInfo.proj.transportFinsihed();
				
				mCurState = State.CONSTRUCT_TRANSPORT_TO_WAREHOUSR;
				
				if(mMan.getInfo().constructionInfo.bCancel)
				{
					mMan.getInfo().constructionInfo.proj = null;
					mMan.getInfo().constructionInfo.bCancel = false;
				}
				
				return ExcuteResult.TRUE;
			}
			break;
			
		case CONSTRUCT_TRANSPORT_NO_RESOURCE:
			mMan.stopMove();
			mMan.getInfo().animeType = ManAnimeType.STANDING;
			break;
		}
		
		return ExcuteResult.RUNNING;
	}
	
	
}
