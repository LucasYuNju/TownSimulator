package com.TownSimulator.entity.building;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.TownSimulator.ai.btnimpls.construct.ConstructProject;
import com.TownSimulator.entity.Entity;
import com.TownSimulator.entity.ResourceType;
import com.TownSimulator.ui.UIManager;
import com.TownSimulator.ui.building.construction.ConstructionResourceInfo;
import com.TownSimulator.ui.building.construction.ConstructionWindow;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Building extends Entity{
	protected HashMap<ResourceType, ConstructionResourceInfo>	resouceMap;
	protected int												constructionWork;
	protected int												finishedConstructionWork;
	private	  ConstructProject									constructionProject;
	protected State												state;
	protected BuildingType										type;

	public enum State
	{
		BUILDING_PROCESS, BUILDING_FINISHED
	}
	
	public Building(Sprite sp, BuildingType type) {
		super(sp);
		this.type = type;
		
		state = State.BUILDING_PROCESS;
		resouceMap = new HashMap<ResourceType, ConstructionResourceInfo>();
	}
	
	public BuildingType getType()
	{
		return type;
	}
	
	public void setNeededConstructionResource(ResourceType type, int need)
	{
		if(resouceMap.containsKey(type))
			resouceMap.get(type).need = need;
		else
			resouceMap.put(type, new ConstructionResourceInfo(need));
	}	
	
	public Set<ResourceType> getNeededConstructionResourceTypes()
	{
		return resouceMap.keySet();
	}
	
	public boolean addConstructionResource(ResourceType type, int amount)
	{
		if(resouceMap.containsKey(type))
		{
			resouceMap.get(type).cur += amount;
			return true;
		}
		else
			return false;
	}
	
	public int getNeededConstructionResouceAmount(ResourceType type)
	{
		if( !resouceMap.containsKey(type) )
			return 0;
		
		return resouceMap.get(type).need;
	}
	
	public void setNeededConstructionWork(int amount)
	{
		constructionWork = amount;
	}
	
	public int getUnfinishedConstructionWork()
	{
		return constructionWork - finishedConstructionWork;
	}

	public int getFinishedConstructionWork() 
	{
		return finishedConstructionWork;
	}

	public void doConstructionWork(int amount)
	{
		finishedConstructionWork = Math.min(constructionWork, finishedConstructionWork + amount);
	}
	
	public boolean isConstructionResourceSufficient()
	{
		Iterator<ResourceType> itr = resouceMap.keySet().iterator();
		while(itr.hasNext())
		{
			ConstructionResourceInfo info = resouceMap.get(itr.next());
			if(info.cur < info.need)
				return false;
		}
		return true;
	}
	
	public void setConstructionProject(ConstructProject project)
	{
		constructionProject = project;
	}
	
	public ConstructProject getConstructionProject()
	{
		return constructionProject;
	}
	
	public boolean isConstructionFinished()
	{
		return finishedConstructionWork >= constructionWork;
	}
	
	public float getProcess() {
		return finishedConstructionWork / (float)constructionWork;
	}
	
	@Override
	public boolean detectTouchDown()
	{
		super.detectTouchDown();
		ConstructionWindow infoWindow = 
				UIManager.getInstance(UIManager.class).getGameUI().createConstructionWindow();
		infoWindow.setPosition(150, 150);
		infoWindow.setVisible(true);
		
		UIManager.getInstance(UIManager.class).getGameUI().createTestWindow();;
		
		return true;
	}
	
	public void setState(State state)
	{
		this.state = state;
	}
	
	public State getState()
	{
		return state;
	}
}
