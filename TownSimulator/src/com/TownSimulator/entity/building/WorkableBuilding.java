package com.TownSimulator.entity.building;

import com.TownSimulator.ai.behaviortree.BehaviorTreeNode;
import com.TownSimulator.ai.btnimpls.idle.IdleBTN;
import com.TownSimulator.entity.JobType;
import com.TownSimulator.entity.Man;
import com.TownSimulator.ui.building.view.WorkerGroupListener;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

/**
 * 可以有人工作的建筑 
 *
 */
public abstract class WorkableBuilding extends Building
	implements WorkerGroupListener
{
	protected int maxJobCnt;
	protected int openJobCnt;
	protected int curWorkerCnt;
	protected JobType jobType;
	protected Array<Man> workers;
	
	public WorkableBuilding(String textureName, BuildingType type, JobType jobType, int maxJobCnt) {
		super(textureName, type);
		this.jobType = jobType;
		this.maxJobCnt = maxJobCnt;
		this.openJobCnt = maxJobCnt;
		workers = new Array<Man>();
		listenToViewWindow();
	}
	
	public WorkableBuilding(Sprite sp, BuildingType type, JobType jobType, int maxJobCnt) {
		super(sp, type);
		this.jobType = jobType;
		this.maxJobCnt = maxJobCnt;
		this.openJobCnt = maxJobCnt;
		workers = new Array<Man>();
		listenToViewWindow();
	}
	
	private void listenToViewWindow() {
		viewWindow.setWorkerGroupListener(this);
	}
	
	private void fireWorker(int cnt)
	{
		for (int i = 0; i < cnt; i++) {
			Man worker = workers.pop();
			worker.getInfo().job = null;
			worker.getInfo().workingBuilding = null;
			worker.setBehavior(new IdleBTN(worker));
		}
		curWorkerCnt -= cnt;
	}

	public int getMaxJobCnt() {
		return maxJobCnt;
	}

	public int getOpenJobCnt() {
		return openJobCnt;
	}

	public void setOpenJobCnt(int openJobCnt) {
		if(curWorkerCnt > openJobCnt)
			fireWorker(curWorkerCnt - openJobCnt);
		
		this.openJobCnt = openJobCnt;
	}

	public int getCurWorkerCnt() {
		return curWorkerCnt;
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
		if(curWorkerCnt >= openJobCnt)
			return;
		
		workers.add(man);
		curWorkerCnt ++;
		man.getInfo().job = jobType;
		man.getInfo().workingBuilding = this;
		man.setBehavior(createBehavior(man));
	}
	
	public void removeWorker(Man man)
	{
		if( workers.removeValue(man, false) )
		{
			curWorkerCnt--;
			man.getInfo().job = null;
			man.getInfo().workingBuilding = null;
		}
	}
	
	/*
	 * 两种情况下该方法会被调用：
	 * 	建筑工人数上限改变
	 * 	工人人数上限改变
	 */
	@Override
	public final void workerLimitSelected(int limit) {
		if(state == State.UnderConstruction)
			//called by ConstructionWindow's WorkerGroup
			super.workerLimitSelected(limit);
		else if(state == State.Constructed) {
			//called by ViewWindow's WorkerGroup
			workerLimitChanged(limit);
		}
	}
	
	/*
	 * WorkerGroup变化时，会调用此方法
	 */
	protected abstract void workerLimitChanged(int limit);

}
