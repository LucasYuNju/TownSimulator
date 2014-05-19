package com.TownSimulator.entity.building;

import java.io.IOException;
import java.io.ObjectInputStream;
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
	private boolean isTeacherWork;
	public static final int SingleSchoolStudentNum=30;
	private int currentStudentNum;
	private transient SchoolViewWindow schoolViewWindow;
	
	public School(){
		super("building_school", BuildingType.SCHOOL,JobType.TEACHER);
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
//		initStudents();
		updateStudentNum();
	}

	@Override
	public void removeWorker(Man man) {
		super.removeWorker(man);
		this.isTeacherWork=false;
		removeStudents();
		updateStudentNum();
	}
	
	public boolean isTeacherWork(){
		return isTeacherWork;
	}

	@Override
	public void setState(State state) {
		super.setState(state);
		
		if(state==Building.State.Constructed){
			World.getInstance(World.class).updateSchoolInfo();
//			initStudents();
//			Driver.getInstance(Driver.class).addListener(new DriverListenerBaseImpl(){
//
//				@Override
//				public void update(float deltaTime) {
//				}
//			});
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
	
	public void removeStudents(){
		List<Man> mansArray=EntityInfoCollector.getInstance(EntityInfoCollector.class).getAllMan();
		for(Man man:mansArray){
			if(man.getInfo().getSchool()==null){
				continue;
			}
			if(man.getInfo().getSchool().equals(School.this)){
				this.currentStudentNum--;
				man.getInfo().setSchool(null);
			}
		}
	}
	
	public boolean checkAgeInSchool(int age){
		return (ManInfo.MIN_STUDENT_AGE<=age)&&(age<ManInfo.ADULT_AGE);
	}
	
	@Override
	protected WorkableViewWindow createWorkableWindow() {
		schoolViewWindow = UIManager.getInstance(UIManager.class).getGameUI().createSchoolViewWindow(getMaxJobCnt(),currentStudentNum);
		return schoolViewWindow;
	}

	public void updateStudentNum(){
		schoolViewWindow.updateStudentNum(this.currentStudentNum);
	}

	public int getCurrentStudentNum() {
		return currentStudentNum;
	}

	public void changeCurrentStudentNum(int num) {
		this.currentStudentNum++;
		updateViewWindow();
	}

	@Override
	public void setOpenJobCnt(int openJobCnt) {
		super.setOpenJobCnt(openJobCnt);	
	}

	@Override
	protected void workerLimitChanged(int limit) {
		super.workerLimitChanged(limit);
		this.isTeacherWork=false;
		removeStudents();
		updateStudentNum();	
	}
	
	public void updateViewWindow() {
		schoolViewWindow.updateStudentNum(this.currentStudentNum);
	}
	
	private void readObject(ObjectInputStream s) throws ClassNotFoundException, IOException {
		s.defaultReadObject();
		updateViewWindow();
	}

//	
//	protected void reloadViewWindow() {
//		updateViewWindow();
//	}
}