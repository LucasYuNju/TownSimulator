package com.TownSimulator.entity.building;

import com.TownSimulator.entity.ResourceType;

public enum CropType {
	Wheat("crop_wheat", ResourceType.RS_WHEAT);
	
	private String textureName;
	private ResourceType rsType; //作物对应的资源
	private CropType(String textureName, ResourceType resourceType)
	{
		this.textureName = textureName;
		this.rsType = resourceType;
	}
	
	public String getTextureName()
	{
		return textureName;
	}
	
	public ResourceType getResourceType()
	{
		return rsType;
	}
}
