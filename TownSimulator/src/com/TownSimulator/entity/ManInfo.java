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
	public static final float MAX_WORKEFFICENCY=2.0f;
	
	public static final float HUNGER_POINTS_MAX = 300.0f;
	public static final float HUNGER_POINTS_FIND_FOOD = 100.0f;
	public static final float HUNGER_POINTS_MIN = 0.0f; // die!
	public static final float HUNGER_DECRE_SPEED = HUNGER_POINTS_MAX / (World.SecondPerYear * 0.3f); //per second
	public float			hungerPoints = HUNGER_POINTS_MAX;
	public boolean			isDead = false;
	
	private static final int MAX_AGE = 100;
	private static final int ADULT_AGE = 18;

	public static final int MAX_STUDENT_AGE=ADULT_AGE;
	public static final int MIN_STUDENT_AGE=MAX_STUDENT_AGE-10;
	
	private static List<String> namePool;
	private Gender gender;
	private String name;
	private int age;

	@Deprecated
	public ManInfo() {
		this((int)(Math.random() * MAX_AGE), Gender.Male);
	}

	public ManInfo(int age, Gender gender) {		
		this.age = age;
		this.gender = gender;
		name = getRandomName();
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
	
	public List<String> toStringList() {
		List<String> list = new ArrayList<String>();
		list.add(getName());
		list.add(getGender().toString());
		list.add(getAge() + "");
		return list;
	}

	static {
		initNamePool();
	}
}
