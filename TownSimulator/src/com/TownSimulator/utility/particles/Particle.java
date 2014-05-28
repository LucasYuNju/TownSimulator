package com.TownSimulator.utility.particles;

import com.TownSimulator.render.Drawable;
import com.TownSimulator.utility.AllocManaged;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Particle extends AllocManaged implements Drawable{
//	private static Array<Particle> particlesAlloced = new Array<Particle>();
//	private static Array<Particle> particlesFree = new Array<Particle>();
	
//	private static Particle alloc()
//	{
//		if(particlesFree.size <= 0)
//			particlesFree.add(new Particle());
//		
//		Particle particle = particlesFree.pop();
//		particlesAlloced.add(particle);
//		return particle;
//	}
//	static
//	{
//		Driver.getInstance(Driver.class).addListener(new DriverListenerBaseImpl()
//		{
//
//			@Override
//			public void update(float deltaTime) {
//				for (Particle p : getAllocedList(Particle.class)) {
//					p.update(deltaTime);
//				}
//			}
//			
//		});
//		
//		Renderer.getInstance(Renderer.class).addListener(new RendererListener() {
//			
//			@Override
//			public void renderEnded() {
//			}
//			
//			@Override
//			public void renderBegined() {
//				for (Particle p : getAllocedList(Particle.class)) {
//					Renderer.getInstance(Renderer.class).draw(p);
//				}
//			}
//		});
//	}
	
	public static void initStatic()
	{
		ParticleManager.register(Particle.class);
	}
	
	public static Particle create(Vector2 pos, Vector2 velocity, Vector2 accelaration, Vector2 size, Color color, float life, TextureRegion texture)
	{
		Particle particle = alloc(Particle.class);
//		particle.pos = pos;
//		particle.velocity = velocity;
//		particle.acceleration = accelaration;
//		particle.size = size;
//		particle.color.r = color.r;
//		particle.color.g = color.g;
//		particle.color.b = color.b;
//		particle.life = life;
//		particle.texture = texture;
		init(particle, pos, velocity, accelaration, size, color, life, texture);
		
		return particle;
	}
	
	protected static void init(Particle particle, Vector2 pos, Vector2 velocity, Vector2 accelaration, Vector2 size, Color color, float life, TextureRegion texture)
	{
		particle.pos = pos.cpy();
		particle.velocity = velocity.cpy();
		particle.acceleration = accelaration.cpy();
		particle.size = size.cpy();
		particle.color = color.cpy();
		particle.life = life;
		particle.texture = texture;
	}
	
	protected Vector2 pos;
	protected Vector2 velocity;
	protected Vector2 acceleration;
	protected Vector2 size;
	protected Color	color;
	protected float	life;
	protected TextureRegion texture;
	protected float	timeLived;
	
	public Particle()
	{
		pos = new Vector2();
		velocity = new Vector2();
		acceleration = new Vector2();
		size = new Vector2();
		
		color = new Color(Color.WHITE);
	}
	
	public void update(float deltaTime)
	{
		pos.mulAdd(velocity, deltaTime);
		velocity.mulAdd(acceleration, deltaTime);
		timeLived += deltaTime;
		
		if(timeLived >= life)
		{
			timeLived = 0.0f;
			release();
		}
	}

	
	@Override
	public void drawSelf(SpriteBatch batch) {
		if(texture != null)
		{
			batch.setColor(color);
			float halfX = size.x * 0.5f;
			float halfY = size.y * 0.5f;
			batch.draw(texture, -halfX + pos.x, -halfY + pos.y, size.x, size.y);
		}
	}

	@Override
	public float getDepth() {
		return pos.y;
	}

	@Override
	protected Class<? extends Particle> getClassType() {
		return Particle.class;
	}
	
}
