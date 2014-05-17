package com.TownSimulator.utility.particles;

import com.TownSimulator.driver.Driver;
import com.TownSimulator.driver.DriverListenerBaseImpl;
import com.TownSimulator.render.Renderer;
import com.TownSimulator.render.RendererListener;
import com.TownSimulator.utility.AllocManaged;
import com.badlogic.gdx.utils.Array;

public class ParticleManager extends AllocManaged{
	private static Array<Class<? extends Particle>> classTypes = new Array<Class<? extends Particle>>();

	@Override
	protected Class<ParticleManager> getClassType() {
		return ParticleManager.class;
	}
	
	public static void init()
	{
		Particle.init();
		BounceParticle.init();
		RandomJetBounceParticleSystem.init();
		
		Driver.getInstance(Driver.class).addListener(new DriverListenerBaseImpl()
		{

			@Override
			public void update(float deltaTime) {
				for (Class<? extends Particle> c : classTypes) {
					if(getAllocedList(c) != null)
						for (Particle p : getAllocedList(c)) {
							p.update(deltaTime);
						}
				}
			}

			@Override
			public void dispose() {
				classTypes.clear();
			}
			
		});
		
		Renderer.getInstance(Renderer.class).addListener(new RendererListener() {
			
			@Override
			public void renderEnded() {
			}
			
			@Override
			public void renderBegined() {
				for (Class<? extends Particle> c : classTypes) {
					if(getAllocedList(c) != null)
						for (Particle p : getAllocedList(c)) {
							Renderer.getInstance(Renderer.class).draw(p);
						}
				}
			}
		});
	}
	
	public static <T extends Particle> void register(Class<T> classType)
	{
		classTypes.add(classType);
	}
}
