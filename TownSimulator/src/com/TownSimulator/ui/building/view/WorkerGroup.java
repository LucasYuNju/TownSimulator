package com.TownSimulator.ui.building.view;

import java.util.LinkedList;
import java.util.List;

import com.TownSimulator.ui.base.FlipButton;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.Singleton;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class WorkerGroup extends Group{
	public static final float WORKER_WIDTH = Settings.WORKER_WIDTH;
	private static final float WORKER_HEIGHT = Settings.WORKER_HEIGHT;
	public static final float HEIGHT = WORKER_HEIGHT * 1.6f;
	private TextureRegion forbiddenBuilderTexture;
	private TextureRegion allowedBuilderTexture;
	private TextureRegion builderTexture;
	private int numAllowed;
	private int numSelected;
	private int numWorker;
	private List<FlipButton> builderButtons;
	private WorkerGroupListener listener;

	public WorkerGroup(int numAllowedBuilder) {
		this.numAllowed = numAllowedBuilder;
		numSelected = numAllowedBuilder;
		builderTexture = Singleton.getInstance(ResourceManager.class).createTextureRegion("head");
		allowedBuilderTexture = Singleton.getInstance(ResourceManager.class).createTextureRegion("head_gray");
		forbiddenBuilderTexture = Singleton.getInstance(ResourceManager.class).createTextureRegion("head_forbidden");
		initBuilderButtons();
		setSize(WORKER_WIDTH * numAllowed, HEIGHT);
	}
	
	void initBuilderButtons() {
		builderButtons = new LinkedList<FlipButton>();
		for(int i=0; i<numAllowed; i++) {
			FlipButton btn;
			btn = new FlipButton(allowedBuilderTexture, allowedBuilderTexture, null);
			builderButtons.add(btn);
			btn.setPosition(WORKER_WIDTH * i, (HEIGHT - WORKER_HEIGHT) / 2);
			btn.setSize(WORKER_WIDTH, WORKER_HEIGHT);
			final int indexOfClickedButton = i;
			btn.addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					
					FlipButton clickedButton = builderButtons.get(indexOfClickedButton);
					if(clickedButton.getImgUp() == forbiddenBuilderTexture) {
						clickedButton.setImgUp(allowedBuilderTexture);
						numSelected = indexOfClickedButton + 1;
					}
					else {
						clickedButton.setImgUp(forbiddenBuilderTexture);
						numSelected = indexOfClickedButton;
					}
					
					if(numWorker > numSelected)
						numWorker = numSelected;
					for(int i=0; i<numAllowed; i++) {
						if(i == indexOfClickedButton)
							continue;
						if(i < numSelected) {
							if(i < numWorker)
								builderButtons.get(i).setImgUp(builderTexture);
							else 
								builderButtons.get(i).setImgUp(allowedBuilderTexture);
						}
						else {
							builderButtons.get(i).setImgUp(forbiddenBuilderTexture);
						}
					}
					
					if(listener != null)
						listener.workerLimitSelected(numSelected);

					return true;
				}
			});
			addActor(btn);
		}
	}
	
	public boolean addWorker() {
		if(++numWorker > numSelected) {
			numWorker = numSelected;
			return false;
		}
		refreshUI();
		return true;
	}
		
	int getNumAllowedWorker() {
		return numAllowed;
	}
	
	void setUpperLimit(int upperLimit) {
		this.numSelected = upperLimit;
	}
	
	public void refreshUI() {
		for(int i=0; i<numAllowed; i++) {
			if(i < numSelected) {
				if(i < numWorker) {
					builderButtons.get(i).setImgUp(builderTexture);
				}
				else {
					builderButtons.get(i).setImgUp(allowedBuilderTexture);
				}
			}
			else {
				builderButtons.get(i).setImgUp(forbiddenBuilderTexture);
			}
		}
	}
	
	public void setListener(WorkerGroupListener listener) {
		this.listener = listener;
	}
}