package com.TownSimulator.entity;

import java.util.ArrayList;
import java.util.List;

import com.TownSimulator.ai.btnimpls.construct.ConstructionInfo;
import com.TownSimulator.entity.building.LivingHouse;
import com.TownSimulator.entity.building.WorkableBuilding;

public class ManInfo {
	public ManAnimeType 	animeType = ManAnimeType.STANDING;
	public boolean 			animeFlip = false;
	public ConstructionInfo constructionInfo = new ConstructionInfo();
	public float 			workEfficency = 1.0f;
	public JobType 			job;
	public WorkableBuilding 	workingBuilding;
	public LivingHouse 		home;
	public static final float HUNGER_POINTS_MAX = 300.0f;
	public static final float HUNGER_POINTS_FIND_FOOD = 100.0f;
	public static final float HUNGER_POINTS_MIN = 0.0f; // die!
	public static final float HUNGER_DECRE_SPEED = HUNGER_POINTS_MAX / (World.SecondPerYear * 0.3f); //per second
	public float			hungerPoints = HUNGER_POINTS_MAX;
	public boolean			isDead = false;
	
	private static final float MAX_AGE = 100;
	private static final float ADULT_AGE = 10;
	private static final float MAX_HEALTH_DEGREE = 100;
	private static final float SICK_HEALTH_DEGREE = 40;		//住院
	private static final float HEALTHY_HEALTH_DEGREE = 70;	//出院
	private static final float TREAMENT = 0.001f;
	private static List<String> namePool;
	private Gender gender;
	private String name;
	private int age;
	private float healthDegree;
	
	@Deprecated
	public ManInfo() {
		this((int)(Math.random() * MAX_AGE), Gender.Male);
	}

	public ManInfo(int age, Gender gender) {		
		this.age = age;
		this.gender = gender;
		name = getRandomName();
		healthDegree = 10f;
	}
	
	//healthDeredd转化成0~5
	public float getHealthDegree() {
//		return (int) (healthDegree / 20 + 0.5f);
		return healthDegree;
	}
	
	public boolean isSick() {
		return healthDegree <= SICK_HEALTH_DEGREE;
	}
	
	/**
	 * 是否足够出院
	 */
	public boolean isHealthy() {
		return healthDegree >= HEALTHY_HEALTH_DEGREE;
	}
	
	public void addHealthDegree() {
		healthDegree +=TREAMENT;
	}
	
	//默认减1
	public void decreaseHealth() {
		healthDegree -= 1;
	}

	public boolean isAdult() {
		return age >= ADULT_AGE;
	}
	
	public Gender getGender() {
		return gender;
	}
	
	public int getAge() {
		return age;
	}
	
	public String getName() {
		return name;
	}
	
	public static String getRandomName() {
		int randomIndex = (int)(Math.random() * namePool.size());
		return namePool.get(randomIndex);
	}
	
	private static void initNamePool() {
		namePool = new ArrayList<String>();
		namePool.add("ANtonia");
		namePool.add("Arden");
		namePool.add("Barney");
		namePool.add("Brooks");
		namePool.add("Clayton");
		namePool.add("Damon");
		namePool.add("Eric");
		namePool.add("Franklyn");
		namePool.add("Glenn");
		namePool.add("Hank");
		namePool.add("Issac");
		namePool.add("Jefferey");
		namePool.add("Karl");
		namePool.add("Leo");
		namePool.add("Marvin");
		namePool.add("Neil");
		namePool.add("Oliver");
		namePool.add("Phlip");
		namePool.add("Ray");
		namePool.add("Sonny");
		namePool.add("Tad");
		namePool.add("William");
	}
	
	public enum Gender {
		Male("male"), 
		Female("female");
		private String str;
		private Gender(String str) {
			this.str = str;
		}
		@Override
		public String toString() {
			return str;
		}
	}
	
	public List<String> toStringList() {
		List<String> list = new ArrayList<String>();
		list.add(getName());
		list.add(getGender().toString());
		list.add(getAge() + "");
		return list;
	}
	
	public List<String> toPatientStringList() {
		List<String> list = new ArrayList<String>();
		list.add(getName());
		list.add(getGender().toString());
		list.add(getAge() + "");
		list.add(getHealthDegree() + "");
		return list;
	}	

	static {
		initNamePool();
	}
}
