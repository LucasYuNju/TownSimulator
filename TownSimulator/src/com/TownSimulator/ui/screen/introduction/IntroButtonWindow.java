package com.TownSimulator.ui.screen.introduction;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.equations.Bounce;
import aurelienribon.tweenengine.equations.Quint;

import com.TownSimulator.ui.base.IconButton;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.animation.ActorAccessor;
import com.TownSimulator.utility.animation.AnimationManager;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class IntroButtonWindow extends BaseIntroductionWindow{
	
	private IconButton speedImage;
	private IconButton manWorkImage;
	private IconButton manNoWorkiImage;
	private IconButton buildingImage;
	private IconButton totalImage;
	private IconButton livingBuildingImage;
	private IconButton mpbuildingImage;
	private float IconMargin=ButtonMargin*1.4f;
	private float height=0.0f;
	
	private Image speedTextImage;
	private Image manWorkTextImage;
	private Image manNoWorkiTextImage;
	private Image buildingTextImage;
	private Image totalTextImage;
	private Image livingBuildingTextImage;
	private Image mpbuildingTextImage;
	private float maxTextWidth;
	
	
	public IntroButtonWindow(){
		maxTextWidth=10.0f*ButtonWidth;
		initComponents();
	}
	
	public void initComponents(){
		iconButtonLocate(totalImage, "crop_mimosa", totalTextImage,"textTotal",maxTextWidth/21*15,6);
		iconButtonLocate(manWorkImage, "head", manWorkTextImage,"textManWork",maxTextWidth/21*19,5);
		iconButtonLocate(manNoWorkiImage, "head_gray",manNoWorkiTextImage,"textManNoWork", maxTextWidth,4);
		iconButtonLocate(buildingImage, "button_build", buildingTextImage,"textBuilding",maxTextWidth/21*13,3);
		iconButtonLocate(livingBuildingImage, "button_build_house", livingBuildingTextImage,"textLivingHouse",maxTextWidth/21*20,2);
		iconButtonLocate(mpbuildingImage, "button_build_mp",mpbuildingTextImage,"textMP",maxTextWidth/21*14, 1);
		iconButtonLocate(speedImage, "speed_x1",speedTextImage,"textSpeed", maxTextWidth/21*18,0);
		
//		totalImage=new IconButton("crop_mimosa");
//		totalImage.setBounds(ButtonWidth, ButtonHeight, ButtonWidth, ButtonHeight);
//		addActor(totalImage);
	}
	/**
	 * 
	 * yIndex 从0开始index
	 */
	public void iconButtonLocate(IconButton iconButton,String textureName,Image textImage,String textName,float textWidth,float yIndex){
		height=yIndex*(ButtonHeight+IconMargin)+IconMargin;
		
		iconButton=new IconButton(textureName);
		iconButton.setSize(ButtonWidth, ButtonHeight);
		iconButton.setPosition(2.0f*ButtonWidth,height);
		addActor(iconButton);
		
		textImage=new Image(ResourceManager.getInstance(ResourceManager.class).createSprite(textName));
		textImage.setBounds(3.5f*ButtonWidth, height+0.1f*ButtonHeight, textWidth, ButtonHeight*0.8f);
		addActor(textImage);
	}
	
	
	public void startPlay() {
		//System.out.println(totalImage.toString()+"-"+totalImage.getX()/Settings.UNIT);
		if(totalImage==null){
			System.out.println("111");
		}
		Tween.registerAccessor(Image.class, new ActorAccessor());
		Tween.registerAccessor(IconButton.class, new ActorAccessor());
		
		Timeline.createSequence().beginSequence()
//             .push(Tween.to(totalTextImage, ActorAccessor.SCALE_XY, 2.0f).ease(Bounce.INOUT)
//					   .target(5f,5f).repeat(3, 0.0f))
   		     .push(Tween.to(totalImage, ActorAccessor.POS_XY, 10.0f).ease(Quint.IN)
	                   .target(5*ButtonWidth,5*ButtonHeight))
		   
		   .end()
		   .start(AnimationManager.getInstance(AnimationManager.class).getManager());
		
	}

}
