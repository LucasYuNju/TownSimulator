package com.TownSimulator.entity.building;

import com.TownSimulator.driver.DriverListener;
import com.TownSimulator.entity.EntityInfoCollector;
import com.TownSimulator.entity.Man;

public class ApartmentHouse extends LivingHouse implements DriverListener{

	
	private int persentage = 0;
	private int increasePerMonth = 10;
	private float time;
	
	
	public ApartmentHouse() {
		super("building_low_cost_house", BuildingType.LOW_COST_HOUSE);
	}
	
	
	/**
	 * 随机增加人口，每个月概率增加10%，成功后变为0
	 */
	public void increasePopulation(){
		if(residents.size() != 0){
			persentage += increasePerMonth;
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
		// TODO Auto-generated method stub
		
	}


	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
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
