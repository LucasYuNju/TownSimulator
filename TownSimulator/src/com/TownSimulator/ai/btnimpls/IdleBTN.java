package com.TownSimulator.ai.btnimpls;

import com.TownSimulator.ai.behaviortree.ConditionNode;
import com.TownSimulator.ai.behaviortree.ExcuteResult;
import com.TownSimulator.ai.behaviortree.SelectorNode;
import com.TownSimulator.ai.behaviortree.SequenceNode;
import com.TownSimulator.ai.btnimpls.construct.ConstructBTN;
import com.TownSimulator.ai.btnimpls.construct.ConstructProject;
import com.TownSimulator.entity.EntityInfoCollector;
import com.TownSimulator.entity.Man;

public class IdleBTN extends SelectorNode{
	private Man mMan;
	
	public IdleBTN(Man man) {
		this.mMan = man;
		initTree();
	}

	protected void initTree() {
		ConditionNode constructProj = new ConditionNode() {
			
			private ConstructProject findAvailableProj()
			{
				for (ConstructProject proj : EntityInfoCollector.getInstance(EntityInfoCollector.class).getAllConstructProjs()) {
					if(proj.getAvailableBuildJobCnt() > 0)
						return proj;
				}
				
				return null;
			}
			
			@Override
			public ExcuteResult execute(float deltaTime) {
				if(mMan.getInfo().constructProj == null)
				{
					ConstructProject proj = findAvailableProj();
					if(proj == null)
						return ExcuteResult.FALSE;
					else
						proj.addMan(mMan);
					return ExcuteResult.TRUE;
				}
				else
					return ExcuteResult.TRUE;
			}
		};
		
		
		this.addNode( new SequenceNode().addNode(constructProj)
										.addNode(new ConstructBTN(mMan)))
			.addNode(new RandomMoveBTN(mMan));
	}

}
