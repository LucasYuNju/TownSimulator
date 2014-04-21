package com.townSimulator.utility;

import com.townSimulator.game.AxisAlignedBoundingBox;

public class GameMath {
	public static float lerp(float v0, float v1, float x)
	{
		return (1.0f - x) * v0 + x * v1;
	}
	
	public static boolean intersect(AxisAlignedBoundingBox aabb0, AxisAlignedBoundingBox aabb1)
	{
		if( 	aabb0.maxX < aabb1.minX || aabb1.maxX < aabb0.minX
			||	aabb0.maxY < aabb1.minY || aabb1.maxY < aabb0.minY )
			return false;
		else
			return true;
	}
	
	public static boolean intersect(AxisAlignedBoundingBox aabb, float x, float y)
	{
		if( 	x < aabb.minX || x > aabb.maxX 
			||	y < aabb.minY || y > aabb.maxY )
			return false;
		else
			return true;
	}
}
