package com.TownSimulator.ui.utility.ls;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import com.TownSimulator.utility.ResourceManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SerializedTextureRegion extends TextureRegion implements Serializable{
	private static final long serialVersionUID = -6107910048288626615L;
	private String name;
	
	public SerializedTextureRegion(Texture texture, String name) {
		super(texture);
		this.name = name;
	}
	
	public String getTextureName() {
		return name;
	}
	
	private void readObject(ObjectInputStream s) throws ClassNotFoundException, IOException {
		s.defaultReadObject();
		ResourceManager.getInstance(ResourceManager.class).reloadTextureRegion(this);
	}
}
