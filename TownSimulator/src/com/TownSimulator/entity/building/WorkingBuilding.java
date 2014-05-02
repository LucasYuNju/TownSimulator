package com.TownSimulator.entity.building;

import com.TownSimulator.ai.btnimpls.idle.IdleBTN;
import com.TownSimulator.entity.JobType;
import com.TownSimulator.entity.Man;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

/**
 *可以有人工作的建筑 
 *
 */
public class WorkingBuilding extends Building{
	protected int maxJobCnt;
	protected int openJobCnt;
	protected int curWorkerCnt;
	protected JobType jobType;
	protected Array<Man> workers;
	
	public WorkingBuilding(String textureName, BuildingType type, JobType jobType, int maxJobCnt) {
		super(textureName, type);
		this.jobType = jobType;
		this.maxJobCnt = maxJobCnt;
		this.openJobCnt = maxJobCnt;
		workers = new Array<Man>();
	}
	
	public WorkingBuilding(Sprite sp, BuildingType type, JobType jobType, int maxJobCnt) {
		super(sp, type);
		this.jobType = jobType;
		this.maxJobCnt = maxJobCnt;
		this.openJobCnt = maxJobCnt;
		workers = new Array<Man>();
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
	
	public void addWorker(Man man)
	{
		if(curWorkerCnt >= openJobCnt)
			return;
		
		workers.add(man);
		curWorkerCnt ++;
		man.getInfo().job = jobType;
		man.getInfo().workingBuilding = this;
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

}
