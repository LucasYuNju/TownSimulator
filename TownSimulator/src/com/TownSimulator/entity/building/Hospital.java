package com.TownSimulator.entity.building;

import java.util.ArrayList;
import java.util.List;

import com.TownSimulator.ai.behaviortree.BehaviorTreeNode;
import com.TownSimulator.ai.btnimpls.doctor.DoctorNode;
import com.TownSimulator.entity.JobType;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.ManInfo;
import com.TownSimulator.ui.UIManager;
import com.TownSimulator.ui.building.view.HospitalViewWindow;
import com.TownSimulator.ui.building.view.WorkableViewWindow;
import com.TownSimulator.utility.Singleton;

public class Hospital extends WorkableBuilding{
	private static final long serialVersionUID = -8332826056929917055L;
	private static final int MAX_JOB_CNT = 2;
	private static final int CAPACITY = 10;
	private List<Man> patients;
	protected transient HospitalViewWindow hospitalViewWindow;
	
	public Hospital() {
		super("building_hospital", BuildingType.Hospital, JobType.BARTENDER);
		patients = new ArrayList<Man>();
		updateHospitalViewWindow();
	}

	public boolean addPatient(Man man) {
		if(hasCapacity() && man.getInfo().isSick()) {
			patients.add(man);
			updateHospitalViewWindow();
			return true;
		}
		return false;
	}
	
	public boolean containsPatient(Man man) {
		return patients.contains(man);
	}
	
	public void removePatient(Man man) {
		if(patients.contains(man)) {
			patients.remove(man);
			updateHospitalViewWindow();
		}
	}
	
	public boolean hasCapacity() {
		return patients.size() < CAPACITY;
	}
	
	public List<List<String>> getViewData() {
		List<List<String>> list = new ArrayList<List<String>>();
		for(Man patient : patients) {
			list.add(patient.getInfo().toPatientStringList());
		}
		if(list.isEmpty()) {
			list.add(ManInfo.getEmptyPatientStringList());
		}
		return list;
	}
	
	@Override
	protected  WorkableViewWindow createWorkableWindow() {
		hospitalViewWindow = Singleton.getInstance(UIManager.class).getGameUI().createHospitalViewWindow(getMaxJobCnt());
		return hospitalViewWindow;
	}

	public void updateHospitalViewWindow() {
		hospitalViewWindow.updateData(getViewData());
	}

	@Override
	protected int getMaxJobCnt() {
		return MAX_JOB_CNT;
	}

	@Override
	protected BehaviorTreeNode createBehavior(Man man) {
		return new DoctorNode(man);
	}
	
	/**
	 * man只能属于workers和patients两个列表中的一个
	 */
	@Override
	public void removeWorker(Man man)
	{
		super.removeWorker(man);
		if(patients.remove(man)) {
			updateHospitalViewWindow();
		}
	}
}
