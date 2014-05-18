package com.TownSimulator.ai.behaviortree;

import java.util.ArrayList;
import java.util.List;

public abstract class CompositeNode implements BehaviorTreeNode{
	private static final long serialVersionUID = 1L;
	protected List<BehaviorTreeNode> mChildren;
	
	public CompositeNode()
	{
		mChildren = new ArrayList<BehaviorTreeNode>();
	}
	
	public CompositeNode addNode(BehaviorTreeNode node)
	{
		mChildren.add(node);
		return this;
	}
	
	
	
	@Override
	public void destroy() {
		for (BehaviorTreeNode node : mChildren) {
			node.destroy();
		}
	}

	@Override
	abstract public ExecuteResult execute(float deltaTime);

}
