package com.TownSimulator.utility.particle;

import java.util.ArrayList;

import com.TownSimulator.driver.Driver;
import com.TownSimulator.driver.DriverListenerBaseImpl;
import com.TownSimulator.entity.SeasonType;
import com.TownSimulator.entity.World;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.Singleton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;

public class ParticleControl extends Singleton{
	public final static float weatherParticleIntervel=5.0f;
	private float everyEmitterIntervel=weatherParticleIntervel/15.0f;
	private SpriteBatch batch;
	private ParticleEffect tempEffect;
	private float acountTime;
	private float acountIndex;
	private boolean isRenderWeather;
	
	private ParticleProvider snowParticle;
	private ParticleProvider rainParticle;
    private ArrayList<ParticleEffect> weatherParticlelist;
    
	public ParticleControl() {
		batch = new SpriteBatch();
		acountTime = 0.0f;
		acountIndex=0.0f;
		isRenderWeather = false;

		snowParticle = new ParticleProvider(ParticleType.Snow);
		rainParticle=new ParticleProvider(ParticleType.Rain);

		weatherParticlelist = new ArrayList<ParticleEffect>();
		
		Driver.getInstance(Driver.class).addListener(new DriverListenerBaseImpl() {

					@Override
					public void update(float deltaTime) {
						// TODO Auto-generated method stub
						acountTime += deltaTime;
						if (acountTime >= everyEmitterIntervel) {
							acountTime -= everyEmitterIntervel;
							if (isRenderWeather) {
//								isaddWeatherParticle = true;
								addWeatherParticleToList(World.getInstance(World.class).getCurSeason(),acountIndex);
							}
							acountIndex++;
							if(acountIndex>=15.0){
								acountIndex=0.0f;
							}
						}

					}

					@Override
					public void dispose() {
						// TODO Auto-generated method stub
						ParticleControl.this.dispose();
					}

				});
	}
	
	public void init(){
		this.isRenderWeather=true;
	}
	
	public void reset(){
		this.acountTime=weatherParticleIntervel;
		clearWeatherList();
	}
    
	public void clearWeatherList(){
		for(int i=0;i<weatherParticlelist.size();i++){
			weatherParticlelist.get(i).dispose();
		}
	}
	
	public void render() {
		batch.begin();
		for (int i = 0; i < weatherParticlelist.size(); i++) {
			weatherParticlelist.get(i).draw(batch, Gdx.graphics.getDeltaTime());
		}
		batch.end();
		clearIsComplete();
	}
	
	public void addWeatherParticleToList(SeasonType seasonType, float index) {
		float tempLocation=0.0f;
		switch (seasonType) {
		case Winter:
			tempLocation = (index + (float) Math.random())* Settings.UNIT;
			tempEffect = snowParticle.getParticleEffect();
			tempEffect.setPosition(tempLocation, Gdx.graphics.getHeight());
			weatherParticlelist.add(tempEffect);
			break;
		case Summer:
			tempLocation = (index + (float) Math.random())* Settings.UNIT;
			tempEffect = rainParticle.getParticleEffect();
			tempEffect.setPosition(tempLocation, Gdx.graphics.getHeight());
			weatherParticlelist.add(tempEffect);
			break;
		default:
			break;
		}
		// this.isaddWeatherParticle=false;
	}
	
	/**
	 * 清除已经播放完成的粒子系统
	 */
	public void clearIsComplete(){
		ParticleEffect temparticle;
		for (int i = 0; i < weatherParticlelist.size(); i++) {
			temparticle = weatherParticlelist.get(i);
			if (temparticle.isComplete()) {
				if(temparticle!=null){
					temparticle.dispose();
				}
				weatherParticlelist.remove(i);
			}
		}
	}
    
    public void dispose(){
		batch.dispose();
		
		snowParticle.dispose();
		
		clearIsComplete();
		
		for(int i=0;i<weatherParticlelist.size();i++){
			weatherParticlelist.get(i).dispose();
		}
    }
    
    public void setIsRenderWeather(boolean isrenderweather){
    	this.isRenderWeather=isrenderweather;
    }
}
