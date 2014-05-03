package com.TownSimulator.render;

import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Grid implements Drawable{
	private Sprite sp;
	public static final float PAD = 0.1f;
	
	public Grid()
	{
		sp = ResourceManager.getInstance(ResourceManager.class).createSprite("grid");
		sp.setSize(Settings.UNIT - PAD * 2.0f, Settings.UNIT - PAD * 2.0f);
	}
	
	public void setGridPos(int x, int y)
	{
		sp.setPosition(x * Settings.UNIT + PAD, y * Settings.UNIT + PAD);
	}
	
	public void setColor(Color c)
	{
		sp.setColor(c);
	}
	
	public void setColor(float r, float g, float b, float a)
	{
		sp.setColor(r, g, b, a);
	}
	
	@Override
	public void drawSelf(SpriteBatch batch) {
		sp.draw(batch);
	}

	@Override
	public float getDepth() {
		return Float.MAX_VALUE * 0.5f;
	}

}
