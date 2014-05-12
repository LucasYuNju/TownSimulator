package com.TownSimulator.entity.building;

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
import com.TownSimulator.ui.building.view.UndockedWindow;
import com.TownSimulator.ui.building.view.WorkerGroupListener;
import com.TownSimulator.utility.quadtree.QuadTreeType;
import com.badlogic.gdx.graphics.g2d.Sprite;


public abstract class Building extends Entity 
	implements ConstructionWindowListener, WorkerGroupListener{
	
	protected List<Resource>			constructionResources;
	protected float						constructionWork;
	protected float						finishedConstructionWork;
	protected State						buildingState;
	protected BuildingType				buildingType;
	protected UndockedWindow			undockedWindow;
	private	  ConstructionProject		constructionProject;
	private   ConstructionWindow		constructionWindow;
	private	  ConstructionProgressBar	constructionProgressBar;
	private   int 						numAllowedBuilder = 3;
	
	public enum State
	{
		PosUnconfirmed, UnderConstruction, Constructed
	}
	
	public Building(String textureName, BuildingType type)
	{
		super(textureName);
		this.buildingType = type;
		init();
	}
	
	public Building(Sprite sp, BuildingType type) {
		super(sp);
		this.buildingType = type;
		init();
	}
	
	private void init()
	{
		buildingState = State.PosUnconfirmed;
		constructionResources = new LinkedList<Resource>();
		constructionWindow = UIManager.getInstance(UIManager.class).getGameUI().createConstructionWindow(buildingType, constructionResources, numAllowedBuilder);
		constructionWindow.setVisible(false);
		constructionWindow.setConstructionListener(this);
		undockedWindow = createUndockedWindow();//Singleton.getInstance(UIManager.class).getGameUI().createViewWindow(type);
		if(undockedWindow != null)
			undockedWindow.setVisible(false);
//		if(viewWindow == null) {
//			throw new NullPointerException("failed to create view window");
//		}
	}
	
	abstract protected UndockedWindow createUndockedWindow();
	
	@Override
	public void setPositionWorld(float x, float y) {
		super.setPositionWorld(x, y);
		if(constructionWindow != null)
			constructionWindow.setBuildingPosWorld(getPositionXWorld(), getPositionYWorld());
		if(undockedWindow != null)
			undockedWindow.setBuildingPosWorld(getPositionXWorld(), getPositionXWorld());
	}

	public int getMaxAllowdBuilderCnt()
	{
		return numAllowedBuilder;
	}
	
	public BuildingType getType()
	{
		return buildingType;
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
	
	public float getUnfinishedConstructionWork()
	{
		return constructionWork - finishedConstructionWork;
	}

	public float getFinishedConstructionWork() 
	{
		return finishedConstructionWork;
	}

	//this method will notify constructionWindow
	public void doConstructionWork(float f)
	{
		finishedConstructionWork = Math.min(constructionWork, finishedConstructionWork + f);
		constructionWindow.setProcess(getProcess());
		constructionProgressBar.setProgress(getProcess());
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
	
	public boolean isAround(float x, float y)
	{
		float buildingCenterX = getAABBWorld(QuadTreeType.COLLISION).getCenterX();
		float buildingCenterY = getAABBWorld(QuadTreeType.COLLISION).getCenterY();
		double dist = Math.pow(x - buildingCenterX, 2)
				    + Math.pow(y - buildingCenterY, 2);
		double radius = Math.pow(getAABBWorld(QuadTreeType.COLLISION).getWidth() * 0.5f, 2)
			    	  + Math.pow(getAABBWorld(QuadTreeType.COLLISION).getHeight() * 0.5f, 2);
		return dist <= radius;
	}
	
	@Override
	public boolean detectTouchDown() {
		return super.detectTouchDown() || buildingState == State.UnderConstruction || buildingState == State.Constructed;
	}

	@Override
	public void detectTapped()
	{
		super.detectTouchUp();
		UIManager.getInstance(UIManager.class).getGameUI().hideAllWindow();
		
		if(buildingState == State.UnderConstruction) {
			if(constructionWindow != null)
				constructionWindow.setVisible(true);
		}
		if(buildingState == State.Constructed) {
			if(undockedWindow != null)
			{
				undockedWindow.setBuildingPosWorld(getPositionXWorld(), getPositionYWorld());
				undockedWindow.setVisible(true);
			}
		}
	}
	
	/**
	 * 
	 */
	abstract protected void updateViewWindow();
	
	public void setState(State state)
	{
		this.buildingState = state;
		
		if(state == State.UnderConstruction)
			constructionProgressBar = ConstructionProgressBar.create(this);
		else if(state == State.Constructed)
		{
			if(constructionProgressBar != null)
			{
				constructionProgressBar.realease();
				constructionProgressBar = null;
			}
		}
	}
	
	public State getState()
	{
		return buildingState;
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
