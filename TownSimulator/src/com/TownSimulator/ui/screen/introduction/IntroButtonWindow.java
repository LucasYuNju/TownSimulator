package com.TownSimulator.ui.screen.introduction;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.equations.Elastic;

import com.TownSimulator.ui.base.IconButton;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.animation.ActorAccessor;
import com.TownSimulator.utility.animation.AnimationManager;
import com.badlogic.gdx.Gdx;
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
//		iconButtonLocate(totalImage, "crop_mimosa", totalTextImage,"textTotal",maxTextWidth/21*15,6);
//		iconButtonLocate(manWorkImage, "head", manWorkTextImage,"textManWork",maxTextWidth/21*19,5);
//		iconButtonLocate(manNoWorkiImage, "head_gray",manNoWorkiTextImage,"textManNoWork", maxTextWidth,4);
//		iconButtonLocate(buildingImage, "button_build", buildingTextImage,"textBuilding",maxTextWidth/21*13,3);
//		iconButtonLocate(livingBuildingImage, "button_build_house", livingBuildingTextImage,"textLivingHouse",maxTextWidth/21*20,2);
//		iconButtonLocate(mpbuildingImage, "button_build_mp",mpbuildingTextImage,"textMP",maxTextWidth/21*14, 1);
//		iconButtonLocate(speedImage, "speed_x1",speedTextImage,"textSpeed", maxTextWidth/21*18,0);
		
		height=6*(ButtonHeight+IconMargin)+IconMargin;
		totalImage=new IconButton("button_options");
		totalImage.setBounds(2.0f*ButtonWidth, height, ButtonWidth, ButtonHeight);
		addActor(totalImage);
		
		totalTextImage=new Image(ResourceManager.getInstance(ResourceManager.class).createSprite("textTotal"));
		totalTextImage.setBounds(3.5f*ButtonWidth, height+0.1f*ButtonHeight, maxTextWidth/21*15, ButtonHeight*0.8f);
		addActor(totalTextImage);
		
		height=5*(ButtonHeight+IconMargin)+IconMargin;
		manWorkImage=new IconButton("head");
		manWorkImage.setBounds(2.0f*ButtonWidth, height, ButtonWidth, ButtonHeight);
		addActor(manWorkImage);
		
		manWorkTextImage=new Image(ResourceManager.getInstance(ResourceManager.class).createSprite("textManWork"));
		manWorkTextImage.setBounds(3.5f*ButtonWidth, height+0.1f*ButtonHeight, maxTextWidth/21*19, ButtonHeight*0.8f);
		addActor(manWorkTextImage);
		
		height=4*(ButtonHeight+IconMargin)+IconMargin;
		manNoWorkiImage=new IconButton("head_gray");
		manNoWorkiImage.setBounds(2.0f*ButtonWidth, height, ButtonWidth, ButtonHeight);
		addActor(manNoWorkiImage);
		
		manNoWorkiTextImage=new Image(ResourceManager.getInstance(ResourceManager.class).createSprite("textManNoWork"));
		manNoWorkiTextImage.setBounds(3.5f*ButtonWidth, height+0.1f*ButtonHeight, maxTextWidth, ButtonHeight*0.8f);
		addActor(manNoWorkiTextImage);
		
		height=3*(ButtonHeight+IconMargin)+IconMargin;
		buildingImage=new IconButton("button_build");
		buildingImage.setBounds(2.0f*ButtonWidth, height, ButtonWidth, ButtonHeight);
		addActor(buildingImage);
		
		buildingTextImage=new Image(ResourceManager.getInstance(ResourceManager.class).createSprite("textBuilding"));
		buildingTextImage.setBounds(3.5f*ButtonWidth, height+0.1f*ButtonHeight, maxTextWidth/21*13, ButtonHeight*0.8f);
		addActor(buildingTextImage);
		
		height=2*(ButtonHeight+IconMargin)+IconMargin;
		livingBuildingImage=new IconButton("button_build_house");
		livingBuildingImage.setBounds(2.0f*ButtonWidth, height, ButtonWidth, ButtonHeight);
		addActor(livingBuildingImage);
		
		livingBuildingTextImage=new Image(ResourceManager.getInstance(ResourceManager.class).createSprite("textLivingHouse"));
		livingBuildingTextImage.setBounds(3.5f*ButtonWidth, height+0.1f*ButtonHeight, maxTextWidth/21*20, ButtonHeight*0.8f);
		addActor(livingBuildingTextImage);
		
		height=1*(ButtonHeight+IconMargin)+IconMargin;
		mpbuildingImage=new IconButton("button_build_mp");
		mpbuildingImage.setBounds(2.0f*ButtonWidth, height, ButtonWidth, ButtonHeight);
		addActor(mpbuildingImage);
		
		mpbuildingTextImage=new Image(ResourceManager.getInstance(ResourceManager.class).createSprite("textMP"));
		mpbuildingTextImage.setBounds(3.5f*ButtonWidth, height+0.1f*ButtonHeight, maxTextWidth/21*14, ButtonHeight*0.8f);
		addActor(mpbuildingTextImage);
		
		height=0*(ButtonHeight+IconMargin)+IconMargin;
		speedImage=new IconButton("speed_x1");
		speedImage.setBounds(2.0f*ButtonWidth, height, ButtonWidth, ButtonHeight);
		addActor(speedImage);
		
		speedTextImage=new Image(ResourceManager.getInstance(ResourceManager.class).createSprite("textSpeed"));
		speedTextImage.setBounds(3.5f*ButtonWidth, height+0.1f*ButtonHeight,maxTextWidth/21*18, ButtonHeight*0.8f);
		addActor(speedTextImage);
	}

