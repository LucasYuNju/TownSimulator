package com.townSimulator.utility;

public class AxisAlignedBoundingBox {
	public float minX;
	public float minY;
	public float maxX;
	public float maxY;
	
	public AxisAlignedBoundingBox()
	{
	}
	
	public AxisAlignedBoundingBox( float minX, float minY, float maxX, float maxY )
	{
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
	}
}
