package com.TownSimulator.ai.behaviortree;

public class SequenceNode extends CompositeNode{

	@Override
	public ExcuteResult execute(float deltaTime) {
		for (BehaviorTreeNode node : mChildren) {
			ExcuteResult r = node.execute(deltaTime);
			if(r != ExcuteResult.TRUE)
				return r;
		}
		return ExcuteResult.TRUE;
	}

}
