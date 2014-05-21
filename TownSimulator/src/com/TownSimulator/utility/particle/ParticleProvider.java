package com.TownSimulator.utility.particle;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;


public class ParticleProvider{
    
    ParticleEffect particle; 
    ParticleEffect tem; 
    ParticleEffectPool particlepool;  
    
    public ParticleProvider(ParticleType particleType){
    	particle = new ParticleEffect();      
    	particle.load(Gdx.files.internal("particle/"+particleType.getFileName()), Gdx.files.internal("particle"));  
    	particlepool=new ParticleEffectPool(particle, 5, 30); 
    }
    
    public ParticleEffect getParticleEffect(){
    	return particlepool.obtain();
    }
    
//	public void render() {
//		if (Gdx.input.isTouched()) {
//			tem = snowParticlepool.obtain();
//			tem.setPosition(Gdx.input.getX(), Gdx.graphics.getHeight()
//					- Gdx.input.getY());
//			snowParticlelist.add(tem);
//		}
//	}
    
    public void dispose(){
    	particle.dispose();
        if(tem!=null)
            tem.dispose();
        particlepool.clear();
    }

}
