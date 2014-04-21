package com.townSimulator.game.render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public interface GameDrawable {
	public void drawSelf(SpriteBatch batch);
	
	public void setVisible(boolean v);
	
	public float getDepth();
}
