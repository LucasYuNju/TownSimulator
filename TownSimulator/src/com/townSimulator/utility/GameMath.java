package com.townSimulator.utility;

public class GameMath {
	public static float lerp(float v0, float v1, float x)
	{
		return (1.0f - x) * v0 + x * v1;
	}
}
