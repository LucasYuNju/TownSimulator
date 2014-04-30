package com.TownSimulator.ui;

import com.TownSimulator.camera.CameraController;
import com.TownSimulator.camera.CameraListener;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Group;

public class UndockedWindow extends Group{
	protected float buildingPosXWorld;
	protected float buildingPosYWorld;

	public UndockedWindow() {
		super();
		initCameraListener();
	}
	
	protected void initCameraListener()
	{
		CameraController.getInstance(CameraController.class).addListener(new CameraListener() {
			@Override
			public void cameraZoomed(float prevWidth, float prevHeight, float curWidth,
					float curHeight) {
				if(isVisible())
					updatePosition();
			}
			
			@Override
			public void cameraMoved(float deltaX, float deltaY) {
				if(isVisible())
					updatePosition();
			}
		});
	}
	
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if(visible)
			updatePosition();
	}

	protected void updatePosition()
	{
		Vector3 pos = new Vector3(buildingPosXWorld, buildingPosYWorld, 0.0f);
		CameraController.getInstance(CameraController.class).worldToScreen(pos);
		float windowX = pos.x - getWidth();
		float windowY = pos.y - getHeight() * 0.5f;
		setPosition(windowX, windowY);
	}

	public void setBuildingPosWorld(float x, float y)
	{
		buildingPosXWorld = x;
		buildingPosYWorld = y;
	}

}
