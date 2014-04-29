package com.TownSimulator.ui.building.construction;

import java.util.LinkedList;
import java.util.List;

import com.TownSimulator.ui.base.FlipButton;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.Singleton;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;

public class ConstructionBuilderGroup extends Group{
	private static final float BUILDER_WIDTH = Settings.UNIT * 0.6f;
	private TextureRegion forbiddenBuilderTexture;
	private TextureRegion allowedBuilderTexture;
	private TextureRegion builderTexture;
	private int numAllowed;
	private int numSelected;
	private int numBuilder;
	private List<FlipButton> builderButtons = new LinkedList<FlipButton>();
	private ConstructionWindow window;

	public ConstructionBuilderGroup(ConstructionWindow window, int numAllowedBuilder) {
		this.window = window;
		this.numAllowed = numAllowedBuilder;
		numSelected = numAllowedBuilder;
		builderTexture = Singleton.getInstance(ResourceManager.class).findTextureRegion("head");
		allowedBuilderTexture = Singleton.getInstance(ResourceManager.class).findTextureRegion("head_gray");
		forbiddenBuilderTexture = Singleton.getInstance(ResourceManager.class).findTextureRegion("head_forbidden");
		addBuilderButtons();
		setSize(BUILDER_WIDTH * numAllowed, BUILDER_WIDTH);
	}
	
	void addBuilderButtons() {
		for(int i=0; i<numAllowed; i++) {
			FlipButton btn;
			btn = new FlipButton(allowedBuilderTexture, allowedBuilderTexture, null);
			builderButtons.add(btn);
			btn.setPosition(BUILDER_WIDTH * i, 0);
			btn.setSize(BUILDER_WIDTH, BUILDER_WIDTH);
			final int indexOfClickedButton = i;
			btn.addListener(new EventListener() {
				@Override
				public boolean handle(Event event) {
					FlipButton clickedButton = builderButtons.get(indexOfClickedButton);
					if(clickedButton.getImgUp() == forbiddenBuilderTexture) {
						clickedButton.setImgUp(allowedBuilderTexture);
						numSelected++;
					}
					else {
						clickedButton.setImgUp(forbiddenBuilderTexture);
						numSelected--;
					}
					
					numSelected = indexOfClickedButton + 1;
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
		return true;
	}
	
	int getGroupWidth() {
		return (int) (numAllowed * BUILDER_WIDTH);
	}
	
	int getNumAllowedBuilder() {
		return numAllowed;
	}
	
	void setUpperLimit(int upperLimit) {
		this.numSelected = upperLimit;
	}
}