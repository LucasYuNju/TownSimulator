package com.TownSimulator.achivement;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

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
		int amount;
		String desc;
		amount = 1000;
		desc = "Your mom likes your candies (" + NumberFormat.getInstance(Locale.US).format(amount) + ")";
		achievements.add(new AmountAM(amount, "Mum is Happy", desc, "achievement_icon_copper"));
		amount = 10000;
		desc = "Supermarkets are selling your candies (" + NumberFormat.getInstance(Locale.US).format(amount) + ")";
		achievements.add(new AmountAM(amount, "Everyone likes it", desc, "achievement_icon_silver"));
		amount = 1000000;
		desc = "You fill the earth with your candies (" + NumberFormat.getInstance(Locale.US).format(amount) + ")";
		achievements.add(new AmountAM(amount, "The World!", desc, "achievement_icon_golden"));
		amount = 100000000;
		desc = "The god is eating your candies (" + NumberFormat.getInstance(Locale.US).format(amount) + ")";
		achievements.add(new AmountAM(amount, "Oh My God!!", desc, "achievement_icon_cup"));
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
