package com.TownSimulator.ai.btnimpls.farmer;

import com.TownSimulator.ai.behaviortree.ConditionNode;
import com.TownSimulator.ai.behaviortree.ExecuteResult;
import com.TownSimulator.ai.behaviortree.SelectorNode;
import com.TownSimulator.ai.behaviortree.SequenceNode;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.SeasonType;
import com.TownSimulator.entity.World;
import com.TownSimulator.entity.building.FarmHouse;

public class SowBTN extends SequenceNode{
	private Man man;
	
	public SowBTN(Man man){
		this.man=man;
		init();
	}
	
	private void init(){
		
		ConditionNode isSowStart=new ConditionNode() {
			
			@Override
			public ExecuteResult execute(float deltaTime) {
				// TODO Auto-generated method stub
				FarmHouse farmHouse=(FarmHouse)man.getInfo().workingBuilding;
				if(!farmHouse.isSowStart())//未播种
					return ExecuteResult.TRUE;
				return ExecuteResult.FALSE;
			}
		};
		
		ConditionNode judgeTimeSuitable=new ConditionNode() {
			
			@Override
			public ExecuteResult execute(float deltaTime) {
				// TODO Auto-generated method stub
				if(World.getInstance(World.class).getCurSeason()==SeasonType.Winter)
					return ExecuteResult.FALSE;
				return ExecuteResult.TRUE;
			}
		};
		
		ConditionNode judgeRoomEnough=new ConditionNode() {
			
			@Override
			public ExecuteResult execute(float deltaTime) {
				// TODO Auto-generated method stub
				FarmHouse farmHouse = ((FarmHouse)man.getInfo().workingBuilding);
				if(farmHouse.isSowed()){
					return ExecuteResult.FALSE;
				}
				return ExecuteResult.TRUE;
			}
		};
		
		ConditionNode judgeCropSelect=new ConditionNode() {
			
			@Override
			public ExecuteResult execute(float deltaTime) {
				FarmHouse farmHouse = ((FarmHouse)man.getInfo().workingBuilding);
				if(farmHouse.getSowCropType() == null){
					return ExecuteResult.FALSE;
				}
				return ExecuteResult.TRUE;
			}
		};
		
		this.addNode(new SelectorNode().addNode(isSowStart)
				                       .addNode(new SequenceNode().addNode(judgeTimeSuitable)
				                                                  .addNode(judgeRoomEnough)
				                                                  .addNode(judgeCropSelect)
				                               )
				    )
		    .addNode(new SowExecuteBTN(man));
		
	}
}
