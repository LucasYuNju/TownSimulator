package com.TownSimulator.achivement;

import java.util.ArrayList;

import com.TownSimulator.driver.Driver;
import com.TownSimulator.driver.DriverListenerBaseImpl;
import com.TownSimulator.ui.UIManager;
import com.TownSimulator.utility.Singleton;

public class AchievementManager extends Singleton{
	private ArrayList<Achievement> achievements;
	
	private AchievementManager()
	{
		Driver.getInstance(Driver.class).addListener(new DriverListenerBaseImpl()
		{
			@Override
			public void update(float deltaTime) {
				checkAchievements();
			}
			
		});
		
		initAchievements();
	}
	
	private void initAchievements()
	{
		achievements = new ArrayList<Achievement>();
		
		achievements.add(new FirstMPBuildingAM());
	}
	
	private void checkAchievements()
	{
		for (Achievement achieve : achievements) {
			if(achieve.check())
				showAchievement(achieve);
		}
	}
	
	private void showAchievement(Achievement achieve)
	{
		System.out.println("Achieve " + achieve.getTitle());
		UIManager.getInstance(UIManager.class).getGameUI().getAchievementUI().pushToShow(achieve);
	}
	
}
