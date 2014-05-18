package com.TownSimulator.ai.btnimpls.construct;

import com.TownSimulator.ai.behaviortree.ConditionNode;
import com.TownSimulator.ai.behaviortree.ExecuteResult;
import com.TownSimulator.ai.behaviortree.SelectorNode;
import com.TownSimulator.ai.behaviortree.SequenceNode;
import com.TownSimulator.entity.EntityInfoCollector;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.ManInfo;

public class ConstructionBTN extends SequenceNode{
	private static final long serialVersionUID = 1L;
	private Man		mMan;
	
	public ConstructionBTN(Man man) {
		this.mMan = man;
		init();
	}
	
	private void init()
	{
		ConditionNode judgeAge = new ConditionNode() {
			
			@Override
			public ExecuteResult execute(float deltaTime) {
				if(mMan.getInfo().getAge() >= ManInfo.ADULT_AGE)
					return ExecuteResult.TRUE;
				else
					return ExecuteResult.FALSE;
			}
		};
		
		ConditionNode constructProj = new ConditionNode() {
			private static final long serialVersionUID = 1L;
			private ConstructionProject findAvailableProj()
			{
				for (ConstructionProject proj : EntityInfoCollector.getInstance(EntityInfoCollector.class).getAllConstructProjs()) {
					if(proj.getAvailableBuildJobCnt() > 0)
						return proj;
				}
				
				return null;
			}
			
			@Override
			public ExecuteResult execute(float deltaTime) {
				if(mMan.getInfo().constructionInfo.proj == null)
				{
					ConstructionProject proj = findAvailableProj();
					if(proj == null)
						return ExecuteResult.FALSE;
					else
					{
						proj.addWorker(mMan);
						mMan.getInfo().constructionInfo.proj = proj;
						return ExecuteResult.TRUE;
					}
				}
				else
					return ExecuteResult.TRUE;
			}
		};
		
		ConditionNode judgeTransport = new ConditionNode() {
			private static final long serialVersionUID = 1L;

			@Override
			public ExecuteResult execute(float deltaTime) {
				if(mMan.getInfo().constructionInfo.bCancel == false)
				{
					if(mMan.getInfo().constructionInfo.proj.remainResourceToTrans() || mMan.getInfo().constructionInfo.transportNeededAmount > 0)
						return ExecuteResult.TRUE;
					else
						return ExecuteResult.FALSE;
				}
				else
				{
					if(mMan.getInfo().constructionInfo.transportNeededAmount > 0)
						return ExecuteResult.TRUE;
					else
						return ExecuteResult.FALSE;
				}
			}
		};
		
		ConditionNode judgeUnfinish = new ConditionNode() {
			private static final long serialVersionUID = 1L;
	
			@Override
			public ExecuteResult execute(float deltaTime) {
				if(mMan.getInfo().constructionInfo.proj.isFinished() || mMan.getInfo().constructionInfo.bCancel)
					return ExecuteResult.FALSE;
				else
					return ExecuteResult.TRUE;
			}
		};
		
		this.addNode(judgeAge)
			.addNode(constructProj)
			.addNode( new SelectorNode().addNode( new SequenceNode().addNode(judgeTransport)
																	.addNode(new ConstructionTransportBTN(mMan)) 
												)
										.addNode( new SequenceNode().addNode(judgeUnfinish)
																	.addNode(new ConstructionExecuteBTN(mMan)) 
												)
					);
		
	}

}
