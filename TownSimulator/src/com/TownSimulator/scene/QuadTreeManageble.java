package com.TownSimulator.scene;

import com.TownSimulator.utility.AxisAlignedBoundingBox;

public interface QuadTreeManageble {	
	public AxisAlignedBoundingBox getBoundingBox(QuadTreeType type);
	public void dettachQuadTree(QuadTreeType type);
	public void addContainedQuadTreeNode(QuadTreeType type, QuadTreeNode node);
	//public void removeContainedQuadTreeNode(QuadTreeType type, QuadTreeNode node);
}
