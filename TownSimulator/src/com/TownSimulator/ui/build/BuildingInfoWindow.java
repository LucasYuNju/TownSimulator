package com.TownSimulator.ui.build;

import java.util.LinkedList;

import com.TownSimulator.ui.base.FlipButton;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.Singleton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

/*
 * 					  margin 		  closeButton
 *   		| header|				| 
 * margin	| icon	| icon	| icon	| margin
 *  	  	| label	| label	| label	|
 *  		| 		workers			|
 * 			| 		process bar		|
 * 					  margin
 */
public class BuildingInfoWindow extends Group{
	//LABEL_WIDTH is also ICON_WIDTH
	private static final float LABEL_WIDTH = Settings.UNIT * 1f;
	private static final float LABEL_HEIGHT = Settings.UNIT * 0.5f;
	private static final float MARGIN = Settings.UNIT * 0.25f; 
	private static final float PROCESS_BAR_HEIGHT = Settings.UNIT * 0.2f;
	private static final int NUM_PAIR = 3;
	private static final int MAX_WORKER = 5;
	
	private TextureRegion background;
	//window去掉margin后的宽度
	private float windowWidth;
	//window去掉margin后的高度
	private float windowHeight;	
	private BuildingInfoWindowListener listener;
	
	//进度条
	private float process;
	private TextureRegion processBar;
	private TextureRegion blackFrame;
	
	//分派建筑工
	private TextureRegion grayWorker;
	private TextureRegion worker;
	private int upperLimit;
	private int seletedLimit;
	private float workerWidth;
	
	public BuildingInfoWindow() {
		background = Singleton.getInstance(ResourceManager.class).findTextureRegion("background");
		processBar = Singleton.getInstance(ResourceManager.class).findTextureRegion("process_bar");
		blackFrame = Singleton.getInstance(ResourceManager.class).findTextureRegion("frame_black");
		worker = Singleton.getInstance(ResourceManager.class).findTextureRegion("head");
		grayWorker = Singleton.getInstance(ResourceManager.class).findTextureRegion("head_gray");
		setBuilderUpperLimit(5);
		
		process = 0.5f;
		
		windowWidth = LABEL_WIDTH * NUM_PAIR;
		windowHeight = LABEL_WIDTH * 2 + LABEL_HEIGHT * 2 + PROCESS_BAR_HEIGHT;
		workerWidth  = windowWidth / MAX_WORKER;
		setSize(windowWidth + MARGIN * 2, windowHeight + MARGIN * 2);
		setPosition( (Gdx.graphics.getWidth() - getWidth())/2, 
				(Gdx.graphics.getHeight() - getHeight())/2 );
		addPairs();
		addCloseButton();
		addHeader();
		addWorkers();
	}
	
	private void addWorkers() {
		final java.util.List<FlipButton> btns = new LinkedList<FlipButton>();
		for(int i=0; i<upperLimit; i++) {
			FlipButton btn = new FlipButton("head_gray", "head", null);
			btns.add(btn);
			btn.setPosition(MARGIN + workerWidth * i, MARGIN * 1.4f + PROCESS_BAR_HEIGHT);
			btn.setSize(workerWidth, workerWidth);
			final int currIndex = i;
			btn.addListener(new EventListener() {
				@Override
				public boolean handle(Event event) {
					for(int i=0; i<btns.size(); i++) {
						if(i <= currIndex)
							btns.get(i).setImgUp(worker);
						else
							btns.get(i).setImgUp(grayWorker);
					}
					return true;
				}
			});
			addActor(btn);
		}
	}

	private void addPairs() {
		// initialize icon button and label
		for (int i = 0; i < NUM_PAIR; i++) {
			Button btn;
			if(i == 0)
				btn = new FlipButton("button_build_food", "button_build_food", null);
			else 
				btn = new FlipButton("button_build_house", "button_build", null);
			btn.setSize(LABEL_WIDTH, LABEL_WIDTH);
			btn.setPosition(LABEL_WIDTH * i + MARGIN, LABEL_HEIGHT + LABEL_WIDTH + PROCESS_BAR_HEIGHT + MARGIN);
			btn.addListener(new EventListener() {
				@Override
				public boolean handle(Event event) {
					return false;
				}
			});
			addActor(btn);

			LabelStyle labelStyle = new LabelStyle();
			labelStyle.font = ResourceManager.getInstance(ResourceManager.class).getFont((int) (Settings.UNIT * 0.3f));
			labelStyle.fontColor = Color.WHITE;
			Label label = new Label("27/30", labelStyle);
			label.setSize(LABEL_WIDTH, LABEL_HEIGHT);
			label.setPosition(LABEL_WIDTH * i + MARGIN, LABEL_WIDTH + PROCESS_BAR_HEIGHT + MARGIN);
			label.setAlignment(Align.center);
			addActor(label);
		}
	}
	
	private void addCloseButton() {
		Button closeButton = new FlipButton("button_cancel", "button_cancel", null);
		closeButton.setSize(MARGIN, MARGIN);
		closeButton.setPosition(getWidth() - closeButton.getWidth(), getHeight() - closeButton.getHeight());
		closeButton.addListener(new EventListener() {
			@Override
			public boolean handle(Event event) {
				BuildingInfoWindow.this.setVisible(false);
				return false;
			}
		});
		addActor(closeButton);
	}
	
	private void addHeader() {
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = ResourceManager.getInstance(ResourceManager.class).getFont((int) (Settings.UNIT * 0.3f));
		labelStyle.fontColor = Color.WHITE;
		Label label = new Label("house", labelStyle);
		label.setSize(LABEL_WIDTH, LABEL_HEIGHT);
		label.setPosition(MARGIN, MARGIN + LABEL_WIDTH * 2 + LABEL_HEIGHT + PROCESS_BAR_HEIGHT);
		label.setAlignment(Align.center);
		addActor(label);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		Color c = this.getColor();
		batch.setColor(c.r, c.g, c.b, c.a * parentAlpha);
		batch.draw(background, getX(), getY(), getWidth(), getHeight());
		applyTransform(batch, computeTransform());
		drawChildren(batch, parentAlpha);
		resetTransform(batch);
		
		//draw process bar
		batch.draw(processBar, getX() + MARGIN, getY() + MARGIN, windowWidth * process, PROCESS_BAR_HEIGHT);
		batch.draw(blackFrame, getX() + MARGIN, getY() + MARGIN, windowWidth , 			PROCESS_BAR_HEIGHT);
	}
	
	public void setListener(BuildingInfoWindowListener listener) {
		this.listener = listener;
	}
	
	public void setProcess(float process) {
		this.process = process;
	}
	
	private void setBuilderUpperLimit(int limit) {
		//最多只能有5个建筑工同时建造一个房子
		if(limit <= MAX_WORKER)
			this.upperLimit = limit;
	}
}

interface BuildingInfoWindowListener {
	
}