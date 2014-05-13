package com.TownSimulator.ai.btnimpls.lumerjack;

import com.TownSimulator.ai.behaviortree.ConditionNode;
import com.TownSimulator.ai.behaviortree.ExecuteResult;
import com.TownSimulator.ai.behaviortree.SelectorNode;
import com.TownSimulator.ai.behaviortree.SequenceNode;
import com.TownSimulator.collision.CollisionDetector;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.Tree;
import com.TownSimulator.entity.building.FellingHouse;
import com.TownSimulator.utility.AxisAlignedBoundingBox;
import com.TownSimulator.utility.Settings;
import com.TownSimulator.utility.quadtree.QuadTreeManageble;
import com.badlogic.gdx.utils.Array;


public class FellingBTN extends SequenceNode{
	private Man man;
	FellingInfo fellingInfo;
	
	public FellingBTN(Man man)
	{
		this.man = man;
		fellingInfo = new FellingInfo();
		init();
	}
	
	private Tree findAvailableTree()
	{
		FellingHouse house = (FellingHouse) man.getInfo().workingBuilding;
		AxisAlignedBoundingBox range = new AxisAlignedBoundingBox();
		int originGridX = (int)(house.getPositionXWorld() / Settings.UNIT);
		int originGridY = (int)(house.getPositionYWorld() / Settings.UNIT);
		range.minX = (originGridX - FellingHouse.RANGE) * Settings.UNIT;
		range.minY = (originGridY - FellingHouse.RANGE) * Settings.UNIT;
		range.maxX = (originGridX + FellingHouse.RANGE + 1) * Settings.UNIT;
		range.maxY = (originGridY + FellingHouse.RANGE + 1) * Settings.UNIT;
		Array<QuadTreeManageble> objs = new Array<QuadTreeManageble>();
		CollisionDetector.getInstance(CollisionDetector.class).detect(range, objs);
		for (QuadTreeManageble obj : objs) {
			if(obj instanceof Tree)
			{
				Tree tree = (Tree) obj;
				if(tree.isCutting())
					continue;
				
				if(house.isInRange(tree) && tree.getScale() >= 0.8f)
					return tree;
			}
		}
		return null;
	}
	

	
	private void init()
	{
		ConditionNode judgeCuttingStart = new ConditionNode() {
			
			@Override
			public ExecuteResult execute(float deltaTime) {
				if(fellingInfo.fellingTree == null)
					return ExecuteResult.FALSE;
				else
					return ExecuteResult.TRUE;
			}
		};
		
		ConditionNode findAvailableTree = new ConditionNode() {
			
			@Override
			public ExecuteResult execute(float deltaTime) {
				fellingInfo.fellingTree = findAvailableTree();
				if(fellingInfo.fellingTree == null)
					return ExecuteResult.FALSE;
				else
				{
					fellingInfo.fellingTree.setCutting(true);
					return ExecuteResult.TRUE;
				}
			}
		};
		
		this.addNode( new SelectorNode().addNode(judgeCuttingStart)
										.addNode(findAvailableTree)
					)
			.addNode( new SelectorNode().addNode(new TransportWoodBTN(man, fellingInfo))
										.addNode(new FellingExecuteBTN(man, fellingInfo))
					);
	}
}
