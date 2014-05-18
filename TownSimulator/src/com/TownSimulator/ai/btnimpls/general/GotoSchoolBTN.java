package com.TownSimulator.ai.btnimpls.general;

import com.TownSimulator.ai.behaviortree.ActionNode;
import com.TownSimulator.ai.behaviortree.ConditionNode;
import com.TownSimulator.ai.behaviortree.ExecuteResult;
import com.TownSimulator.ai.behaviortree.SequenceNode;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.ManAnimeType;
import com.TownSimulator.entity.ManInfo;
import com.TownSimulator.entity.World;
import com.TownSimulator.entity.building.School;

public class GotoSchoolBTN extends SequenceNode{
	private static final long serialVersionUID = 680186532221255950L;
	private Man man;
	private float acountTime;
	
	public GotoSchoolBTN(Man man){
		this.man=man;
		acountTime=0.0f;
		init();
	}
	
	public void init(){
		
		ConditionNode judgeAgeNode=new ConditionNode() {
			private static final long serialVersionUID = 6609696691194948032L;

			@Override
			public ExecuteResult execute(float deltaTime) {
				// TODO Auto-generated method stub
				if(checkAgeOutSchool(man.getInfo().getAge())){
					return ExecuteResult.FALSE;
				}
				return ExecuteResult.TRUE;
			}
		};
		
		ConditionNode judgeSchoolInfoNode=new ConditionNode() {
			private static final long serialVersionUID = 2190149705073170712L;

			@Override
			public ExecuteResult execute(float deltaTime) {
				if(man.getInfo().getSchool()==null){
					return ExecuteResult.FALSE;
				}
				return ExecuteResult.TRUE;
			}
		};
		
		//判读老师
		ConditionNode judgeTeacherNode=new ConditionNode() {
			private static final long serialVersionUID = -837845211936650938L;

			@Override
			public ExecuteResult execute(float deltaTime) {
				// TODO Auto-generated method stub
				if(!man.getInfo().getSchool().isTeacherWork()){
					return ExecuteResult.FALSE;
				}
				return ExecuteResult.TRUE;
			}
		};
		
		ActionNode gotoSchoolNode=new ActionNode() {
			private static final long serialVersionUID = -2334260871309999613L;

			@Override
			public ExecuteResult execute(float deltaTime) {
				gotoSchool(deltaTime);
				return ExecuteResult.TRUE;
			}
		};
		
		this.addNode(judgeAgeNode)
		    .addNode(judgeSchoolInfoNode)
		    .addNode(judgeTeacherNode)
		    .addNode(gotoSchoolNode);
	}
	
	public void gotoSchool(float deltaTime){
		School school=man.getInfo().getSchool();
		if(school==null){
			return;
		}
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
		return (ManInfo.MIN_STUDENT_AGE<=age)&&(age<ManInfo.ADULT_AGE);
	}
	
	public boolean checkAgeOutSchool(int age){
		return (age>=ManInfo.ADULT_AGE)||(age<ManInfo.MIN_STUDENT_AGE);
	}

}
