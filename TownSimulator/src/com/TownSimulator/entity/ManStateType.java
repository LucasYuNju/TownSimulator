package com.TownSimulator.entity;

public enum ManStateType {
	Normal(null),
	Hungry("man_state_hungry"),
	Sick("man_state_sick"),
	Depressed("man_state_depressed"),
	Working("man_state_working"),
	Study("man_state_study");
	
	private String iconTex;
	
	private ManStateType(String iconTex)
	{
		this.iconTex = iconTex;
	}
	
	public String getIconTex()
	{
		return iconTex;
	}
}
