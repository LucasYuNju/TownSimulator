package com.TownSimulator.entity.building;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.TownSimulator.ai.btnimpls.construct.ConstructionProject;
import com.TownSimulator.camera.CameraController;
import com.TownSimulator.camera.CameraListener;
import com.TownSimulator.entity.Entity;
import com.TownSimulator.entity.ResourceType;
import com.TownSimulator.ui.UIManager;
import com.TownSimulator.ui.building.construction.ConstructionResourceInfo;
import com.TownSimulator.ui.building.construction.ConstructionWindow;
import com.TownSimulator.ui.building.construction.ConstructionWindowListener;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Building extends Entity implements ConstructionWindowListener{
	protected HashMap<ResourceType, ConstructionResourceInfo>	resouceMap;
	protected int												constructionWork;
	protected int												finishedConstructionWork;
	private	  ConstructionProject								constructionProject;
	protected State												state;
	protected BuildingType										type;
	private   int 												numAllowedBuilder = 5;
	private   ConstructionWindow								constructionWindow;
	
	public enum State
	{
		BUILDING_PROCESS, BUILDING_FINISHED
	}
	
	public Building(Sprite sp, BuildingType type) {
		super(sp);
		this.type = type;
		
		state = State.BUILDING_PROCESS;
		resouceMap = new HashMap<ResourceType, ConstructionResourceInfo>();

		constructionWindow = UIManager.getInstance(UIManager.class).getGameUI().createConstructionWindow(resouceMap, numAllowedBuilder);
		constructionWindow.setListener(this);
		constructionWindow.setPosition(150, 150);
		
		
		CameraController.getInstance(CameraController.class).addListener(new CameraListener() {
			
			@Override
			public void cameraZoomed(float prevWidth, float prevHeight, float curWidth,
					float curHeight) {
				
			}
			
			@Override
			public void cameraMoved(float deltaX, float deltaY) {
				
			}
		});
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

	//this method will notify constructionWindow
	public void doConstructionWork(int amount)
	{
		finishedConstructionWork = Math.min(constructionWork, finishedConstructionWork + amount);
		if(constructionWindow != null)
			constructionWindow.setProcess(getProcess());
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
	
	public void setConstructionProject(ConstructionProject project)
	{
		constructionProject = project;
	}
	
	public ConstructionProject getConstructionProject()
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
		if(state == State.BUILDING_PROCESS) {
//			constructionWindow.setVisible(true);
		}
		//for test
		UIManager.getInstance(UIManager.class).getGameUI().testScrollPane();		
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

	//this method will notify constructionWindow
	public void addBuilder() {
		constructionWindow.addBuilder();
	}

	@Override
	public void constructionCancelled() {
		
	}

	@Override
	public void builderLimitSelected(int limit) {
		System.out.println(limit);
		constructionProject.setOpenWorkJobCnt(limit);
	}
}
