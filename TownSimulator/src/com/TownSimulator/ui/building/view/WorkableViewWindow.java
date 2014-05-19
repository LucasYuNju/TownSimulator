package com.TownSimulator.ui.building.view;

import com.TownSimulator.entity.building.BuildingType;

/**
 * 	支持WorkerGroupWindow及监听
 */
public class WorkableViewWindow extends UndockedWindow {
	protected WorkerGroup workerGroup;
	
	public WorkableViewWindow(BuildingType buildingType, int numAllowedWorker) {
		super(buildingType);
		workerGroup = new WorkerGroup(numAllowedWorker);
		workerGroup.setVisible(true);
		workerGroup.setPosition(MARGIN, MARGIN);
		addActor(workerGroup);
		addHeader();
		addCloseButton();
		
		float width = Math.max(workerGroup.getWidth(),
				headerLabel.getStyle().font.getBounds(headerLabel.getText()).width + closeButton.getWidth() + MARGIN);
		width = Math.max(width, MIN_WIDTH);
		setSize(width + MARGIN * 2, workerGroup.getHeight() + MARGIN * 2);
		updateLayout();
	}
	
	public boolean addWorker() {
		return workerGroup.addWorker();
	}
	
	public boolean addWorker(int addition) {
		return workerGroup.addWorker(addition);
	}
	
	public void setWorkerGroupListener(WorkerGroupListener workerGroupListener) {
		workerGroup.setListener(workerGroupListener);
	}
	
	public void reLoad(int openJobCnt, int numWorker) {
		workerGroup.reLoad(openJobCnt, numWorker);
	}
}
