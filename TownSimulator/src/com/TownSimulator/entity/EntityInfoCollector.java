package com.TownSimulator.entity;

import com.TownSimulator.utility.Singleton;
import com.badlogic.gdx.utils.Array;

public class EntityInfoCollector extends Singleton{
	private Array<Man> mPeople;
	
	private EntityInfoCollector()
	{
		mPeople = new Array<Man>();
	}
	
	public void addMan(Man man)
	{
		mPeople.add(man);
	}
	
	public Array<Man> getAllPeople()
	{
		return mPeople;
	}
	
}
