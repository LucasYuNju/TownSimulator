package com.TownSimulator.utility.particle;

import java.util.ArrayList;

import com.TownSimulator.camera.CameraController;
import com.TownSimulator.camera.CameraListener;
import com.TownSimulator.driver.Driver;
import com.TownSimulator.driver.DriverListenerBaseImpl;
import com.TownSimulator.entity.SeasonType;
import com.TownSimulator.entity.World;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.Singleton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class ParticleControl extends Singleton{
	public final static float WeatherParticleTimeIntervel=5.0f;
	private float EveryEmitterTimeIntervel=WeatherParticleTimeIntervel/15.0f;
	private float ParticleHeight=Settings.UNIT*0.5f;
	private float ParticleLocationIntervel;
	
	private SpriteBatch batch;
	private ParticleEffect tempEffect;
	private float acountTime;
	private float acountIndex;
	private boolean isRenderWeather;
	
	private float renderStartX=0.0f;
	private float renderStartY=0.0f;
	
	private ParticleProvider waterParticle;
	
	private ParticleProvider snowParticle;
	private ParticleProvider rainParticle;
    private ArrayList<ParticleEffect> weatherParticlelist;
    
	public ParticleControl() {
		batch = new SpriteBatch();
		acountTime = 0.0f;
		acountIndex=0.0f;
		isRenderWeather = false;
		ParticleLocationIntervel=CameraController.getInstance(CameraController.class).getViewPortWidth()/15.0f;
		
		waterParticle=new ParticleProvider(ParticleType.Water);

		snowParticle = new ParticleProvider(ParticleType.Snow);
		rainParticle=new ParticleProvider(ParticleType.Rain);

		weatherParticlelist = new ArrayList<ParticleEffect>();
		
		Driver.getInstance(Driver.class).addListener(new DriverListenerBaseImpl() {

					@Override
					public void update(float deltaTime) {
						// TODO Auto-generated method stub
						acountTime += deltaTime;
						if (acountTime >= EveryEmitterTimeIntervel) {
							acountTime -= EveryEmitterTimeIntervel;
							if (isRenderWeather) {
//								isaddWeatherParticle = true;
								renderStartX=CameraController.getInstance(CameraController.class).getCameraViewAABB().minX;
								renderStartY=CameraController.getInstance(CameraController.class).getCameraViewAABB().maxY;
								acountIndex=(float)Math.random()*20.0f-2.0f;
								addWeatherParticleToList(World.getInstance(World.class).getCurSeason(),acountIndex);
							}
						}

					}

					@Override
					public void dispose() {
						// TODO Auto-generated method stub
						ParticleControl.this.dispose();
					}

				});
		
		CameraController.getInstance(CameraController.class).addListener(new CameraListener() {
			
			@Override
			public void cameraZoomed(float prevWidth, float prevHeight, float curWidth,
					float curHeight) {
				// TODO Auto-generated method stub
				zoomed(curWidth/prevWidth, curHeight/prevHeight);
			}
			
			@Override
			public void cameraMoved(float deltaX, float deltaY) {
				// TODO Auto-generated method stub
				moved(deltaX, deltaY);
			}
		});
	}
	
	public void init(){
		this.isRenderWeather=true;
	}
	
	public void moved(float deltaX, float deltaY){
		for(int i=0;i<weatherParticlelist.size();i++){
			float x=weatherParticlelist.get(i).getEmitters().get(0).getX();
			float y=weatherParticlelist.get(i).getEmitters().get(0).getY();
			weatherParticlelist.get(i).setPosition(x+deltaX, y+deltaY);
		}
	}
	
	public void zoomed(float widthScale,float heightScale){
		this.ParticleHeight*=heightScale;
		this.ParticleLocationIntervel*=heightScale;
	}

	public void reset(){
		this.acountTime=WeatherParticleTimeIntervel;
		clearWeatherList();
	}
    
	public void clearWeatherList(){
		for(int i=0;i<weatherParticlelist.size();i++){
			weatherParticlelist.get(i).allowCompletion();
			weatherParticlelist.get(i).dispose();
		}
	}
	
	public void render() {
		if(!isRenderWeather){
			return;
		}
//		Matrix4 matrix4=new Matrix4();
//		matrix4.scale(0.5f, 0.5f, 1);
//		batch.setTransformMatrix(matrix4);
		batch.setProjectionMatrix(CameraController.getInstance(CameraController.class).getCameraCombined());
		batch.begin();
		for (int i = 0; i < weatherParticlelist.size(); i++) {
			weatherParticlelist.get(i).draw(batch, Gdx.graphics.getDeltaTime());
		}
		batch.end();
		clearIsComplete();
	}
	
	public void addWeatherParticleToList(SeasonType seasonType, float index) {
		float tempLocation=0.0f;
		Array<ParticleEmitter> particleEffectters;
		switch (seasonType) {
		case Winter:
			tempLocation = (index + (float) Math.random())* ParticleLocationIntervel;
			tempEffect = snowParticle.getParticleEffect();
			tempEffect.setPosition(renderStartX+tempLocation, renderStartY);
			particleEffectters=tempEffect.getEmitters();
			for(int i=0;i<particleEffectters.size;i++){
				particleEffectters.get(i).getScale().setHigh(ParticleHeight);
			}
			weatherParticlelist.add(tempEffect);
			break;
		case Summer:
			tempLocation = (index + (float) Math.random())* ParticleLocationIntervel;
			tempEffect = rainParticle.getParticleEffect();
			tempEffect.setPosition(renderStartX+tempLocation, renderStartY);
			particleEffectters=tempEffect.getEmitters();
			for(int i=0;i<particleEffectters.size;i++){
				particleEffectters.get(i).getScale().setHigh(ParticleHeight);
			}
			weatherParticlelist.add(tempEffect);
//			tempEffect=waterParticle.getParticleEffect();
//			float x=CameraController.getInstance(CameraController.class).getViewPortWidth();
//			float y=CameraController.getInstance(CameraController.class).getViewPortHeight();
//			tempEffect.setPosition(renderStartX+(float)Math.random()*x, renderStartY-(float)Math.random()*y);
//			particleEffectters=tempEffect.getEmitters();
//			for(int i=0;i<particleEffectters.size;i++){
//				particleEffectters.get(i).getScale().setHigh(ParticleHeight*0.6f);
//			}
//			weatherParticlelist.add(tempEffect);
			break;
		default:
			break;
		}
	}
	
	/**
	 * 清除已经播放完成的粒子系统
	 */
	public void clearIsComplete(){
		ParticleEffect temparticle;
		for (int i = 0; i < weatherParticlelist.size(); i++) {
			temparticle = weatherParticlelist.get(i);
			if (temparticle.isComplete()) {
//				float x=temparticle.getBoundingBox().min.x;
//				float y=temparticle.getBoundingBox().min.y;
//				ParticleEffect waterParti = waterParticle.getParticleEffect();
//				waterParti.setPosition(x, y);
//				System.out.println("x:"+x+"y:"+y);
//				weatherParticlelist.add(waterParti);
				if(temparticle!=null){
					temparticle.dispose();
				}
				weatherParticlelist.remove(i);
			}
		}
	}
    
    public void dispose(){
		batch.dispose();
		
		waterParticle.dispose();
		snowParticle.dispose();
		rainParticle.dispose();
		
		clearIsComplete();
		
		for(int i=0;i<weatherParticlelist.size();i++){
			weatherParticlelist.get(i).dispose();
		}
    }
    
    public void setIsRenderWeather(boolean isrenderweather){
    	this.isRenderWeather=isrenderweather;
    }
}
