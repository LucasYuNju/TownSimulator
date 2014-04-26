package com.TownSimulator.camera;

public interface CameraListener
{
	public void cameraMoved(float deltaX, float deltaY);
	public void cameraZoomed(float prevWidth, float prevHeight, float curWidth, float curHeight);
}