package com.TownSimulator.io;

public interface InputMgrListener {
	public boolean touchDown(float screenX, float screenY, int pointer, int button);

	public boolean touchUp(float screenX, float screenY, int pointer, int button);
	
	public void touchDragged(float screenX, float screenY, float deltaX, float deltaY, int pointer);
	
	

}
