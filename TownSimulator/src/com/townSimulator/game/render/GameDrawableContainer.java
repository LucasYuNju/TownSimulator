package com.townSimulator.game.render;

import com.badlogic.gdx.math.Rectangle;

public interface GameDrawableContainer {
	public void draw(RenderBatch batch, Rectangle scissorRect);
}
