package com.TownSimulator.entity.building;

import com.TownSimulator.driver.DriverListener;
import com.TownSimulator.entity.EntityInfoCollector;
import com.TownSimulator.entity.Man;

public class ApartmentHouse extends LivingHouse implements DriverListener{

	public static final  int INCREASEPERMONTH = LowCostHouse.INCREASEPERMONTH*2;				//每人每个月增加的几率
	
	private static final long serialVersionUID = 7436059925334394911L;
	private int persentage = 0;
	private float time;
	
	public ApartmentHouse() {
		super("building_apartment_house", BuildingType.APARTMENT);
	}
	
	/**
	 * 随机增加人口，每个月概率增加2*人数，成功后变为0
	 */
	public void increasePopulation(){
		if(residents.size() != 0){
			persentage += INCREASEPERMONTH*residents.size();
			int p = (int) (Math.random()*240);
			if(p <= persentage){
				Man man = new Man();
				EntityInfoCollector.getInstance(EntityInfoCollector.class).addMan(man);
				persentage = 0;
			}
		}
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	/**
	 * 每过一个月，调用一次增加人口
	 */
	@Override
	public void update(float deltaTime) {
		time += deltaTime;
		if(time >= LowCostHouse.SecondPerMonth){
			increasePopulation();
			time = 0;
		}
	}
}
