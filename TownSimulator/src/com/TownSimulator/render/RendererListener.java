package com.TownSimulator.render;

import java.io.Serializable;

public interface RendererListener extends Serializable{
	public void renderBegined();
	
	public void renderEnded();
	
	public void emptyTapped();
}
