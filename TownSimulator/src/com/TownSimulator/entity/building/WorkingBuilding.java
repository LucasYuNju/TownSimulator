package com.TownSimulator.entity.building;

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
		workers = new Array<Man>();
	}
	
	public WorkingBuilding(Sprite sp, BuildingType type, JobType jobType, int maxJobCnt) {
		super(sp, type);
		this.jobType = jobType;
		this.maxJobCnt = maxJobCnt;
		workers = new Array<Man>();
	}

	public int getMaxJobCnt() {
		return maxJobCnt;
	}

	public int getOpenJobCnt() {
		return openJobCnt;
	}

	public void setOpenJobCnt(int openJobCnt) {
		this.openJobCnt = openJobCnt;
	}

	public int getCurWorkerCnt() {
		return curWorkerCnt;
	}

	public JobType getJobType() {
		return jobType;
	}


}
