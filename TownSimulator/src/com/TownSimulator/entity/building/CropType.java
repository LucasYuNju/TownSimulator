package com.TownSimulator.entity.building;

public enum CropType {
	WHEAT;
	
	
	public static String getTextureName(CropType type)
	{
		String textureName = null;
		switch (type) {
		case WHEAT:
			textureName = "crop_wheat";
			break;

		default:
			break;
		}
		
		return textureName;
	}
}
