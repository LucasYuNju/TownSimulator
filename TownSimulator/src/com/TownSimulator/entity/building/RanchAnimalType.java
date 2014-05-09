package com.TownSimulator.entity.building;


public enum RanchAnimalType {
	COW("animal_cow", "Cow");
	
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
