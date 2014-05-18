package com.TownSimulator.entity.building;

import java.util.List;

import com.TownSimulator.ai.behaviortree.BehaviorTreeNode;
import com.TownSimulator.ai.btnimpls.teacher.TeacherBTN;
import com.TownSimulator.entity.EntityInfoCollector;
import com.TownSimulator.entity.JobType;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.ManInfo;
import com.TownSimulator.entity.World;
import com.TownSimulator.ui.UIManager;
import com.TownSimulator.ui.building.view.SchoolViewWindow;
import com.TownSimulator.ui.building.view.WorkableViewWindow;

public class School extends WorkableBuilding{
	private static final long serialVersionUID = 5070279529268945444L;
	public static final float WORKEFFIENCY_GROW_SPEED_PERYEAR=0.1f;
	//public boolean isSchoolConstruct=false;
	private boolean isTeacherWork;
	public static final int SingleSchoolStudentNum=30;
	private int currentStudentNum;
	private SchoolViewWindow schoolViewWindow;
	
	public School(){
		super("building_bar", BuildingType.SCHOOL,JobType.TEACHER);
		currentStudentNum=0;
		schoolViewWindow=(SchoolViewWindow)undockedWindow;
	}

	@Override
	protected int getMaxJobCnt() {
		return 1;
	}

	@Override
	protected BehaviorTreeNode createBehavior(Man man) {
		return new TeacherBTN(man);
	}
	
	@Override
	public void addWorker(Man man) {
		super.addWorker(man);
		this.isTeacherWork=true;
	}

	@Override
	public void removeWorker(Man man) {
		super.removeWorker(man);
		this.isTeacherWork=false;
	}
	
	public boolean isTeacherWork(){
		return isTeacherWork;
	}

	@Override
	public void setState(State state) {
		super.setState(state);
		
		if(state==Building.State.Constructed){
			World.getInstance(World.class).updateSchoolInfo();
			initStudentNum();
		}
	}
	
	public void initStudentNum(){
		List<Man> mansArray=EntityInfoCollector.getInstance(EntityInfoCollector.class).getAllMan();
		for(Man man:mansArray){
			School school=EntityInfoCollector.getInstance(EntityInfoCollector.class).
					   findNearestSchool( man.getPositionXWorld(), man.getPositionYWorld());
			if(!checkAgeInSchool(man.getInfo().getAge())){
				continue;
			}
			if(school==null){
				continue;
			}
			if(school.equals(School.this)){
				this.currentStudentNum++;
				man.getInfo().setSchool(school);
			}
		}
	}
	
	public boolean checkAgeInSchool(int age){
		return (ManInfo.MIN_STUDENT_AGE<=age)&&(age<ManInfo.ADULT_AGE);
	}
	
	@Override
	protected WorkableViewWindow createWorkableWindow() {
		return UIManager.getInstance(UIManager.class).getGameUI().createSchoolViewWindow(getMaxJobCnt(),currentStudentNum);
	}

	@Override
	public void updateViewWindow() {
		super.updateViewWindow();
		schoolViewWindow.updateStudentNum(this.currentStudentNum);
	}

	public int getCurrentStudentNum() {
		return currentStudentNum;
	}

	public void changeCurrentStudentNum(int num) {
		this.currentStudentNum++;
		updateViewWindow();
	}
}
