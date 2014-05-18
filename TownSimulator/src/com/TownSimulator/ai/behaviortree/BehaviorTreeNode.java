package com.TownSimulator.ai.behaviortree;

import java.io.Serializable;

public interface BehaviorTreeNode extends Serializable{
	public ExecuteResult execute(float deltaTime);
	
	public void destroy();
	
}
