package com.TownSimulator.utility;

import com.badlogic.gdx.Gdx;

public class Settings {
	public static float UNIT = Gdx.graphics.getWidth() / 15.0f;
	
	/*
	 * UI Settings
	 */
	public static final float MARGIN = UNIT * 0.4f;
	public static final float LABEL_WIDTH = UNIT * 1.1f;
	public static final float LABEL_HEIGHT = UNIT * 0.4f;
	public static final float WORKER_WIDTH = UNIT * 0.6f;
	public static final float WORKER_HEIGHT = UNIT * 0.6f;
	public static final float PROCESS_BAR_PREFERED_WIDTH = LABEL_WIDTH * 2;
	public static final float PROCESS_BAR_HEIGHT = UNIT * 0.4f;
}
