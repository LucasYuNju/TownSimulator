package com.TownSimulator.ai.btnimpls.construct;

import com.TownSimulator.ai.behaviortree.ConditionNode;
import com.TownSimulator.ai.behaviortree.ExcuteResult;
import com.TownSimulator.ai.behaviortree.SelectorNode;
import com.TownSimulator.ai.behaviortree.SequenceNode;
import com.TownSimulator.entity.EntityInfoCollector;
import com.TownSimulator.entity.Man;

public class ConstructionBTN extends SequenceNode{
	private ConstructionInfo		mConstructInfo;
	
	public ConstructionBTN(Man man) {
		mConstructInfo = new ConstructionInfo();
		mConstructInfo.man = man;
		init();
	}
	
	private void init()
	{
		ConditionNode constructProj = new ConditionNode() {
			
			private ConstructionProject findAvailableProj()
			{
				for (ConstructionProject proj : EntityInfoCollector.getInstance(EntityInfoCollector.class).getAllConstructProjs()) {
					if(proj.getAvailableBuildJobCnt() > 0)
						return proj;
				}
				
				return null;
			}
			
			@Override
			public ExcuteResult execute(float deltaTime) {
				if(mConstructInfo.proj == null)
				{
					ConstructionProject proj = findAvailableProj();
					if(proj == null)
						return ExcuteResult.FALSE;
					else
					{
						proj.addMan(mConstructInfo.man);
						mConstructInfo.proj = proj;
					}
					return ExcuteResult.TRUE;
				}
				else
					return ExcuteResult.TRUE;
			}
		};
		
		ConditionNode judgeTransport = new ConditionNode() {
			
			@Override
			public ExcuteResult execute(float deltaTime) {
				if(mConstructInfo.proj.remainResourceToTrans() || mConstructInfo.transportNeededAmount > 0)
					return ExcuteResult.TRUE;
				else
					return ExcuteResult.FALSE;
			}
		};
		
		ConditionNode judgeUnfinish = new ConditionNode() {
			
			@Override
			public ExcuteResult execute(float deltaTime) {
				if(mConstructInfo.proj.isFinished())
					return ExcuteResult.FALSE;
				else
					return ExcuteResult.TRUE;
			}
		};
		
		this.addNode(constructProj)
			.addNode( new SelectorNode().addNode( new SequenceNode().addNode(judgeTransport)
																	.addNode(new ConstructionTransportBTN(mConstructInfo)) 
												)
										.addNode( new SequenceNode().addNode(judgeUnfinish)
																	.addNode(new ConstructionExecuteBTN(mConstructInfo)) 
												)
					);
		
	}
	
	
	
	private void startProj()
	{
		mConstructInfo.proj = mConstructInfo.man.getInfo().constructProj;
		mConstructInfo.man.getInfo().bIdle = false;
	}

	@Override
	public ExcuteResult execute(float deltaTime) {
		if(mConstructInfo.proj == null)
			startProj();
		
		return super.execute(deltaTime);
	}

}
