package com.TownSimulator.entity;

import java.util.Calendar;

import com.TownSimulator.driver.Driver;
import com.TownSimulator.driver.DriverListenerBaseImpl;
import com.TownSimulator.utility.Singleton;

public class World extends Singleton {
	
	public static final int SecondPerYear=600; 
	
	private Calendar calendar;
	private float secondPerDay;
	private float secondDuringLastDay;
	
	public enum SeasonType{
		Spring,Summer,Autumn,Winter
	}
	private int[] startSeasonMonth={2,5,8,11};
	
	private World(){
		calendar=Calendar.getInstance();
		calendar.set(2014, 3, 30);//设置初始日期
		secondPerDay=SecondPerYear/365;
		secondDuringLastDay=0f;
		
		Driver.getInstance(Driver.class).addListener(new DriverListenerBaseImpl(){

			@Override
			public void update(float deltaTime) {
				// TODO Auto-generated method stub
				secondDuringLastDay+=deltaTime;
				if(secondDuringLastDay>secondPerDay){
					calendar.add(Calendar.DATE, 1);
					secondDuringLastDay-=secondPerDay;
				}
			}
			
			
		});
	}
	
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
					season=i;
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
	

}
