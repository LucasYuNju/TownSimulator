package com.TownSimulator.ui.screen.introduction;

import java.util.ArrayList;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.equations.Bounce;
import aurelienribon.tweenengine.equations.Quint;

import com.TownSimulator.driver.Driver;
import com.TownSimulator.driver.DriverListenerBaseImpl;
import com.TownSimulator.ui.base.IconButton;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.animation.ActorAccessor;
import com.TownSimulator.utility.animation.AnimationManager;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class IntroStoryWindow extends BaseIntroductionWindow{
	
	private Image introductionImage;
	private ArrayList<Image> manImages;
	private Image blackholeImage;
	
//	private TextureRegion introductionImage;
//	private TextureRegion manImage;
//	private TextureRegion blackHoleImage;
	private int manNum;
	private float manWidth=Settings.UNIT*0.8f;
	private float manHeight=Settings.UNIT*0.8f;
	private float blackholeWidth=2.5f*Settings.UNIT;
	private float blackholeHeight=2.5f*Settings.HEIGHTUNIT;
	private float blackholeCenterX;
	private float blackholeCenterY;
	private float acountTime=0.0f;
	private float removeInteval=1.0f;
	private int removeNum=0;
	
	public IntroStoryWindow(){
		manNum=5;
		manImages=new ArrayList<Image>();
		initComponents();
	}
	
	public void initComponents(){
		introductionImage=new Image(ResourceManager.getInstance(ResourceManager.class).createSprite("introductionStory"));
		introductionImage.setBounds(0, 3*ButtonHeight, getWidth(), 5*ButtonHeight);
		introductionImage.getColor().a=0.0f;
		addActor(introductionImage);
		
		TextureRegion manRegion=ResourceManager.getInstance(ResourceManager.class).createSprite("pixar_man_1");
		manRegion.flip(true, false);
		for(int i=0;i<manNum;i++){
			Image manImage=new Image(manRegion);
			manImage.setBounds(0+i*manWidth, ButtonHeight+ButtonMargin, manWidth, manHeight);
			manImages.add(manImage);
			addActor(manImage);
		}
		
		blackholeImage=new Image(ResourceManager.getInstance(ResourceManager.class).createSprite("button_build_mp_black_hole"));
		blackholeImage.setBounds(10.5f*ButtonWidth, -0.3f*ButtonHeight, blackholeWidth, blackholeHeight);
		blackholeCenterX=blackholeImage.getX()+blackholeWidth*0.5f;
		blackholeCenterY=blackholeImage.getY()+blackholeHeight*0.5f;
		blackholeImage.setOrigin(blackholeWidth*0.5f, blackholeHeight*0.5f);
		addActor(blackholeImage);
	}
	
	public void startPlay(){
		Tween.registerAccessor(Image.class, new ActorAccessor());
		Tween.registerAccessor(IconButton.class, new ActorAccessor());
		
		Timeline.createParallel().beginParallel()
		           .push(Tween.to(introductionImage, ActorAccessor.OPACITY, 10.0f)
		        		   .target(200))
				   .push(Tween.to(manImages.get(0), ActorAccessor.POS_XY, 1.0f).ease(Quint.IN)
						   .target(blackholeCenterX,blackholeCenterY))
				   .push(Tween.to(manImages.get(1), ActorAccessor.POS_XY, 2.0f).ease(Quint.IN)
						   .target(blackholeCenterX,blackholeCenterY))
				   .push(Tween.to(manImages.get(2), ActorAccessor.POS_XY, 3.0f).ease(Quint.IN)
						   .target(blackholeCenterX,blackholeCenterY))
				   .push(Tween.to(manImages.get(3), ActorAccessor.POS_XY, 4.0f).ease(Quint.IN)
						   .target(blackholeCenterX,blackholeCenterY))
				   .push(Tween.to(manImages.get(4), ActorAccessor.POS_XY, 5.0f).ease(Quint.IN)
						   .target(blackholeCenterX,blackholeCenterY))
				   .push(Tween.to(blackholeImage, ActorAccessor.SCALE_XY, 2.0f).ease(Bounce.INOUT)
						   .target(1.5f,1.5f).repeat(3, 0.0f))
				   .push(Tween.to(blackholeImage, ActorAccessor.ROTATION, 1.0f)
						   .target(360).repeat(6, 0.0f))   	
				   
				   .end()
				   .start(AnimationManager.getInstance(AnimationManager.class).getManager());
		              
		addDriverListener();
	}
	
	public void addDriverListener(){
		Driver.getInstance(Driver.class).addListener(new DriverListenerBaseImpl(){

			@Override
			public void update(float deltaTime) {
				// TODO Auto-generated method stub
				if(removeNum>=manNum){
					return;
				}
				acountTime+=deltaTime;
				if(acountTime>=removeInteval){
					manImages.get(removeNum).setVisible(false);
					removeNum++;
					acountTime-=removeInteval;
				}
			}
			
		});
	}
	
	public void dispose(){
		super.dispose();
		
	}
}
