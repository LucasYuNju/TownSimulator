package com.TownSimulator.entity.building;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.TownSimulator.ai.btnimpls.construct.ConstructionProject;
import com.TownSimulator.entity.Entity;
import com.TownSimulator.entity.Resource;
import com.TownSimulator.entity.ResourceType;
import com.TownSimulator.ui.UIManager;
import com.TownSimulator.ui.building.construction.ConstructionWindow;
import com.TownSimulator.ui.building.construction.ConstructionWindowListener;
import com.TownSimulator.ui.building.view.ListenableViewWindow;
import com.TownSimulator.ui.building.view.ScrollViewWindow;
import com.TownSimulator.ui.building.view.WorkerGroupListener;
import com.TownSimulator.utility.Singleton;
import com.badlogic.gdx.graphics.g2d.Sprite;


public abstract class Building extends Entity 
	implements ConstructionWindowListener, WorkerGroupListener{
	protected List<Resource>			constructionResources;
	protected int						constructionWork;
	protected int						finishedConstructionWork;
	private	  ConstructionProject		constructionProject;
	protected State						state;
	protected BuildingType				type;
	private   int 						numAllowedBuilder = 3;
	private   ConstructionWindow		constructionWindow;
	protected ListenableViewWindow				viewWindow;
	
	public enum State
	{
		PosUnconfirmed, UnderConstruction, Constructed
	}
	
	public Building(String textureName, BuildingType type)
	{
		super(textureName);
		this.type = type;
		init();
	}
	
	public Building(Sprite sp, BuildingType type) {
		super(sp);
		this.type = type;
		init();
	}
	
	private void init()
	{
		state = State.PosUnconfirmed;
		constructionResources = new LinkedList<Resource>();
		constructionWindow = UIManager.getInstance(UIManager.class).getGameUI().createConstructionWindow(type, constructionResources, numAllowedBuilder);
		constructionWindow.setVisible(false);
		viewWindow = Singleton.getInstance(UIManager.class).getGameUI().createViewWindow(type);
		viewWindow.setVisible(false);
		if(viewWindow == null) {
			throw new NullPointerException("failed to create view window");
		}
		constructionWindow.setConstructionListener(this);
	}
	
	@Override
	public void setPositionWorld(float x, float y) {
		super.setPositionWorld(x, y);
		constructionWindow.setBuildingPosWorld(getPositionXWorld(), getPositionYWorld());
		viewWindow.setBuildingPosWorld(getPositionXWorld(), getPositionXWorld());
	}

	public int getMaxAllowdBuilderCnt()
	{
		return numAllowedBuilder;
	}
	
	public BuildingType getType()
	{
		return type;
	}
	
	public void setNeededConstructionResource(ResourceType type, int need)
	{
		if(constructionResources.contains(new Resource(type))) 
			constructionResources.get(constructionResources.indexOf(new Resource(type))).setNeededAmount(need);
		else
			constructionResources.add(new Resource(type, 0, need));
		constructionWindow.refreshResouceLabel();
	}	
	
	public Set<ResourceType> getConstructionResourceTypes()
	{
		Set<ResourceType> types = new HashSet<ResourceType>();
		for(Resource res : constructionResources) 
			types.add(res.getType());
		return types;
	}
	
	public boolean addConstructionResource(ResourceType type, int amount)
	{
		if(constructionResources.contains(new Resource(type))) {
			constructionResources.get(constructionResources.indexOf(new Resource(type))).addAmount(amount);
			constructionWindow.refreshResouceLabel();
			return true;
		}
		return false;
	}
	
	public int getNeededConstructionResouceAmount(ResourceType type)
	{
		if(constructionResources.contains(new Resource(type)))
			return constructionResources.get(constructionResources.indexOf(new Resource(type))).getNeededAmount();
		return -1;
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
		constructionWindow.setProcess(getProcess());
	}
	
	public boolean isConstructionResourceSufficient()
	{
		for(Resource res : constructionResources) {
			if(!res.isSufficient())
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
	public boolean detectTouchDown() {
		return super.detectTouchDown() || state == State.UnderConstruction || state == State.Constructed;
	}

	@Override
	public void detectTouchUp()
	{
		super.detectTouchUp();
		UIManager.getInstance(UIManager.class).getGameUI().hideAllWindow();
		if(state == State.UnderConstruction) {
			constructionWindow.setVisible(true);
		}
		if(state == State.Constructed) {
			viewWindow.setBuildingPosWorld(getPositionXWorld(), getPositionYWorld());
			viewWindow.setVisible(true);
		}
	}
	
	//使用ViewWindow的子类需要override此方法
	public List<List<String>> getViewData() {
		return Collections.emptyList();
	}
	
	/*
	 * 将数据更新到viewWindow
	 */
	protected void updataViewWindow() {
		if(viewWindow instanceof ScrollViewWindow) {
			ScrollViewWindow scrollViewWindow = (ScrollViewWindow) viewWindow;
			scrollViewWindow.updateData(getViewData());
		}
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
	public void workerLimitSelected(int limit) {
		constructionProject.setOpenWorkJobCnt(limit);
	}
}
