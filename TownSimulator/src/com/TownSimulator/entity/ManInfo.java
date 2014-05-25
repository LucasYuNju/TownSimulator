package com.TownSimulator.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.TownSimulator.ai.btnimpls.construct.ConstructionInfo;
import com.TownSimulator.entity.building.Bar;
import com.TownSimulator.entity.building.LivingHouse;
import com.TownSimulator.entity.building.School;
import com.TownSimulator.entity.building.WorkableBuilding;
import com.badlogic.gdx.Gdx;

public class ManInfo implements Serializable{
	private static final long serialVersionUID = 6185641512377240722L;
	public 	ManAnimeType 		animeType = ManAnimeType.STANDING;
	public 	boolean 			animeFlip = false;
	public 	Set<ManStateType>	manStates = new HashSet<ManStateType>();
	public 	ConstructionInfo 	constructionInfo = new ConstructionInfo();
	public 	JobType 			job;
	public 	WorkableBuilding 	workingBuilding;
	private School         		school; 
	public 	LivingHouse 		home;
	public float 				workEfficency = WORKEFFICIENCY_BASE;
	public static final float 	WORKEFFICIENCY_BASE = 1.0f;
	public static final float 	WORKEFFICENCY_MAX = 2.0f;
	
	public boolean				isDead = false;
	
	public static final float 	TEMPERATURE_POINTS_MAX = 300.0f;
	public static final float 	TEMPERATURE_POINTS_FIND_COAT = 100.0f;
	public static final float 	TEMPERATURE_POINTS_MIN = 0.0f; // die!
	public static final float 	TEMPERATURE_DECRE_SPEED = TEMPERATURE_POINTS_MAX / (World.SecondPerYear * 0.45f); //per second
	public float				temperature = TEMPERATURE_POINTS_MAX;
	
	public static final float 	HUNGER_POINTS_MAX = 300.0f;
	public static final float 	HUNGER_POINTS_FIND_FOOD = 100.0f;
	public static final float 	HUNGER_POINTS_MIN = 0.0f; // die!
	public static final float 	HUNGER_DECRE_SPEED_MIN = HUNGER_POINTS_MAX / (World.SecondPerYear * 0.5f); //per second
	public static final float 	HUNGER_DECRE_SPEED_MAX = HUNGER_POINTS_MAX / (World.SecondPerYear * 0.25f); //per second
	public float				hungerPoints = HUNGER_POINTS_MAX;
	
	public static final int 	AGE_MAX = 50;
	public static final int 	AGE_ADULT = 10;
	public static final int 	AGE_MIN_STUDENT = AGE_ADULT - 10;
	private int 				age;

	private static final float 	HEALTH_POINTS_MAX = 100;
	public static final float 	HEALTH_POINTS_SICK = 40;				//住院
	private static final float 	HEALTH_POINTS_HEALTHY = 70;			//出院
	private static final float 	HEALTH_POINTS_INCREMENT_PER_SECOND = 30 * 12 / World.SecondPerYear;
	private float 				healthPoints;

	private static final float 	HAPPINESS_POINTS_MAX = 100;
	public static final float 	HAPPINESS_POINTS_DEPRESSED = 40;		//去酒吧
	private static final float 	HAPPINESS_POINTS_PER_WINE = Bar.HAPPINESS_POINTS_PER_WINE;
	private float 				happinessPoints;
	
	private static List<String> namePool;
	private Gender gender;
	private String name;
	
	public ManInfo() {		
		name = getRandomName();
		school=null;
		
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
	
	public void receiveTreatment(float deltaTime) {
		healthPoints += HEALTH_POINTS_INCREMENT_PER_SECOND * deltaTime;
	}
	
	//默认减1
	public void decreaseHealth() {
		healthPoints -= 1;
	}

	public boolean isAdult() {
		return age >= AGE_ADULT;
	}
	
	public void setGender(Gender gender)
	{
		this.gender = gender;
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
	
	public boolean isOldEnough(){
		return age > AGE_MAX;
	}
	
	public void setIsDead(boolean isdead){
		isDead=isdead;
	}
	
	public float getWorkEfficency(){
		return workEfficency;
	}
	
	public void growWorkEfficency(float increaseNum){
		this.workEfficency=Math.min(WORKEFFICENCY_MAX, workEfficency+increaseNum);
	}
	
	public boolean isWorkEffiencyMax(){
		return this.workEfficency==WORKEFFICENCY_MAX;
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
		list.add((int)getHealthPoints() + "");
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

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public void drinkWine() {
		happinessPoints += HAPPINESS_POINTS_PER_WINE;
		if(happinessPoints > HAPPINESS_POINTS_MAX)
			happinessPoints = HAPPINESS_POINTS_MAX;
		Gdx.app.log("happiness", "drink wine");
	}
	
	private static final float HP_DECRE_PER_WORKLESS_SECOND = 75 / World.SecondPerYear;
	/**
	 * 调整幸福度的全部方法
	 */
	public void hpHomeless(float deltaTime) {
		happinessPoints -= HP_DECRE_PER_WORKLESS_SECOND * deltaTime;
		if (happinessPoints < 0) {
			happinessPoints = 0;
		}
//		Gdx.app.log("happiness info", "hp:" + happinessPoints);
	}

	public void hpWorkless(float deltaTime) {
		hpHomeless(deltaTime);
	}

	public void hpFindSomeHouse() {
		happinessPoints += HAPPINESS_POINTS_PER_WINE / 2;
		if(happinessPoints > HAPPINESS_POINTS_MAX)
			happinessPoints = HAPPINESS_POINTS_MAX;		
	}
	
	public void hpResideInLowCostHouse(float deltaTime) {
		hpHomeless(deltaTime/3);
	}
	
	public void hpResideInApartment(float deltaTime) {
		happinessPoints += HP_DECRE_PER_WORKLESS_SECOND * deltaTime / 3;
		if (happinessPoints > HAPPINESS_POINTS_MAX) {
			happinessPoints = HAPPINESS_POINTS_MAX;
		}
	}
	
	/**
	 * 每个月一次抽奖，生病的概率为1/24 
	 */
	private float healthLotteryTime = 0;
	public void hpDrawHealthLottery(float deltaTime) {
		healthLotteryTime += deltaTime;
		if(healthLotteryTime > World.SecondPerYear / 24) {
			healthLotteryTime = 0;
			if(Math.random() < 1/24f && isHealthy()) {
				healthPoints -= (HEALTH_POINTS_MAX - HEALTH_POINTS_SICK)/2;
				if(healthPoints < 0)
					healthPoints = 0;
				Gdx.app.log("healthInfo", "got lottery! health points:" + healthPoints + " isHealthy:" + isHealthy());
			}
		}
	}
	
	static {
		initNamePool();
	}
}
