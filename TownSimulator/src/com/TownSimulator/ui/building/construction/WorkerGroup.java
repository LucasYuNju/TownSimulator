package com.TownSimulator.ui.building.construction;

import java.util.LinkedList;
import java.util.List;

import com.TownSimulator.ui.base.FlipButton;
import com.TownSimulator.ui.building.UndockedWindow;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.Singleton;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class WorkerGroup extends Group{
	public static final float WORKER_WIDTH = Settings.UNIT * 0.6f;
	public static final float WORKER_HEIGHT = Settings.UNIT * 0.6f;
	private TextureRegion forbiddenBuilderTexture;
	private TextureRegion allowedBuilderTexture;
	private TextureRegion builderTexture;
	private int numAllowed;
	private int numSelected;
	private int numBuilder;
	private List<FlipButton> builderButtons = new LinkedList<FlipButton>();
	private UndockedWindow window;

	public WorkerGroup(UndockedWindow window, int numAllowedBuilder) {
		this.window = window;
		this.numAllowed = numAllowedBuilder;
		numSelected = numAllowedBuilder;
		builderTexture = Singleton.getInstance(ResourceManager.class).findTextureRegion("head");
		allowedBuilderTexture = Singleton.getInstance(ResourceManager.class).findTextureRegion("head_gray");
		forbiddenBuilderTexture = Singleton.getInstance(ResourceManager.class).findTextureRegion("head_forbidden");
		addBuilderButtons();
		setSize(WORKER_WIDTH * numAllowed, WORKER_WIDTH);
	}
	
	void addBuilderButtons() {
		for(int i=0; i<numAllowed; i++) {
			FlipButton btn;
			btn = new FlipButton(allowedBuilderTexture, allowedBuilderTexture, null);
			builderButtons.add(btn);
			btn.setPosition(WORKER_WIDTH * i, 0);
			btn.setSize(WORKER_WIDTH, WORKER_WIDTH);
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

					window.builderLimitSelected(numSelected);
					for(int i=0; i<numAllowed; i++) {
						if(i == indexOfClickedButton)
							continue;
						if(i < numSelected) {
							if(i < numBuilder) {
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
					return true;
				}
			});
			addActor(btn);
		}
	}

	boolean addBuilder() {
		if(++numBuilder > numSelected) {
			numBuilder = numSelected;
			return false;
		}
		builderButtons.get(numBuilder - 1).setImgUp(builderTexture);
		refresh();
		return true;
	}
	
	int getGroupWidth() {
		return (int) (numAllowed * WORKER_WIDTH);
	}
	
	int getNumAllowedBuilder() {
		return numAllowed;
	}
	
	void setUpperLimit(int upperLimit) {
		this.numSelected = upperLimit;
	}
	
	public void refresh() {
		for(int i=0; i<numAllowed; i++) {
			if(i < numSelected) {
				if(i < numBuilder) {
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
}