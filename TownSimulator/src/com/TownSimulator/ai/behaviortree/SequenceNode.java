package com.TownSimulator.ai.behaviortree;

public class SequenceNode extends CompositeNode{

	@Override
	public ExecuteResult execute(float deltaTime) {
		for (BehaviorTreeNode node : mChildren) {
			ExecuteResult r = node.execute(deltaTime);
			if(r != ExecuteResult.TRUE)
				return r;
		}
		return ExecuteResult.TRUE;
	}

}
