package com.TownSimulator.utility;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class GdxInputListnerEx extends InputListener{
	private float touchDownX = 0.0f;
	private float touchDownY = 0.0f;
	
	@Override
	public boolean touchDown(InputEvent event, float x, float y, int pointer,
			int button) {
		touchDownX = x;
		touchDownY = y;
		return true;
	}

	@Override
	public void touchUp(InputEvent event, float x, float y, int pointer,
			int button) {
		if(touchDownX == x && touchDownY == y)
			tapped(event, x, y, pointer, button);
	}
	
	public void tapped(InputEvent event, float x, float y, int pointer,
			int button)
	{
		
	}
	
}
