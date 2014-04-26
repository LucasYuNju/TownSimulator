package com.TownSimulator.utility.quadtree;

import com.TownSimulator.utility.AxisAlignedBoundingBox;

public interface QuadTreeManageble {	
	public AxisAlignedBoundingBox getAABBWorld(QuadTreeType type);
	public void dettachQuadTree(QuadTreeType type);
	public void addContainedQuadTreeNode(QuadTreeType type, QuadTreeNode node);
}
