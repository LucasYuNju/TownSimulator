package com.TownSimulator.ai.behaviortree;

public interface BehaviorTreeNode {
	public ExecuteResult execute(float deltaTime);
	
	public void destroy();
	
}
