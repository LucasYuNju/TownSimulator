package com.TownSimulator.entity.building;

import java.util.ArrayList;
import java.util.List;

import com.TownSimulator.entity.Man;
import com.TownSimulator.ui.UIManager;
import com.TownSimulator.ui.building.view.ScrollViewWindow;
import com.TownSimulator.ui.building.view.UndockedWindow;
import com.TownSimulator.utility.Singleton;

public class Hospital extends Building{
	private List<Man> patients;
	private int capacity = 10;
	private int numPatient = 0;
	protected ScrollViewWindow scrollWindow;
	
	public Hospital() {
		super("building_hospital", BuildingType.Hospital);
		patients = new ArrayList<Man>();
	}

	public boolean addPatient(Man man) {
		if(numPatient < capacity && man.getInfo().isSick()) {
			numPatient++;
			patients.add(man);
			updateViewWindow();
			return true;
		}
		return false;
	}
	
	public boolean containsPatient(Man man) {
		return patients.contains(man);
	}
	
	public void removePatient(Man man) {
		if(patients.contains(man)) {
			numPatient--;
			patients.remove(man);
			updateViewWindow();
		}
	}
	
	public boolean hasCapacity() {
		return numPatient < capacity;
	}
	
	public boolean releasePatient() {
		return true;
	}
	
	public List<List<String>> getViewData() {
		List<List<String>> list = new ArrayList<List<String>>();
		for(Man patient : patients) {
			list.add(patient.getInfo().toPatientStringList());
		}
		return list;
	}
	
	@Override
	protected UndockedWindow createUndockedWindow() {
		scrollWindow = Singleton.getInstance(UIManager.class).getGameUI().createScrollViewWindow(type);
		return scrollWindow;
	}

	@Override
	public void updateViewWindow() {
		scrollWindow.updateData(getViewData());
	}
}
