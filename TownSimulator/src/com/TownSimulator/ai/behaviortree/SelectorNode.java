package com.TownSimulator.ai.behaviortree;

public class SelectorNode extends CompositeNode{
	
	@Override
	public ExecuteResult execute(float deltaTime) {
		for (BehaviorTreeNode node : mChildren) {
			ExecuteResult r = node.execute(deltaTime);
			if(r != ExecuteResult.FALSE)
				return r;
		}
		return ExecuteResult.FALSE;
	}

}
