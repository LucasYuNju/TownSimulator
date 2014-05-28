package com.TownSimulator.achivement;

import java.util.ArrayList;

import com.TownSimulator.driver.Driver;
import com.TownSimulator.driver.DriverListenerBaseImpl;
import com.TownSimulator.ui.UIManager;
import com.TownSimulator.utility.ResourceManager;
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
		int amount;
//		String desc;
		amount = 1000;
//		desc = "Your mom likes your candies (" + NumberFormat.getInstance(Locale.US).format(amount) + ")";
		achievements.add(new AmountAM(amount, 	ResourceManager.stringMap.get("achivement_title_level_0"),
												ResourceManager.stringMap.get("achivement_desc_level_0"), "achievement_icon_copper"));
		amount = 10000;
//		desc = "Supermarkets are selling your candies (" + NumberFormat.getInstance(Locale.US).format(amount) + ")";
		achievements.add(new AmountAM(amount, 	ResourceManager.stringMap.get("achivement_title_level_1"),
												ResourceManager.stringMap.get("achivement_desc_level_1"), "achievement_icon_silver"));
		amount = 1000000;
//		desc = "You fill the earth with your candies (" + NumberFormat.getInstance(Locale.US).format(amount) + ")";
		achievements.add(new AmountAM(amount, 	ResourceManager.stringMap.get("achivement_title_level_2"),
												ResourceManager.stringMap.get("achivement_desc_level_2"), "achievement_icon_golden"));
		amount = 100000000;
//		desc = "The god is eating your candies (" + NumberFormat.getInstance(Locale.US).format(amount) + ")";
		achievements.add(new AmountAM(amount, 	ResourceManager.stringMap.get("achivement_title_level_3"),
												ResourceManager.stringMap.get("achivement_desc_level_3"), "achievement_icon_cup"));
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
