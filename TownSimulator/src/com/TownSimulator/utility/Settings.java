package com.TownSimulator.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public class Settings {
	public static float UNIT = Gdx.graphics.getWidth() / 15.0f;
	
	public static int gameSpeed = 1;
	public static Color backgroundColor = new Color(Color.BLACK);
	
	public static Color gameGroundColor = GameMath.rgbaIntToColor(205, 163, 95, 255);
	/*
	 * UI Settings
	 */
	public static final float MARGIN = UNIT * 0.2f;
	public static final float LABEL_WIDTH = UNIT * 1.3f;
	public static final float LABEL_HEIGHT = UNIT * 0.4f;
	public static final float WORKER_WIDTH = UNIT * 0.6f;
	public static final float WORKER_HEIGHT = UNIT * 0.6f;
	public static final float PROCESS_BAR_PREFERED_WIDTH = LABEL_WIDTH * 2;
	public static final float PROCESS_BAR_HEIGHT = UNIT * 0.4f;
	public static final float UI_ALPHA = 0.7f;
	
	public static final float GRAVITY_X = 0.0f;
	public static final float GRAVITY_Y = UNIT * -6.0f;
}
