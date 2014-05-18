package com.TownSimulator.ui.building.view;

import java.io.Serializable;

import com.TownSimulator.entity.building.BuildingType;

/**
 * 	支持WorkerGroupWindow及监听
 */
public class WorkableViewWindow extends UndockedWindow implements Serializable{
	private static final long serialVersionUID = 7232098349892246130L;
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
