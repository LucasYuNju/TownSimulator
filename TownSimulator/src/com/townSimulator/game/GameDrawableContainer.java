package com.townSimulator.game;

import com.badlogic.gdx.math.Rectangle;

public interface GameDrawableContainer {
	public void draw(RenderBatch batch, Rectangle scissorRect);
}
