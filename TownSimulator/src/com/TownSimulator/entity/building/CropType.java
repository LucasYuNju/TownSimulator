package com.TownSimulator.entity.building;

public enum CropType {
	Wheat("crop_wheat");
	
	private String textureName;
	
	private CropType(String textureName) {
		this.textureName = textureName;
	}
	
	public String getTextureName()
	{
		return textureName;
	}
}