//	public void iconButtonLocate(IconButton iconButton,String textureName,Image textImage,String textName,float textWidth,float yIndex){
//		height=yIndex*(ButtonHeight+IconMargin)+IconMargin;
//		
//		iconButton=new IconButton(textureName);
//		iconButton.setSize(ButtonWidth, ButtonHeight);
//		iconButton.setPosition(2.0f*ButtonWidth,height);
//		addActor(iconButton);
//		
//		textImage=new Image(ResourceManager.getInstance(ResourceManager.class).createSprite(textName));
//		textImage.setBounds(3.5f*ButtonWidth, height+0.1f*ButtonHeight, textWidth, ButtonHeight*0.8f);
//		addActor(textImage);
//	}
	
	
	public void startPlay() {
		Tween.registerAccessor(Image.class, new ActorAccessor());
		Tween.registerAccessor(IconButton.class, new ActorAccessor());
		
		float tempWidth=Gdx.graphics.getWidth();
		float tempHeight=Gdx.graphics.getHeight();
		Timeline.createParallel().beginParallel()
		               .push(Tween.from(totalImage, ActorAccessor.POS_XY, 1.0f).ease(Elastic.OUT)
	                   .target(-locationX,tempHeight-locationY))
	                   .push(Tween.from(manWorkImage, ActorAccessor.POS_XY, 1.5f).ease(Elastic.OUT)
	                   .target(-locationX,tempHeight/6*5-locationY))
	                   .push(Tween.from(manNoWorkiImage, ActorAccessor.POS_XY, 2.0f).ease(Elastic.OUT)
	                   .target(-locationX,tempHeight/6*4-locationY))
	                   .push(Tween.from(buildingImage, ActorAccessor.POS_XY, 2.5f).ease(Elastic.OUT)
	                   .target(-locationX,tempHeight/6*3-locationY))
	                   .push(Tween.from(livingBuildingImage, ActorAccessor.POS_XY, 3.0f).ease(Elastic.OUT)
	                   .target(-locationX,tempHeight/6*2-locationY))
	                   .push(Tween.from(mpbuildingImage, ActorAccessor.POS_XY, 3.5f).ease(Elastic.OUT)
	                   .target(-locationX,tempHeight/6*1-locationY))
	                   .push(Tween.from(speedImage, ActorAccessor.POS_XY, 4.0f).ease(Elastic.OUT)
	                   .target(-locationX,0))
	                   .push(Tween.from(totalTextImage, ActorAccessor.POS_XY, 1.0f).ease(Elastic.OUT)
	                   .target(tempWidth,tempHeight))
	                   .push(Tween.from(manWorkTextImage, ActorAccessor.POS_XY, 1.5f).ease(Elastic.OUT)
	                   .target(tempWidth,tempHeight/6*5))
	                   .push(Tween.from(manNoWorkiTextImage, ActorAccessor.POS_XY, 2.0f).ease(Elastic.OUT)
	                   .target(tempWidth,tempHeight/6*4))
	                   .push(Tween.from(buildingTextImage, ActorAccessor.POS_XY, 2.5f).ease(Elastic.OUT)
	                   .target(tempWidth,tempHeight/6*3))
	                   .push(Tween.from(livingBuildingTextImage, ActorAccessor.POS_XY, 3.0f).ease(Elastic.OUT)
	                   .target(tempWidth,tempHeight/6*2))
	                   .push(Tween.from(mpbuildingTextImage, ActorAccessor.POS_XY, 3.5f).ease(Elastic.OUT)
	                   .target(tempWidth,tempHeight/6*1))
	                   .push(Tween.from(speedTextImage, ActorAccessor.POS_XY, 4.0f).ease(Elastic.OUT)
	                   .target(tempWidth,0))
		   
		   .end()
		   .start(AnimationManager.getInstance(AnimationManager.class).getManager());
		
	}

}
