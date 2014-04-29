package com.TownSimulator.ui.building.construction;

import java.util.LinkedList;
import java.util.List;

import com.TownSimulator.ui.base.FlipButton;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.Singleton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;

public class ConstructionBuilderGroup extends Group{
	private static final float BUILDER_WIDTH = Settings.UNIT * 0.6f;
	private TextureRegion forbiddenBuilderTexture;
	private TextureRegion alloweBuilderTexture;
	private TextureRegion builderTexture;
	private int numAllowed;
	private int numSelected;
	private int numBuilder;
	private List<FlipButton> builderButtons = new LinkedList<FlipButton>();

	public ConstructionBuilderGroup(int numAllowedBuilder) {
		numBuilder = 0;
		numSelected = numAllowedBuilder/2;
		this.numAllowed = numAllowedBuilder;
		builderTexture = Singleton.getInstance(ResourceManager.class).findTextureRegion("head");
		alloweBuilderTexture = Singleton.getInstance(ResourceManager.class).findTextureRegion("head_gray");
		forbiddenBuilderTexture = Singleton.getInstance(ResourceManager.class).findTextureRegion("head_forbidden");
		addBuilderButtons();
		setSize(BUILDER_WIDTH * numAllowed, BUILDER_WIDTH);
	}
	
	void addBuilderButtons() {
		for(int i=0; i<numAllowed; i++) {
			FlipButton btn;
			if(i < numSelected) {
				btn = new FlipButton("head_gray", "head_gray", null);
				Gdx.app.log("System.out", "1");
			}
			else {
				btn = new FlipButton("head_forbidden", "head_forbidden", null);
				Gdx.app.log("System.out", "3");
			}
			builderButtons.add(btn);
			btn.setPosition(BUILDER_WIDTH * i, 0);
			btn.setSize(BUILDER_WIDTH, BUILDER_WIDTH);
			final int indexOfCurrentButton = i;
			btn.addListener(new EventListener() {
				@Override
				public boolean handle(Event event) {
					System.out.println(builderButtons.size());
					numSelected = indexOfCurrentButton;
					builderButtons.get(0).setImgUp(builderTexture);
					Gdx.app.log("System.out", numAllowed + ":" + numSelected + ":" + numBuilder);
					for(int i=0; i<numAllowed; i++) {
						Gdx.app.log("System.out", i + " iteration setted");
//						if(i < numSelected) {
//							if(i < numBuilder) {
//								builderButtons.get(i).setImgUp(builderTexture);
//								Gdx.app.log("System.out", "1");
//							}
//							else {
//								builderButtons.get(i).setImgUp(grayBuilderTexture);
//								Gdx.app.log("System.out", "2");
//							}
//						}
//						else {
//							builderButtons.get(i).setImgUp(forbiddenBuilderTexture);
//							Gdx.app.log("System.out", "3");
//						}
					}
					return true;
				}
			});
			addActor(btn);
		}
	}

	boolean addBuilder() {
//		if(++numBuilder > numSelected) {
//			numBuilder = numSelected;
//			return false;
//		}
//		builderButtons.get(numBuilder - 1).setImgUp(builderTexture);
		return true;
	}
	
	int getGroupWidth() {
		return (int) (numAllowed * BUILDER_WIDTH);
	}
	
	int getNumAllowedBuilder() {
		return numAllowed;
	}
	
	void setUpperLimit(int upperLimit) {
//		this.numSelected = upperLimit;
	}
}