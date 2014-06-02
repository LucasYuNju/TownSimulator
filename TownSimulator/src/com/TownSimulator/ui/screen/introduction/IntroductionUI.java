package com.TownSimulator.ui.screen.introduction;

import java.util.ArrayList;

import com.TownSimulator.ui.base.FlipButton;
import com.TownSimulator.ui.base.IconButton;
import com.TownSimulator.ui.base.ScreenUIBase;
import com.TownSimulator.ui.screen.GameScreen;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class IntroductionUI extends ScreenUIBase{
	
	private IntroductionListener introductionListener;
	public float ButtonWidth=Settings.UNIT;
	public float ButtonHeight=Settings.HEIGHTUNIT;
	private float ButtonMargin=ButtonWidth*0.1f;
	
	private IconButton previousButton;
	private IconButton nextButton;

//	private SpriteBatch spriteBatch;
	private ArrayList<BaseIntroductionWindow> windowPages;
	private int currentIndex=0;
	
	public IntroductionUI(){
//		spriteBatch=new SpriteBatch();
		windowPages=new ArrayList<BaseIntroductionWindow>();
		
		initComponents();
		initWindowPages();
	}
	
	public void initComponents(){
		previousButton=new IconButton("animal_caonima");
		previousButton.setSize(ButtonWidth, ButtonHeight);
		previousButton.setPosition(ButtonMargin, ButtonMargin);
		previousButton.addListener(new InputListener(){

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				windowPages.get(currentIndex).setVisible(false);
				currentIndex--;
				if(currentIndex==0){
					previousButton.setVisible(false);
				}
				windowPages.get(currentIndex).setVisible(true);
			}
			
		});

		
		nextButton=new IconButton("crop_rose");
		nextButton.setSize(ButtonWidth, ButtonHeight);
		nextButton.setPosition(Gdx.graphics.getWidth()-ButtonWidth-ButtonMargin, ButtonMargin);
		nextButton.addListener(new InputListener(){

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				if(currentIndex+1==windowPages.size()){
					introductionListener.introductionFinish();
					return;
				}
				windowPages.get(currentIndex).setVisible(false);
				currentIndex++;
				if(currentIndex!=0){
					previousButton.setVisible(true);
				}
				windowPages.get(currentIndex).setVisible(true);
			}
			
		});
		previousButton.setVisible(false);
		nextButton.setVisible(true);
		
		mStage.addActor(previousButton);
		mStage.addActor(nextButton);
	}
	
	public void initWindowPages(){
		windowPages.add(new IntroStoryWindow());
		windowPages.add(new IntroStoryWindow());
		windowPages.add(new IntroStoryWindow());
		
		for(int i=0;i<windowPages.size();i++){
			mStage.addActor(windowPages.get(i));
		}
		windowPages.get(0).setVisible(true);
	}
	
	public interface IntroductionListener{
		public void introductionFinish();
	}
	
	public void setListener(IntroductionListener listener){
		this.introductionListener=listener;
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		super.render();
//		spriteBatch.begin();
//		windowPages.get(currentIndex).renderSelf(spriteBatch);
//		spriteBatch.end();
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		super.update(deltaTime);
	}
	
	
}
