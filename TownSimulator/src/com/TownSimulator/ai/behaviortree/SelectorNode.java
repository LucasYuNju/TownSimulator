package com.TownSimulator.ai.behaviortree;

public class SelectorNode extends CompositeNode{
	
	@Override
	public ExcuteResult execute(float deltaTime) {
		for (BehaviorTreeNode node : mChildren) {
			ExcuteResult r = node.execute(deltaTime);
			if(r != ExcuteResult.FALSE)
				return r;
		}
		return ExcuteResult.FALSE;
	}

}
