package com.TownSimulator.ui.building;

import com.TownSimulator.ui.building.view.ViewWindow;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.Singleton;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ProcessBar extends Actor{
	public static final float PREFERED_WIDTH = ViewWindow.LABEL_WIDTH * 2;
	public static final float HEIGHT = Settings.UNIT * 0.4f;
	private static final float DRAW_HEIGHT = Settings.UNIT * 0.2f;
	private static final float MARGIN =  Settings.UNIT * 0.1f;
	private float width;
	private float process;
	private TextureRegion processBar;
	private TextureRegion blackFrame;
	
	public ProcessBar() {
		this(PREFERED_WIDTH);
	}
	
	public ProcessBar(float length) {
		this.width = length;
		setSize(length, HEIGHT);
		processBar = Singleton.getInstance(ResourceManager.class).findTextureRegion("process_bar");
		blackFrame = Singleton.getInstance(ResourceManager.class).findTextureRegion("frame_black");
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.draw(processBar, getX(), getY() + MARGIN, width * process, DRAW_HEIGHT);
		batch.draw(blackFrame, getX(), getY() + MARGIN, width , 		 DRAW_HEIGHT);
	}
	
	public void setProcess(float process) {
		this.process = process > 1 ? 1 : process;
	}
}
