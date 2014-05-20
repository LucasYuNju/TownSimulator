package com.TownSimulator.achivement;

import com.TownSimulator.utility.ResourceManager;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Achievement {
	private String title;
	private String description;
	private TextureRegion icon;
	boolean bAchieved = false;
	
	public Achievement(String title, String descrition, String iconTexName)
	{
		this.title = title;
		this.description = descrition;
		icon = ResourceManager.getInstance(ResourceManager.class).createTextureRegion(iconTexName);
	}
	
	public boolean check()
	{
		if(bAchieved)
			return false;
		else
		{
			boolean r = doCheckAchievement();
			bAchieved = r;
			return r;
		}
	}
	
	abstract protected boolean doCheckAchievement();
	
	public String getTitle()
	{
		return title;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public TextureRegion getIcon()
	{
		return icon;
	}
	
}
