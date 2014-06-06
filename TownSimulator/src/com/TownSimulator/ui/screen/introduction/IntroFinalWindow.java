package com.TownSimulator.ui.screen.introduction;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.equations.Elastic;

import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.animation.ActorAccessor;
import com.TownSimulator.utility.animation.AnimationManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class IntroFinalWindow extends BaseIntroductionWindow{
	
	private Image textMainImage;
	private float textMainloX;
	private float textMainloY;
	private float originPosX ;
	private float originPoxY;
	
	public IntroFinalWindow(){
		initComponents();
	}
	
	public void initComponents(){
		originPosX = Gdx.graphics.getWidth()/2;
		originPoxY = Gdx.graphics.getHeight()/2;
		
		textMainImage=new Image(ResourceManager.getInstance(ResourceManager.class).createSprite("textMain"));
		textMainImage.setSize(getWidth()*3/5, getHeight()/5);
		textMainloX=originPosX-textMainImage.getWidth()/2;
		textMainloY=originPoxY-textMainImage.getHeight()/2;
		textMainImage.setPosition(textMainloX, textMainloY);
		addActor(textMainImage);
		
	}

	@Override
	public void startPlay() {
		// TODO Auto-generated method stub
		Tween.registerAccessor(Image.class, new ActorAccessor());
		
		Timeline.createParallel().beginParallel()
		           .push(Tween.from(textMainImage, ActorAccessor.POS_XY, 3.0f).ease(Elastic.OUT)
		        		   .target(textMainloX,textMainloY+originPoxY)) 	
				   
				   .end()
				   .start(AnimationManager.getInstance(AnimationManager.class).getManager());
	}

}
