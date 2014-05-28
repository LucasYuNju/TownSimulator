package com.TownSimulator.entity;

import com.TownSimulator.utility.ResourceManager;

public enum RanchAnimalType {
	Cow("animal_cow", ResourceManager.stringMap.get("animal_cow"));
	
	private String textureName;
	private String viewName;
	private RanchAnimalType(String textureName, String viewName)
	{
		this.textureName = textureName;
		this.viewName = viewName;
	}
	
	public String getTextureName()
	{
		return textureName;
	}

	public String getViewName() {
		return viewName;
	}
	
	public static RanchAnimalType findWithViewName(String viewName)
	{
		for (RanchAnimalType type : values()) {
			if(type.getViewName().equals(viewName))
				return type;
		}
		return null;
	}
}
