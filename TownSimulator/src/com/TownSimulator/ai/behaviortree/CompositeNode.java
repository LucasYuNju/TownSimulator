package com.TownSimulator.ai.behaviortree;

import com.badlogic.gdx.utils.Array;

public abstract class CompositeNode implements BehaviorTreeNode{
	protected Array<BehaviorTreeNode> mChildren;
	
	public CompositeNode()
	{
		mChildren = new Array<BehaviorTreeNode>();
	}
	
	public CompositeNode addNode(BehaviorTreeNode node)
	{
		mChildren.add(node);
		return this;
	}
	
	@Override
	abstract public ExecuteResult execute(float deltaTime);

}
