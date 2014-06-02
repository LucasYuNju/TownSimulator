package com.TownSimulator.ui.screen.introduction;

import java.util.ArrayList;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.equations.Bounce;

import com.TownSimulator.entity.Man;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.animation.ActorAccessor;
import com.TownSimulator.utility.animation.AnimationManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
	
	public IntroStoryWindow(){
		manNum=5;
		manImages=new ArrayList<Image>();
		initComponents();
	}
	
	public void initComponents(){
		introductionImage=new Image(ResourceManager.getInstance(ResourceManager.class).createSprite("introductionStory"));
		introductionImage.setBounds(0, 3*ButtonHeight, getWidth(), 5*ButtonHeight);
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
		blackholeImage.setBounds(11.5f*ButtonWidth, 0.25f*ButtonHeight+ButtonMargin, blackholeWidth, blackholeHeight);
		addActor(blackholeImage);
		Tween.registerAccessor(Image.class, new ActorAccessor());
		Tween.to(blackholeImage, ActorAccessor.POS_XY, 1.0f).ease(Bounce.OUT)
		.target(7*ButtonWidth,0).start(AnimationManager.getInstance(AnimationManager.class).getManager());
	}
	
	
	public void dispose(){
		super.dispose();
	}
}
