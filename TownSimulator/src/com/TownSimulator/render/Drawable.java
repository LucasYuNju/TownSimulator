package com.TownSimulator.render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public interface Drawable {
	public void 	drawSelf(SpriteBatch batch);
	
	public float 	getDepth();
}
