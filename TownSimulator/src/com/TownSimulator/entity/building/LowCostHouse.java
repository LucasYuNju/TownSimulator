package com.TownSimulator.entity.building;

import com.TownSimulator.entity.EntityInfoCollector;
import com.TownSimulator.entity.Man;


public class LowCostHouse extends LivingHouse {
	
	private int persentage = 0;
	private int increasePerMonth = 5;
	
	public LowCostHouse() {
		super("building_low_cost_house", BuildingType.LOW_COST_HOUSE);
	}
	
	
	/**
	 * 随机增加人口，每个月概率增加5%，成功后变为0
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
	
}
