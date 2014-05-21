package com.TownSimulator.utility.particle;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;


public class ParticleProvider{
    
    private ParticleEffect particle; 
    private ParticleEffect tem; 
    private ParticleEffectPool particlepool; 
    private float xPositionWorld;
    private float yPositionWorld;
    
    public ParticleProvider(ParticleType particleType){
    	particle = new ParticleEffect();  
    	particle.load(Gdx.files.internal("particle/"+particleType.getFileName()), Gdx.files.internal("particle"));  
    	particlepool=new ParticleEffectPool(particle, 5, 30); 
    }
    
    public ParticleEffect getParticleEffect(){
    	return particlepool.obtain();
    }
    
    
    public void setPositionWorld(float x,float y){
    	this.xPositionWorld=x;
    	this.yPositionWorld=y;
    }
    
    public void dispose(){
    	particle.dispose();
        if(tem!=null)
            tem.dispose();
        particlepool.clear();
    }

}
