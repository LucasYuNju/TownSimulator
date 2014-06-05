package com.TownSimulator.utility;

import java.io.Serializable;

public class AxisAlignedBoundingBox implements Serializable{
	private static final long serialVersionUID = 2221515927091902881L;
	public float minX;
	public float minY;
	public float maxX;
	public float maxY;
	
	public AxisAlignedBoundingBox()
	{
		minX = 0.0f;
		minY = 0.0f;
		maxX = 0.0f;
		maxY = 0.0f;
	}
	
	public AxisAlignedBoundingBox( float minX, float minY, float maxX, float maxY )
	{
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
	}
	
	public AxisAlignedBoundingBox cpy()
	{
		AxisAlignedBoundingBox cpy = new AxisAlignedBoundingBox();
		cpy.minX = minX;
		cpy.minY = minY;
		cpy.maxX = maxX;
		cpy.maxY = maxY;
		return cpy;
	}
	
	public void union(AxisAlignedBoundingBox aabb)
	{
		minX = Math.min(minX, aabb.minX);
		minY = Math.min(minY, aabb.minY);
		maxX = Math.max(maxX, aabb.maxX);
		maxY = Math.max(maxY, aabb.maxY);
	}
	
	public float getCenterX()
	{
		return (minX + maxX) * 0.5f;
	}
	
	public float getCenterY()
	{
		return (minY + maxY) * 0.5f;
	}
	
	public float getWidth()
	{
		return maxX - minX;
	}
	
	public float getHeight()
	{
		return maxY - minY;
	}
}
