package com.TownSimulator.ui.screen.introduction;

import com.TownSimulator.utility.Settings;
import com.badlogic.gdx.scenes.scene2d.Group;

public  class BaseIntroductionWindow extends Group{
	protected float ButtonWidth=Settings.UNIT;
	protected float ButtonHeight=Settings.HEIGHTUNIT;
	protected float ButtonMargin=ButtonWidth*0.1f;
	protected float windowWidth=14*Settings.UNIT;
	protected float windowHeight=8*Settings.HEIGHTUNIT;
	protected float locationX=(float)0.5*Settings.UNIT;
	protected float locationY=(float)Settings.HEIGHTUNIT;
	
	public BaseIntroductionWindow(){
		super();
		setBounds(locationX, locationY, windowWidth, windowHeight);
		setVisible(false);
	}
	
	public void dispose(){
		this.dispose();
	}
}
