package com.TownSimulator.ai.btnimpls.general;

import com.TownSimulator.ai.behaviortree.ActionNode;
import com.TownSimulator.ai.behaviortree.ConditionNode;
import com.TownSimulator.ai.behaviortree.ExecuteResult;
import com.TownSimulator.ai.behaviortree.SequenceNode;
import com.TownSimulator.entity.EntityInfoCollector.BuildingFindResult;
import com.TownSimulator.entity.EntityInfoCollector;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.ManAnimeType;
import com.TownSimulator.entity.ManInfo;
import com.TownSimulator.entity.World;
import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.entity.building.School;

public class GotoSchoolBTN extends SequenceNode{
	
	private Man man;
	private float acountTime;
	
	public GotoSchoolBTN(Man man){
		this.man=man;
		acountTime=0.0f;
		init();
	}
	
	public void init(){
		
		ConditionNode judgeAgeNode=new ConditionNode() {
			
			@Override
			public ExecuteResult execute(float deltaTime) {
				// TODO Auto-generated method stub
				if(checkAgeOutSchool(man.getInfo().getAge())){
					return ExecuteResult.FALSE;
				}
				return ExecuteResult.TRUE;
			}
		};
		
		ConditionNode judgeSchoolConstructNode=new ConditionNode() {
			
			@Override
			public ExecuteResult execute(float deltaTime) {
				// TODO Auto-generated method stub
				if((!World.getInstance(World.class).isSchoolConstructed())){
					return ExecuteResult.FALSE;
				}
				return ExecuteResult.TRUE;
			}
		};
		
		//判读老师和学生放在寻找建筑里做，只用做一次
//		ConditionNode judgeTeacherAndStudentLimitConditionNode=new ConditionNode() {
//			
//			@Override
//			public ExecuteResult execute(float deltaTime) {
//				// TODO Auto-generated method stub
//				
//				return ExecuteResult.TRUE;
//			}
//		};
		
		ActionNode gotoSchoolNode=new ActionNode() {
			
			@Override
			public ExecuteResult execute(float deltaTime) {
				// TODO Auto-generated method stub
				gotoSchool(deltaTime);
				return ExecuteResult.TRUE;
			}
		};
		
		this.addNode(judgeAgeNode)
		    .addNode(judgeSchoolConstructNode)
		    .addNode(gotoSchoolNode);
	}

//	@Override
//	public ExecuteResult execute(float deltaTime) {
//		// TODO Auto-generated method stub
//		if(checkAgeOutSchool(man.getInfo().getAge())){
//			return ExecuteResult.FALSE;
//		}
//		if((!World.getInstance(World.class).isSchoolConstructed())&&(!World.getInstance(World.class).isTeacherWork())){
//			return ExecuteResult.FALSE;
//		}
//		if(checkAgeInSchool(man.getInfo().getAge())){
//			gotoSchool(deltaTime);
//			return ExecuteResult.TRUE;
//		}
//			
//		return ExecuteResult.FALSE;
//	}
	
	public void gotoSchool(float deltaTime){
		School school=EntityInfoCollector.getInstance(EntityInfoCollector.class).
				findNearestSchool( man.getPositionXWorld(), man.getPositionYWorld());
		if(school==null){
			return;
		}
		school.growCurrentStudentNum();
		man.setMoveDestination(school.getPositionXWorld(), school.getPositionYWorld());
		if(man.move(deltaTime)){
			man.getInfo().animeType=ManAnimeType.MOVE;
		}
		if((acountTime+=deltaTime)>=World.SecondPerYear){
			acountTime-=World.SecondPerYear;
			man.getInfo().growWorkEfficency(School.WORKEFFIENCY_GROW_SPEED_PERYEAR);
		}
	}
	
	public boolean checkAgeInSchool(int age){
		return (ManInfo.MIN_STUDENT_AGE<=age)&&(age<ManInfo.MAX_STUDENT_AGE);
	}
	
	public boolean checkAgeOutSchool(int age){
		return (age>=ManInfo.MAX_STUDENT_AGE)||(age<ManInfo.MIN_STUDENT_AGE);
	}

}
