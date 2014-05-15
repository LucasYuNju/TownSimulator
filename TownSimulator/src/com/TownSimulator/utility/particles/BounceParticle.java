package com.TownSimulator.utility.particles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class BounceParticle extends Particle{
	
	static
	{
		ParticleManager.register(BounceParticle.class);
	}
	
	public static BounceParticle create(	Vector2 pos, Vector2 velocity, Vector2 accelaration,
											Vector2 size, Color color, float life, TextureRegion texture,
											float bounceY)
	{
		BounceParticle particle = alloc(BounceParticle.class);
		Particle.init(particle, pos, velocity, accelaration, size, color, life, texture);
		BounceParticle.init(particle, bounceY);
		
		return particle;
	}
	
	protected static void init(BounceParticle particle, float bounceY)
	{
		particle.bounceY = bounceY;
	}
	
	protected float bounceY; 
	private static final float BOUNCE_SPEED_OFF = 0.5f;
	
	private BounceParticle()
	{
		
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		if(pos.y <= bounceY)
		{
			pos.y = bounceY;
			velocity.x = velocity.x * BOUNCE_SPEED_OFF;
			velocity.y = -velocity.y * BOUNCE_SPEED_OFF;
		}
	}

	@Override
	protected Class<? extends BounceParticle> getClassType() {
		return BounceParticle.class;
	}
	
	
}
