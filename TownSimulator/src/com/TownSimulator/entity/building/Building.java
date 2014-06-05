package com.TownSimulator.entity.building;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.TownSimulator.ai.btnimpls.construct.ConstructionProject;
import com.TownSimulator.entity.Entity;
import com.TownSimulator.entity.EntityInfoCollector;
import com.TownSimulator.entity.Resource;
import com.TownSimulator.entity.ResourceType;
import com.TownSimulator.ui.UIManager;
import com.TownSimulator.ui.building.construction.ConstructionProgressBar;
import com.TownSimulator.ui.building.construction.ConstructionWindow;
import com.TownSimulator.ui.building.construction.ConstructionWindowListener;
import com.TownSimulator.ui.building.view.UndockedWindow;
import com.TownSimulator.ui.building.view.UndockedWindow.UndockedWindowListener;
import com.TownSimulator.ui.building.view.WorkerGroupListener;
import com.TownSimulator.utility.VoicePlayer;
import com.TownSimulator.utility.quadtree.QuadTreeType;
import com.badlogic.gdx.graphics.Texture.TextureFilter;

public abstract class Building extends Entity 
	implements ConstructionWindowListener, WorkerGroupListener
{
	private static final long serialVersionUID = 3141326055708432L;
	protected List<Resource>					constructionResources;
	protected float								constructionWork;
	protected float								finishedConstructionWork;
	protected State								buildingState;
	protected BuildingType						buildingType;
	private	  ConstructionProject				constructionProject;
	protected transient UndockedWindow			undockedWindow;
	private   transient ConstructionWindow		constructionWindow;
	private	  transient ConstructionProgressBar	constructionProgressBar;
	private   int 								numAllowedBuilder = 3;
	private   boolean							bDestroyable = true;
	
	public enum State
	{
		PosUnconfirmed, UnderConstruction, Constructed
	}
	
	public Building(String textureName, BuildingType type)
	{
		super(textureName);
		this.buildingType = type;
		init();
		
		mSprite.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
	}
		
	private void init()
	{
		buildingState = State.PosUnconfirmed;
		constructionResources = new LinkedList<Resource>();
		constructionWindow = UIManager.getInstance(UIManager.class).getGameUI().createConstructionWindow(buildingType, constructionResources, numAllowedBuilder);
		constructionWindow.setVisible(false);
		constructionWindow.setConstructionListener(this);
		constructionWindow.setUndockedWindowListener(new UndockedWindowListener() {
			private static final long serialVersionUID = 736644604441367043L;

				@Override
				public void dynamiteButtonClicked() {
					destroy();
				}
			});
		undockedWindow = createUndockedWindow();
		if(undockedWindow != null)
		{
			undockedWindow.setVisible(false);
			undockedWindow.setUndockedWindowListener(new UndockedWindowListener() {
				private static final long serialVersionUID = 736644604441367043L;

				@Override
				public void dynamiteButtonClicked() {
					destroy();
				}
			});
		}
	}
	
	public void setDestroyable(boolean v)
	{
		this.bDestroyable = v;
		if(undockedWindow != null)
			undockedWindow.setShowDynamiteButton(v);
	}
	
	public boolean isDestroyable()
	{
		return bDestroyable;
	}
	
	@Override
	public void destroy()
	{
//		if(bDestroyable == false)
//			return;
		
		super.destroy();
		
		if(undockedWindow != null)
		{
			undockedWindow.setVisible(false);
			undockedWindow = null;
		}
		
		if(getConstructionProject() != null)
		{
			getConstructionProject().destroy();
			constructionProgressBar.realease();
			setConstructionProject(null);
			constructionWindow.setVisible(false);
		}
		
		EntityInfoCollector.getInstance(EntityInfoCollector.class).removeBuilding(this);
	}
	
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
		constructionWindow.resetResourcesViews();
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
		super.detectTapped();
		UIManager.getInstance(UIManager.class).getGameUI().hideAllWindow();
		
		if(buildingState == State.UnderConstruction) {
			if(constructionWindow != null)
			{
				constructionWindow.setVisible(true);
				
				VoicePlayer.getInstance(VoicePlayer.class).playSound("build.mp3");
			}
		}
		if(buildingState == State.Constructed) {
			if(undockedWindow != null)
			{
				undockedWindow.setBuildingPosWorld(getPositionXWorld(), getPositionYWorld());
				undockedWindow.setVisible(true);
				
				VoicePlayer.getInstance(VoicePlayer.class).playSound("click.mp3");
			}
		}
	}

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
			constructionWindow.setVisible(false);
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
	
	abstract protected UndockedWindow createUndockedWindow();

	/**
	 * 用于Building反序列化后重新初始化ViewWindow
	 */
//	abstract protected void reloadViewWindow();
	
//	@Override
//	protected void realReadObject(ObjectInputStream s) throws ClassNotFoundException, IOException {
//		super.realReadObject(s);
//		Gdx.app.log("L/S", "clazz" + getClass());
//		undockedWindow = createUndockedWindow();
//		reloadViewWindow();
//	}
//	
//	private void readObject(ObjectInputStream s) throws ClassNotFoundException, IOException {
//		Gdx.app.log("L/S", "Building readObj");
//		realReadObject(s);
//	reloadViewWindow(); 与在构造函数中调用子类方法相同，dangerous
//	}
	
	private void reload() {
		if(buildingState == State.UnderConstruction) {
			constructionProgressBar = ConstructionProgressBar.create(this);
			constructionProgressBar.setProgress(getProcess());
		}		
		constructionWindow = UIManager.getInstance(UIManager.class).getGameUI().createConstructionWindow(buildingType, constructionResources, numAllowedBuilder);
		constructionWindow.setVisible(false);
		constructionWindow.setConstructionListener(this);
		constructionWindow.refreshResouceLabel();
		constructionWindow.setProcess(getProcess());
		constructionWindow.setBuildingPosWorld(getPositionXWorld(), getPositionYWorld());
	}
	
	private void readObject(ObjectInputStream s) throws ClassNotFoundException, IOException {
		s.defaultReadObject();
		undockedWindow = createUndockedWindow();
		//风车
		if(undockedWindow != null)
			undockedWindow.setVisible(false);
		reload();
	}
}
