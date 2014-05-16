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
	public JobType 			job;
	public WorkableBuilding 	workingBuilding;
	public LivingHouse 		home;
	public float 			workEfficency = BASE_WORKEFFICIENCY;
	public static final float BASE_WORKEFFICIENCY = 1.0f;
	public static final float MAX_WORKEFFICENCY = 2.0f;
	
	public static final float HUNGER_POINTS_MAX = 300.0f;
	public static final float HUNGER_POINTS_FIND_FOOD = 100.0f;
	public static final float HUNGER_POINTS_MIN = 0.0f; // die!
	public static final float HUNGER_DECRE_SPEED = HUNGER_POINTS_MAX / (World.SecondPerYear * 0.25f); //per second
	public float			hungerPoints = HUNGER_POINTS_MAX;
	public boolean			isDead = false;
	
	
	private static final int MAX_AGE = 50;
	public static final int ADULT_AGE = 15;
	public static final int MIN_STUDENT_AGE=ADULT_AGE-10;

	
	private static final float HEALTH_POINTS_MAX = 100;
	public static final float HEALTH_POINTS_SICK = 40;				//住院
	private static final float HEALTH_POINTS_HEALTHY = 70;			//出院
	private static final float HEALTH_POINTS_INCREMENT = 0.005f;

	private static final float HAPPINESS_POINTS_MAX = 100;
	public static final float HAPPINESS_POINTS_DEPRESSED = 50;		//去酒吧
	private static final float HAPPINESS_POINTS_PER_WINE = 10;
	private static List<String> namePool;
	private Gender gender;
	private String name;
	private int age;
	private float healthPoints;
	private float happinessPoints;
	
	@Deprecated
	public ManInfo() {
		this((int)(Math.random() * MAX_AGE), Gender.Male);
	}

	public ManInfo(int age, Gender gender) {		
		this.age = age;
		this.gender = gender;
		name = getRandomName();
		
		healthPoints = HEALTH_POINTS_MAX;
		happinessPoints = HAPPINESS_POINTS_MAX;
	}
	
	public void setHappinessPoints(float points) {
		happinessPoints = points;
	}
	
	public void setHealthyPoints(float points) {
		healthPoints = points;
	}
	
	public boolean isDepressed() {
		return happinessPoints <= HAPPINESS_POINTS_DEPRESSED;
	}
	
	public void drinkWine() {
		happinessPoints += HAPPINESS_POINTS_PER_WINE;
		if(happinessPoints > HAPPINESS_POINTS_MAX)
			happinessPoints = HAPPINESS_POINTS_MAX;
	}
	
	//healthDeredd转化成0~5
	public float getHealthPoints() {
//		return (int) (healthDegree / 20 + 0.5f);
		return healthPoints;
	}
	
	public boolean isSick() {
		return healthPoints <= HEALTH_POINTS_SICK;
	}
	
	/**
	 * 是否足够出院
	 */
	public boolean isHealthy() {
		return healthPoints >= HEALTH_POINTS_HEALTHY;
	}
	
	public void receiveTreatment() {
		healthPoints += HEALTH_POINTS_INCREMENT;
	}
	
	//默认减1
	public void decreaseHealth() {
		healthPoints -= 1;
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
	
	public void setAge(int newAge){
		age=newAge;
	}
	
	public boolean isOldEnough(int age){
		return age>MAX_AGE;
	}
	
	public void setIsDead(boolean isdead){
		isDead=isdead;
	}
	
	public float getWorkEfficency(){
		return workEfficency;
	}
	
	
	public void growWorkEfficency(float increaseNum){
		this.workEfficency=Math.min(MAX_WORKEFFICENCY, workEfficency+increaseNum);
	}
	
	public boolean isWorkEffiencyMax(){
		return this.workEfficency==MAX_WORKEFFICENCY;
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
	
	public List<String> toResidentStringList() {
		List<String> list = new ArrayList<String>();
		list.add(getName());
		list.add(getGender().toString());
		list.add(getAge() + "");
		return list;
	}

	public static List<String> getEmptyResidentStringList() {
		List<String> list = new ArrayList<String>();
		list.add("");
		list.add("");
		list.add("");
		return list;
	}
	
	public List<String> toPatientStringList() {
		List<String> list = new ArrayList<String>();
		list.add(getName());
		list.add(getGender().toString());
		list.add(getAge() + "");
		list.add(getHealthPoints() + "");
		return list;
	}	
	
	//便于hospital的viewWindow初始化宽度
	public static List<String> getEmptyPatientStringList() {
		List<String> list = new ArrayList<String>();
		list.add("");
		list.add("");
		list.add("");
		list.add("");
		return list;		
	}

	static {
		initNamePool();
	}
}
