package com.townSimulator.utility;

import com.badlogic.gdx.Gdx;

public class Settings {
	
	public static float UNIT = 0.0f;
	
	public static void refreshUnit()
	{
		UNIT = Gdx.graphics.getWidth() / 15.0f;
	}
}
