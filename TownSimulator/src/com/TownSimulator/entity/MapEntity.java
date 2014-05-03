package com.TownSimulator.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class MapEntity extends Entity{
	
	public MapEntity(Sprite sp)
	{
		super(sp);
	}
	
	public MapEntity(String textureName) 
	{
		super(textureName);
	}
	
}
