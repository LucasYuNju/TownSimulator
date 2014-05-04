package com.TownSimulator.ui.building.view;

import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.Singleton;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ProcessBar extends Actor{
	public static final float PREFERED_WIDTH = Settings.PROCESS_BAR_PREFERED_WIDTH;
	public static final float HEIGHT = Settings.PROCESS_BAR_HEIGHT;
	private static final float DRAW_HEIGHT = HEIGHT * 0.7f;
	private float width;
	private float process;
	private TextureRegion processBar;
	private TextureRegion blackFrame;
	
	public ProcessBar() {
		this(PREFERED_WIDTH);
	}
	
	public ProcessBar(float width) {
		this.width = width;
		setSize(width, HEIGHT);
		processBar = Singleton.getInstance(ResourceManager.class).findTextureRegion("process_bar");
		blackFrame = Singleton.getInstance(ResourceManager.class).findTextureRegion("frame_black");
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.draw(processBar, getX(), getY() + (HEIGHT - DRAW_HEIGHT) / 2, width * process, DRAW_HEIGHT);
		batch.draw(blackFrame, getX(), getY() + (HEIGHT - DRAW_HEIGHT) / 2, width , 		 DRAW_HEIGHT);
	}
	
	public void setProcess(float process) {
		this.process = process > 1 ? 1 : process;
	}
}
