package com.TownSimulator.io;

import com.TownSimulator.utility.SingletonPublisher;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Array;

public class InputMgr extends SingletonPublisher<InputMgrListener> implements InputProcessor{
	private float 					mPrevDraggedX = 0.0f;
	private float 					mPrevDraggedY = 0.0f;
	private Array<InputMgrListener>	mActiveListeners;
	
	private InputMgr() {
		Gdx.input.setInputProcessor(this);
		mActiveListeners = new Array<InputMgrListener>();
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		mActiveListeners.clear();
		for (int i = 0; i < mListeners.size; i++) {
			if( mListeners.get(i).touchDown(screenX, screenY, pointer, button) )
				mActiveListeners.add(mListeners.get(i));
		}
		mPrevDraggedX = screenX;
		mPrevDraggedY = screenY;
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		for (int i = 0; i < mActiveListeners.size; i++) {
			mActiveListeners.get(i).touchUp(screenX, screenY, pointer, button);
		}
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		for (int i = 0; i < mActiveListeners.size; i++) {
			mActiveListeners.get(i).touchDragged(screenX, screenY, screenX - mPrevDraggedX, screenY - mPrevDraggedY, pointer);
		}
		mPrevDraggedX = screenX;
		mPrevDraggedY = screenY;
		return true;
	}


	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
