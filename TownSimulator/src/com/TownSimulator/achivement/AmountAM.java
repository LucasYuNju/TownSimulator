package com.TownSimulator.achivement;

import com.TownSimulator.entity.ResourceInfoCollector;

public class AmountAM extends Achievement{
	private int amount;
	
	public AmountAM(int amount, String title, String descrition, String iconTexName) {
		super(title, descrition, iconTexName);
		this.amount = amount;
	}

	@Override
	protected boolean doCheckAchievement() {
		if(ResourceInfoCollector.getInstance(ResourceInfoCollector.class).getCandyAmount() > amount)
			return true;
		else
			return false;
	}

}
