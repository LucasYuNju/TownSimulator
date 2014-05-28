package com.TownSimulator.entity.building;

import com.TownSimulator.ai.behaviortree.BehaviorTreeNode;
import com.TownSimulator.ai.btnimpls.construct.BuilderBTN;
import com.TownSimulator.entity.JobType;
import com.TownSimulator.entity.Man;

public class ConstructionCompany extends WorkableBuilding{
	private static final long serialVersionUID = 1L;

	public ConstructionCompany() {
		super("building_construction_company", BuildingType.ConstrctionCompany, JobType.Builder);
	}

	@Override
	protected int getMaxJobCnt() {
		return 3;
	}

	@Override
	protected BehaviorTreeNode createBehavior(Man man) {
		return new BuilderBTN(man);
	}

}
