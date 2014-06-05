package com.TownSimulator.entity;

import java.io.Serializable;
import java.util.Calendar;

import com.TownSimulator.driver.Driver;
import com.TownSimulator.driver.DriverListenerBaseImpl;
import com.TownSimulator.entity.building.School;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.Singleton;
import com.TownSimulator.utility.VoicePlayer;
import com.TownSimulator.utility.particle.ParticleControl;

public class World extends Singleton implements Serializable{
	private static final long serialVersionUID = 1L;
	public static final float SecondPerYear = 600;
	private Calendar calendar;
	private float secondPerDay;
	private float secondDuringLastDay;
	private int maxStudentAmount;
	private SeasonType curSeasonType;
	private int livedDays = 0;
//	private int currentStudentNum;
	
	private int[] startSeasonMonth={2,5,8,11};
	
	private World(){
		
	}
	
	public void init()
	{
		calendar=Calendar.getInstance();
		calendar.set(2014, 1, 29);//设置初始日期
		curSeasonType=getCurSeason();
		secondPerDay = SecondPerYear / 365.0f;
		secondDuringLastDay = 0f;
		
		maxStudentAmount=0;
		
		Driver.getInstance(Driver.class).addListener(new DriverListenerBaseImpl(){
			private static final long serialVersionUID = 422688476183877241L;

			@Override
			public void update(float deltaTime) {
				secondDuringLastDay += deltaTime;
				if(secondDuringLastDay > secondPerDay){
					calendar.add(Calendar.DATE, 1);
					secondDuringLastDay -= secondPerDay;
					livedDays ++;
//					System.out.println(getCurYear()+"/"+getCurMonth()+"/"+getCurDay());
				}
				if(curSeasonType!=getCurSeason()){
					ParticleControl.getInstance(ParticleControl.class).reset();
					curSeasonType=getCurSeason();
					setGroundColor();
					
					if(curSeasonType == SeasonType.Summer)
						VoicePlayer.getInstance(VoicePlayer.class).playMusicForDuringTime("rain.mp3", SecondPerYear / 4.0f);
				}
			}
		});
	}
	
	public int getLivedDays()
	{
		return livedDays;
	}
	
	/**
	 * 当这个世界有新学校建好，更新容纳学生信息
	 */
	public void updateSchoolInfo(){
		this.maxStudentAmount+=School.SingleSchoolStudentNum;			
	}

	public int getMaxStudentAmount() {
		return maxStudentAmount;
	}

	/**
	 * 获取年份
	 * @return
	 */
	public int getCurYear(){
		return calendar.get(Calendar.YEAR);
	}
	
	/**
	 * 获取当前月日期
	 * @return
	 */
	public int getCurDay(){
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 获取当前月份
	 * @return
	 */
	public int getCurMonth(){
		return calendar.get(Calendar.MONTH)+1;
	}
	
	/**
	 * 获取当前季节
	 * @return
	 */
	public SeasonType getCurSeason(){
		int CurMonth=getCurMonth();
		int season=1;
		for(int i=1;i<=4;i++){
			if(i!=4){
				if(startSeasonMonth[i-1]<=CurMonth&&CurMonth<startSeasonMonth[i])
				{
					season=i;
				    break;
				}
			}else{
				season=4;
			}
		}
		switch(season){
		case 1:return SeasonType.Spring;
		case 2:return SeasonType.Summer;
		case 3:return SeasonType.Autumn;
		case 4:return SeasonType.Winter;
		}
		return SeasonType.Spring;
	}
	
	public void setGroundColor(){
		switch (curSeasonType) {
		case Winter:
			Settings.backgroundColor=Settings.gameWinterGroundColor.cpy();
			break;
		case Summer:
			Settings.backgroundColor=Settings.gameSummerGroundColor.cpy();
			break;
			
		default:
			Settings.backgroundColor=Settings.gameNormalGroundColor.cpy();
			break;
		}
	}
}
