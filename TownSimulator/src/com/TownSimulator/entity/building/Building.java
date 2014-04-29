package com.TownSimulator.entity.building;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.TownSimulator.ai.btnimpls.construct.ConstructProject;
import com.TownSimulator.entity.Entity;
import com.TownSimulator.entity.ResourceType;
import com.TownSimulator.ui.UIManager;
import com.TownSimulator.ui.build.BuildingInfoWindow;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Building extends Entity{
	protected HashMap<ResourceType, BuildProcessResourceInfo>	mBuildProcessInfoMap;
	protected int												mNeededBuildContributes;
	protected int												mCurBuildContributes;
	protected State												mCurState;
	protected BuildingType										mType;
	private	  ConstructProject								mConstructionProject;
	
	public enum State
	{
		BUILDING_PROCESS, BUILDING_FINISHED
	}
	
	private class BuildProcessResourceInfo
	{
		public int need = 0;
		public int cur = 0;
		
		public BuildProcessResourceInfo(int need)
		{
			this.need = need;
		}
	}
	
	public Building(Sprite sp, BuildingType type) {
		super(sp);
		mType = type;
		
		mCurState = State.BUILDING_PROCESS;
		mBuildProcessInfoMap = new HashMap<ResourceType, Building.BuildProcessResourceInfo>();
	}
	
	public BuildingType getType()
	{
		return mType;
	}
	
	public void setNeededBuildResource(ResourceType type, int need)
	{
		if(mBuildProcessInfoMap.containsKey(type))
			mBuildProcessInfoMap.get(type).need = need;
		else
			mBuildProcessInfoMap.put(type, new BuildProcessResourceInfo(need));
	}
	
	public void setNeededBuildContributes(int amount)
	{
		mNeededBuildContributes = amount;
	}
	
	public int getNeededBuildContributes()
	{
		return mNeededBuildContributes;
	}
	
	public int getCurBuildContributes()
	{
		return mCurBuildContributes;
	}
	
	public Set<ResourceType> getNeededBuildResourceTypes()
	{
		return mBuildProcessInfoMap.keySet();
	}
	
	public boolean addCurBuildResource(ResourceType type, int amount)
	{
		if(mBuildProcessInfoMap.containsKey(type))
		{
			mBuildProcessInfoMap.get(type).cur += amount;
			return true;
		}
		else
			return false;
	}
	
	public int getNeededBuildResouceAmount(ResourceType type)
	{
		if( !mBuildProcessInfoMap.containsKey(type) )
			return 0;
		
		return mBuildProcessInfoMap.get(type).need;
	}
	
	public int getCurBuildResourceAmount(ResourceType type)
	{
		if( !mBuildProcessInfoMap.containsKey(type) )
			return 0;
		
		return mBuildProcessInfoMap.get(type).cur;
	}
	
	public void addCurBuildContributes(int amount)
	{
		mCurBuildContributes = Math.min(mNeededBuildContributes, mCurBuildContributes + amount);
	}
	
	public boolean isBuildResourceSufficient()
	{
		Iterator<ResourceType> itr = mBuildProcessInfoMap.keySet().iterator();
		while(itr.hasNext())
		{
			BuildProcessResourceInfo info = mBuildProcessInfoMap.get(itr.next());
			if(info.cur < info.need)
				return false;
		}
		
		return true;
	}
	
	public void setConstructionProject(ConstructProject project)
	{
		mConstructionProject = project;
	}
	
	public ConstructProject getConstructionProject()
	{
		return mConstructionProject;
	}
	
	public boolean isBuildFinished()
	{
		return mCurBuildContributes >= mNeededBuildContributes;
	}
	
	
	@Override
	public boolean detectTouchDown()
	{
		super.detectTouchDown();
		BuildingInfoWindow infoWindow = 
				UIManager.getInstance(UIManager.class).getGameUI().getBuildingInfoWindow();
		infoWindow.setPosition(100, 100);
		infoWindow.setVisible(true);
		return true;
	}
	
	public void setState(State state)
	{
		mCurState = state;
	}
	
	public State getState()
	{
		return mCurState;
	}
}
