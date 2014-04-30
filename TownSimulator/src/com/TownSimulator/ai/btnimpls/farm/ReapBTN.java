package com.TownSimulator.ai.btnimpls.farm;

import com.TownSimulator.ai.behaviortree.ConditionNode;
import com.TownSimulator.ai.behaviortree.ExcuteResult;
import com.TownSimulator.ai.behaviortree.SelectorNode;
import com.TownSimulator.ai.behaviortree.SequenceNode;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.World;
import com.TownSimulator.entity.World.SeasonType;
import com.TownSimulator.entity.building.FarmHouse;
import com.TownSimulator.entity.building.FarmLand;

public class ReapBTN extends SequenceNode{
	private Man man;
	
	public ReapBTN(Man man)
	{
		this.man = man;
		
		init();
	}
	
	private void init()
	{
		ConditionNode isReapStart = new ConditionNode() {
			
			@Override
			public ExcuteResult execute(float deltaTime) {
				FarmHouse farmHouse = ((FarmHouse)man.getInfo().workingBuilding);
				if(farmHouse.isReapStart())
					return ExcuteResult.TRUE;
				else
					return ExcuteResult.FALSE;
			}
		};
		
		ConditionNode judgeLeftCrop = new ConditionNode() {
			
			@Override
			public ExcuteResult execute(float deltaTime) {
				FarmHouse farmHouse = ((FarmHouse)man.getInfo().workingBuilding);
				float remainCropAmount = 0.0f;
				for (FarmLand land : farmHouse.getFarmLands()) {
					remainCropAmount += land.getCurCropAmount();
				}
				
				if(remainCropAmount > 0.0f)
					return ExcuteResult.TRUE;
				else
					return ExcuteResult.FALSE;
			}
		};
		
		ConditionNode judgeTime = new ConditionNode() {
			
			@Override
			public ExcuteResult execute(float deltaTime) {
				if( World.getInstance(World.class).getCurSeason() == SeasonType.Winter )
					return ExcuteResult.TRUE;
				else
					return ExcuteResult.FALSE;
			}
		};
		
		ConditionNode isCropFull = new ConditionNode() {
			
			@Override
			public ExcuteResult execute(float deltaTime) {
				FarmHouse farmHouse = ((FarmHouse)man.getInfo().workingBuilding);
				for (FarmLand land : farmHouse.getFarmLands()) {
					if(land.getCurCropAmount() < FarmLand.MAX_CROP_AMOUNT)
						return ExcuteResult.FALSE;
				}
				
				return ExcuteResult.TRUE;
			}
		};
		
		this.addNode( new SelectorNode().addNode(isReapStart)
										.addNode( new SequenceNode().addNode(judgeLeftCrop)
																	.addNode( new SelectorNode().addNode(judgeTime)
																								.addNode(isCropFull)
																			)
												)
					)
			.addNode(new ReapExexcuteBTN(man));
	}
	
}
