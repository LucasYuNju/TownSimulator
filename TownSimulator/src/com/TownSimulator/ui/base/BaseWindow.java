package com.TownSimulator.ui.base;

import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;

public class BaseWindow extends Group{
	private TextureRegion background = ResourceManager.getInstance(ResourceManager.class).createTextureRegion("background");

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(1.0f, 1.0f, 1.0f, parentAlpha * Settings.UI_ALPHA);
		batch.draw(background, getX(), getY(), getWidth(), getHeight());
		
		super.draw(batch, parentAlpha);
	}
	
	
}
