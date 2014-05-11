package com.TownSimulator.ui.building.view;

import com.TownSimulator.entity.building.BuildingType;

/**
 * 
 * 	支持WorkerGroupWindow及监听
 *
 */
public class WorkableViewWindow extends UndockedWindow{

	protected WorkerGroup workerGroup;
	
	public WorkableViewWindow(BuildingType buildingType, int numAllowedWorker) {
		super(buildingType);
		workerGroup = new WorkerGroup(numAllowedWorker);
		setSize(workerGroup.getWidth() + MARGIN * 2, workerGroup.getHeight() + MARGIN * 2);
		workerGroup.setVisible(true);
		workerGroup.setPosition(MARGIN, MARGIN);
		addActor(workerGroup);
		addHeader();
		addCloseButton();
	}
	
	public void addWorker() {
		workerGroup.addWorker();
	}

	
	public void setWorkerGroupListener(WorkerGroupListener workerGroupListener) {
		workerGroup.setListener(workerGroupListener);
	}

//	@Override
//	public void setSelectBoxListener(SelectBoxListener selectBoxListener) {
//		//do nothing
//	}
}
