package com.TownSimulator.ui.building.view;

import com.TownSimulator.camera.CameraController;
import com.TownSimulator.entity.building.BuildingType;
import com.badlogic.gdx.math.Vector3;

public abstract class ListenableViewWindow extends UndockedWindow{
	
	public ListenableViewWindow(BuildingType buildingType) {
		super(buildingType);		
	}

	@Override
	protected void updatePosition()
	{
		Vector3 pos = new Vector3(buildingPosXWorld, buildingPosYWorld, 0.0f);
		CameraController.getInstance(CameraController.class).worldToScreen(pos);
		float windowX = pos.x - getWidth();
		float windowY = pos.y - getHeight() * 0.5f;
		setPosition(windowX, windowY);
	}	
	
	public abstract void setWorkerGroupListener(WorkerGroupListener workerGroupListener);
	
	public abstract void setSelectBoxListener(SelectBoxListener selectBoxListener);
	
}