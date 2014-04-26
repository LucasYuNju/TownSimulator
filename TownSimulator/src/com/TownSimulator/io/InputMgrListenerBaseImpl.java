package com.TownSimulator.io;

public class InputMgrListenerBaseImpl implements InputMgrListener{

	@Override
	public boolean touchDown(float screenX, float screenY, int pointer,
			int button) {
		return false;
	}

	@Override
	public boolean touchUp(float screenX, float screenY, int pointer, int button) {
		return false;
	}

	@Override
	public void touchDragged(float screenX, float screenY, float deltaX, float deltaY, int pointer) {
	}

}
