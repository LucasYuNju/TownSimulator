package com.TownSimulator.ui.screen.introduction;

import org.omg.CORBA.PRIVATE_MEMBER;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.equations.Elastic;

import com.TownSimulator.ui.base.IconButton;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.animation.ActorAccessor;
import com.TownSimulator.utility.animation.AnimationManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class IntroPointWindow extends BaseIntroductionWindow{
	
	private float IconMargin=ButtonMargin*1.4f;
	private float height=0.0f;
	
	private Image coldImage;
	private Image happyImage;
	private Image hungryImage;
	private Image sickImage;
	private Image studyImage;
	private Image workImage;
	private Image energyImage;
	
	private Image textcoldImage;
	private Image texthappyImage;
	private Image texthungryImage;
	private Image textsickImage;
	private Image textstudyImage;
	private Image textworkImage;
	private Image textEnergyImage;
	
	private float maxTextWidth;
	
	
	public IntroPointWindow(){
		maxTextWidth=10.0f*ButtonWidth;
		initComponents();
	}
	
	public void initComponents(){
		height=6*(ButtonHeight+IconMargin)+IconMargin;
		coldImage=new Image(ResourceManager.getInstance(ResourceManager.class).createSprite("man_state_cold"));
		coldImage.setBounds(2.0f*ButtonWidth, height, ButtonWidth, ButtonHeight);
		addActor(coldImage);
		
		textcoldImage=new Image(ResourceManager.getInstance(ResourceManager.class).createSprite("textCold"));
		textcoldImage.setBounds(3.5f*ButtonWidth, height+0.1f*ButtonHeight, maxTextWidth, ButtonHeight*0.8f);
		addActor(textcoldImage);
		
		height=5*(ButtonHeight+IconMargin)+IconMargin;
		happyImage=new Image(ResourceManager.getInstance(ResourceManager.class).createSprite("man_state_depressed"));
		happyImage.setBounds(2.0f*ButtonWidth, height, ButtonWidth, ButtonHeight);
		addActor(happyImage);
		
		texthappyImage=new Image(ResourceManager.getInstance(ResourceManager.class).createSprite("textHappy"));
		texthappyImage.setBounds(3.5f*ButtonWidth, height+0.1f*ButtonHeight, maxTextWidth/20*19, ButtonHeight*0.8f);
		addActor(texthappyImage);
		
		height=4*(ButtonHeight+IconMargin)+IconMargin;
		hungryImage=new Image(ResourceManager.getInstance(ResourceManager.class).createSprite("man_state_hungry"));
		hungryImage.setBounds(2.0f*ButtonWidth, height, ButtonWidth, ButtonHeight);
		addActor(hungryImage);
		
		texthungryImage=new Image(ResourceManager.getInstance(ResourceManager.class).createSprite("textHungry"));
		texthungryImage.setBounds(3.5f*ButtonWidth, height+0.1f*ButtonHeight, maxTextWidth, ButtonHeight*0.8f);
		addActor(texthungryImage);
		
		height=3*(ButtonHeight+IconMargin)+IconMargin;
		sickImage=new Image(ResourceManager.getInstance(ResourceManager.class).createSprite("man_state_sick"));
		sickImage.setBounds(2.0f*ButtonWidth, height, ButtonWidth, ButtonHeight);
		addActor(sickImage);
		
		textsickImage=new Image(ResourceManager.getInstance(ResourceManager.class).createSprite("textSick"));
		textsickImage.setBounds(3.5f*ButtonWidth, height+0.1f*ButtonHeight, maxTextWidth/20*15, ButtonHeight*0.8f);
		addActor(textsickImage);
		
		height=2*(ButtonHeight+IconMargin)+IconMargin;
		studyImage=new Image(ResourceManager.getInstance(ResourceManager.class).createSprite("man_state_study"));
		studyImage.setBounds(2.0f*ButtonWidth, height, ButtonWidth, ButtonHeight);
		addActor(studyImage);
		
		textstudyImage=new Image(ResourceManager.getInstance(ResourceManager.class).createSprite("textStudy"));
		textstudyImage.setBounds(3.5f*ButtonWidth, height+0.1f*ButtonHeight, maxTextWidth/20*18, ButtonHeight*0.8f);
		addActor(textstudyImage);
		
		height=1*(ButtonHeight+IconMargin)+IconMargin;
		workImage=new Image(ResourceManager.getInstance(ResourceManager.class).createSprite("man_state_working"));
		workImage.setBounds(2.0f*ButtonWidth, height, ButtonWidth, ButtonHeight);
		addActor(workImage);
		
		textworkImage=new Image(ResourceManager.getInstance(ResourceManager.class).createSprite("textWork"));
		textworkImage.setBounds(3.5f*ButtonWidth, height+0.1f*ButtonHeight, maxTextWidth/20*14, ButtonHeight*0.8f);
		addActor(textworkImage);
		
		height=0*(ButtonHeight+IconMargin)+IconMargin;
		energyImage=new Image(ResourceManager.getInstance(ResourceManager.class).createSprite("button_build_mp2"));
		energyImage.setBounds(2.0f*ButtonWidth, height, ButtonWidth, ButtonHeight);
		addActor(energyImage);
		
		textEnergyImage=new Image(ResourceManager.getInstance(ResourceManager.class).createSprite("textEnergy"));
		textEnergyImage.setBounds(3.5f*ButtonWidth, height+0.1f*ButtonHeight,maxTextWidth/20*17, ButtonHeight*0.8f);
		addActor(textEnergyImage);		
	}
	
	
	public void startPlay() {
		Tween.registerAccessor(Image.class, new ActorAccessor());
		Tween.registerAccessor(IconButton.class, new ActorAccessor());
		
		float tempWidth=Gdx.graphics.getWidth();
		float tempHeight=Gdx.graphics.getHeight();
		Timeline.createParallel().beginParallel()
		               .push(Tween.from(coldImage, ActorAccessor.POS_XY, 1.0f).ease(Elastic.OUT)
	                   .target(-locationX,tempHeight-locationY))
	                   .push(Tween.from(happyImage, ActorAccessor.POS_XY, 1.5f).ease(Elastic.OUT)
	                   .target(-locationX,tempHeight/6*5-locationY))
	                   .push(Tween.from(hungryImage, ActorAccessor.POS_XY, 2.0f).ease(Elastic.OUT)
	                   .target(-locationX,tempHeight/6*4-locationY))
	                   .push(Tween.from(sickImage, ActorAccessor.POS_XY, 2.5f).ease(Elastic.OUT)
	                   .target(-locationX,tempHeight/6*3-locationY))
	                   .push(Tween.from(studyImage, ActorAccessor.POS_XY, 3.0f).ease(Elastic.OUT)
	                   .target(-locationX,tempHeight/6*2-locationY))
	                   .push(Tween.from(workImage, ActorAccessor.POS_XY, 3.5f).ease(Elastic.OUT)
	                   .target(-locationX,tempHeight/6*1-locationY))
	                   .push(Tween.from(energyImage, ActorAccessor.POS_XY, 4.0f).ease(Elastic.OUT)
	                   .target(-locationX,0))
	                   .push(Tween.from(textcoldImage, ActorAccessor.POS_XY, 1.0f).ease(Elastic.OUT)
	                   .target(tempWidth,tempHeight))
	                   .push(Tween.from(texthappyImage, ActorAccessor.POS_XY, 1.5f).ease(Elastic.OUT)
	                   .target(tempWidth,tempHeight/6*5))
	                   .push(Tween.from(texthungryImage, ActorAccessor.POS_XY, 2.0f).ease(Elastic.OUT)
	                   .target(tempWidth,tempHeight/6*4))
	                   .push(Tween.from(textsickImage, ActorAccessor.POS_XY, 2.5f).ease(Elastic.OUT)
	                   .target(tempWidth,tempHeight/6*3))
	                   .push(Tween.from(textstudyImage, ActorAccessor.POS_XY, 3.0f).ease(Elastic.OUT)
	                   .target(tempWidth,tempHeight/6*2))
	                   .push(Tween.from(textworkImage, ActorAccessor.POS_XY, 3.5f).ease(Elastic.OUT)
	                   .target(tempWidth,tempHeight/6*1))
	                   .push(Tween.from(textEnergyImage, ActorAccessor.POS_XY, 4.0f).ease(Elastic.OUT)
	                   .target(tempWidth,0))
		   
		   .end()
		   .start(AnimationManager.getInstance(AnimationManager.class).getManager());
		
	}

}
