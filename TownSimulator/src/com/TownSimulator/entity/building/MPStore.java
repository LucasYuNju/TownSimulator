package com.TownSimulator.entity.building;

import com.TownSimulator.entity.World;

public class MPStore extends MoneyProducingBuilding{
	private static final long serialVersionUID = 6936394803389627481L;
	private static final float PRODUCING_INTERVAL = World.SecondPerYear / 300;
	private static final int   PRODUCING_AMOUNT = 1;
	
	public MPStore() {
		super("building_mp_store", BuildingType.MP_Store);
	}

	@Override
	protected float getProduceTimeInterval() {
		return PRODUCING_INTERVAL;
	}

	@Override
	protected int getProduceAmountPerMan() {
		return PRODUCING_AMOUNT;
	}

	@Override
	protected int getMaxJobCnt() {
		return 1;
	}

}
