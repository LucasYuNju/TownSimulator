package com.TownSimulator.entity.building;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import com.TownSimulator.driver.Driver;
import com.TownSimulator.driver.DriverListener;
import com.TownSimulator.driver.DriverListenerBaseImpl;
import com.TownSimulator.entity.EntityInfoCollector;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.ManInfo;
import com.TownSimulator.entity.ManInfo.Gender;
import com.TownSimulator.entity.World;
import com.TownSimulator.render.Renderer;
import com.TownSimulator.ui.UIManager;
import com.TownSimulator.ui.building.view.ScrollViewWindow;
import com.TownSimulator.ui.building.view.UndockedWindow;

public class LivingHouse extends Building {
	private static final long serialVersionUID = 6577679479943487313L;
	private static final float MAN_INCREASE_PROBABILITY = 0.02f;
	protected List<ManInfo> residents;
	protected int capacity;
	protected transient ScrollViewWindow scrollWindow;
	private float time;
	private DriverListener driverListener;
	
	public LivingHouse(String textureName, BuildingType type) {
		super(textureName, type);
		residents = new ArrayList<ManInfo>();
		capacity = 5;
		
		driverListener = new DriverListenerBaseImpl()
		{
			private static final long serialVersionUID = 2022240962902543180L;

			/**
			 * 每过一个月，调用一次增加人口
			 */
			@Override
			public void update(float deltaTime) {
				time += deltaTime;
				if(time >= World.SecondPerYear / 12){
					doLottery();
					time = 0;
				}
				
				updateViewWindow();
			}
		};
	}
	
	private void doLottery()
	{
		if(Math.random() <= MAN_INCREASE_PROBABILITY * residents.size())
			newMan();
	}
	
	private void newMan()
	{
		Man man = new Man();
		man.getInfo().setGender(Math.random() < 0.5 ? Gender.Male : Gender.Female);;
		man.setPositionWorld(mPosXWorld, mPosYWorld);
		Renderer.getInstance(Renderer.class).attachDrawScissor(man);
		EntityInfoCollector.getInstance(EntityInfoCollector.class).addMan(man);
	}
	
	/**
	 * 随机增加人口，每个月概率增加2*人数，成功后变为0
	 */
//	public void increasePopulation(){
//		if(residents.size() != 0){
//			persentage += INCREASEPERMONTH * residents.size();
//			int p = (int) (Math.random()*240);
//			if(p <= persentage){
//				Man man = new Man();
//				man.getInfo().setGender(Math.random() < 0.5 ? Gender.Male : Gender.Female);;
//				man.setPositionWorld(mPosXWorld, mPosYWorld);
//				Renderer.getInstance(Renderer.class).attachDrawScissor(man);
//				EntityInfoCollector.getInstance(EntityInfoCollector.class).addMan(man);
//				persentage = 0;
//			}
//		}
//	}
	
	
	@Override
	public void setState(State state) {
		super.setState(state);
		if(state == Building.State.Constructed)
		{
			Driver.getInstance(Driver.class).addListener(driverListener);
			updateViewWindow();
		}
	}

	public boolean addResident(ManInfo newResident) {
		if(capacity > residents.size()) {
			residents.add(newResident);
			newResident.home = this;
			updateViewWindow();
			return true;
		}
		return false;
	}
	
	@Override
	public void destroy() {
		super.destroy();
		
		ArrayList<ManInfo> infos = new ArrayList<ManInfo>(residents);
		for (ManInfo m : infos) {
			removeResident(m);
		}
		
		Driver.getInstance(Driver.class).removeListener(driverListener);
	}

	public void removeResident(ManInfo resident)
	{
		if(residents.remove(resident)) {
			resident.home = null;
			updateViewWindow();
		}
	}
	
	public boolean hasAvailableRoom()
	{
		return residents.size() < capacity;
	}
	
	
	public List<List<String>> getViewData() {
		List<List<String>> list = new ArrayList<List<String>>();
		for(ManInfo resident : residents) {
			list.add(resident.toResidentStringList());
		}
		if (list.isEmpty()) {
			list.add(ManInfo.getEmptyResidentStringList());
		}
		return list;
	}
	
	/*
	 * 将数据更新到viewWindow
	 */
	protected void updateViewWindow() {
		scrollWindow.updateData(getViewData());
	}

	@Override
	protected UndockedWindow createUndockedWindow() {
		scrollWindow = UIManager.getInstance(UIManager.class).getGameUI().createScrollViewWindow(buildingType);
		return scrollWindow;
	}
	
	private void readObject(ObjectInputStream s) throws ClassNotFoundException, IOException {
		s.defaultReadObject();
		updateViewWindow();
		setState(buildingState);
	}

//	@Override
//	protected void reloadViewWindow() {
//		if(residents == null)
//			residents = new ArrayList<ManInfo>();
//		updateViewWindow();
//	}
}
