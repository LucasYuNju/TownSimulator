package com.TownSimulator.utility.particles;

import java.util.Random;

import com.TownSimulator.utility.GameMath;
import com.TownSimulator.utility.ResourceManager;
import com.TownSimulator.utility.Settings;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class RandomJetBounceParticleSystem {
	private static final Random rand = new Random(System.nanoTime());
	private static final float SIZE_X = Settings.UNIT * 0.2f;
	private static final float SIZE_Y = Settings.UNIT * 0.2f;
	//private static final float LIFE = 5.0f;
	private static TextureRegion rectTex;
	
	public static void initStatic()
	{
		rectTex = ResourceManager.getInstance(ResourceManager.class).createTextureRegion("particle_rect");
	}
	
	public static void create(Vector2 pos, float jetAngleStart, float jetAngleEnd, float speedMin, float speedMax, Color color, float life, float bounceY, int jetCnt, TextureRegion texture)
	{
		for (int i = 0; i < jetCnt; i++) {
			Vector2 vel = new Vector2();
			float angle = GameMath.lerp(jetAngleStart, jetAngleEnd, rand.nextFloat());
			float speed = GameMath.lerp(speedMin, speedMax, rand.nextFloat());
			vel.x = (float) (Math.cos(angle) * speed);
			vel.y = (float) (Math.sin(angle) * speed);
			BounceParticle.create(pos, vel, new Vector2(Settings.GRAVITY_X, Settings.GRAVITY_Y), new Vector2(SIZE_X, SIZE_Y), color, life, texture, bounceY);
		}
	}
	
	public static void createRectPartlces(Vector2 pos, float jetAngleStart, float jetAngleEnd, float speedMin, float speedMax, Color color, float life, float bounceY, int jetCnt)
	{
		create(pos, jetAngleStart, jetAngleEnd, speedMin, speedMax, color, life, bounceY, jetCnt, rectTex);
	}
	
	public static void createRectPartlces(Vector2 pos, 	float jetAngleStart, float jetAngleEnd,
														float speedMin, float speedMax,
														Color colorLerp0, Color colorLerp1,
														float life, float bounceY, int jetCnt)
	{
		for (int i = 0; i < jetCnt; i++) {
			Color color = new Color();
			float x = rand.nextFloat();
			color.r = GameMath.lerp(colorLerp0.r, colorLerp1.r, x);
			color.g = GameMath.lerp(colorLerp0.g, colorLerp1.g, x);
			color.b = GameMath.lerp(colorLerp0.b, colorLerp1.b, x);
			color.a = GameMath.lerp(colorLerp0.a, colorLerp1.a, x);
			create(pos, jetAngleStart, jetAngleEnd, speedMin, speedMax, color, life, bounceY, 1, rectTex);
		}
		
	}
}
