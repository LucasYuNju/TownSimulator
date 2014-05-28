package com.TownSimulator.entity.building;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import com.TownSimulator.ai.behaviortree.BehaviorTreeNode;
import com.TownSimulator.ai.btnimpls.idle.IdleBTN;
import com.TownSimulator.entity.JobType;
import com.TownSimulator.entity.Man;
import com.TownSimulator.ui.UIManager;
import com.TownSimulator.ui.building.view.UndockedWindow;
import com.TownSimulator.ui.building.view.WorkableViewWindow;
import com.TownSimulator.ui.building.view.WorkerGroupListener;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * 可以工作的建筑 
 */
public abstract class WorkableBuilding extends Building
	implements WorkerGroupListener
{
	private static final long serialVersionUID = 4219809748364138454L;
	private static final float WARING_ICON_SIZE = Settings.UNIT * 0.7f;
	protected int maxJobCnt;
	protected int openJobCnt;
	protected JobType jobType;
	protected List<Man> workers;
	private Sprite spWarning;
	private transient WorkableViewWindow workableWindow;
	
	public WorkableBuilding(String textureName, BuildingType type, JobType jobType) {
		super(textureName, type);
		this.jobType = jobType;
		this.maxJobCnt = getMaxJobCnt();
		this.openJobCnt = maxJobCnt;//(int)(maxJobCnt * 0.5f);
		workers = new ArrayList<Man>();
		spWarning = ResourceManager.getInstance(ResourceManager.class).createSprite("warning");
	}
	
	@Override
	public void drawSelf(SpriteBatch batch) {
		super.drawSelf(batch);
		
		if( getState() == Building.State.Constructed && !isWorking() )
		{
			float originX = mDrawAABBWorld.getCenterX();
			float originY = mDrawAABBWorld.maxY;
			spWarning.setBounds(originX - WARING_ICON_SIZE * 0.5f, originY, WARING_ICON_SIZE, WARING_ICON_SIZE);
			spWarning.draw(batch);
		}
	}

	public boolean isWorking()
	{
		return maxJobCnt == 0 || getCurWorkerCnt() > 0;
	}
	
	protected String getWarningMessage()
	{
		if(isWorking())
			return "";
		else
			return ResourceManager.stringMap.get("building_warning_noWorker");
	}

	@Override
	public void detectTapped() {
		super.detectTapped();
		workableWindow.setTips(getWarningMessage());
	}

	@Override
	final protected UndockedWindow createUndockedWindow() {
		workableWindow = createWorkableWindow();
		workableWindow.setWorkerGroupListener(this);
		return workableWindow;
	}
	
	protected WorkableViewWindow createWorkableWindow()
	{
		workableWindow = UIManager.getInstance(UIManager.class).getGameUI().createWorkableViewWindow(buildingType, getMaxJobCnt());
		return workableWindow;
	}
	
	abstract protected int getMaxJobCnt(); 

	private void fireWorker(int cnt)
	{
		for (int i = 0; i < cnt; i++) {
			Man worker = workers.remove(workers.size() - 1);
			worker.getInfo().job = JobType.NoJob;
			worker.getInfo().workingBuilding = null;
			worker.setBehavior(new IdleBTN(worker));
		}
	}

	@Override
	public void destroy() {
		super.destroy();
		fireWorker(workers.size());
	}

	public int getOpenJobCnt() {
		return openJobCnt;
	}

	public void setOpenJobCnt(int openJobCnt) {
		if(workers.size() > openJobCnt)
			fireWorker(workers.size() - openJobCnt);
		
		this.openJobCnt = openJobCnt;
	}

	public int getCurWorkerCnt() {
		return workers.size();
	}

	public JobType getJobType() {
		return jobType;
	}
	
	
	/**
	 *Override this method to set the man's behavior 
	 */
	abstract protected BehaviorTreeNode createBehavior(Man man);
	
	public void addWorker(Man man)
	{
		if(workers.size() >= openJobCnt)
			return;
		
		workers.add(man);
		man.getInfo().job = jobType;
		man.getInfo().workingBuilding = this;
		man.setBehavior(createBehavior(man));
		updateWorker(1);
	}
	
	public void removeWorker(Man man)
	{
		if( workers.remove(man))
		{
			man.getInfo().job = JobType.NoJob;
			man.getInfo().workingBuilding = null;
			updateWorker(-1);
		}
	}
	
	/*
	 * 两种情况下该方法会被调用：
	 * 	建筑工人数上限改变
	 * 	工人人数上限改变
	 */
	@Override
	public final void workerLimitSelected(int limit) {
		if(buildingState == State.UnderConstruction)
			//called by ConstructionWindow's WorkerGroup
			super.workerLimitSelected(limit);
		else if(buildingState == State.Constructed) {
			//called by ViewWindow's WorkerGroup
			workerLimitChanged(limit);
		}
	}
	
	private boolean updateWorker(int addition) {
		return workableWindow.addWorker(addition);
	}
	
	/*
	 * WorkerGroup变化时，会调用此方法
	 */
	protected void workerLimitChanged(int limit)
	{
		setOpenJobCnt(limit);
	}
	
	private void readObject(ObjectInputStream s) throws ClassNotFoundException, IOException {
		s.defaultReadObject();
		workableWindow.reLoad(openJobCnt, workers.size());
	}
	
//	@Override
//	protected void reloadViewWindow() {
//		workableWindow.reLoad(openJobCnt, workers.size());
//	}
}
