package com.TownSimulator.camera;

import java.io.Serializable;

public interface CameraListener extends Serializable
{
	public void cameraMoved(float deltaX, float deltaY);
	public void cameraZoomed(float prevWidth, float prevHeight, float curWidth, float curHeight);
}