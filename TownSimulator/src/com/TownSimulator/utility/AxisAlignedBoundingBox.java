package com.TownSimulator.utility;

public class AxisAlignedBoundingBox {
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
