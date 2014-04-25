package com.townSimulator.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public interface Drawable {
	public void 	drawSelf(SpriteBatch batch);
	
	public void 	setVisible(boolean v);
	
	public float 	getDepth();
	
	public void 	setDepth(float depth);
	
	public void 	setDrawSize(float width, float height);
	
	public void 	setDrawPosition(float x, float y);
	
	public float 	getDrawWidth();
	
	public float 	getDrawHeight();
	
	public float 	getDrawX();
	
	public float 	getDrawY();
}
