package com.TownSimulator.ai.btnimpls.construct;

import com.TownSimulator.ai.behaviortree.ConditionNode;
import com.TownSimulator.ai.behaviortree.ExcuteResult;
import com.TownSimulator.ai.behaviortree.SelectorNode;
import com.TownSimulator.ai.behaviortree.SequenceNode;
import com.TownSimulator.entity.EntityInfoCollector;
import com.TownSimulator.entity.Man;

public class ConstructionBTN extends SequenceNode{
	private Man		mMan;
	
	public ConstructionBTN(Man man) {
		this.mMan = man;
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
				if(mMan.getInfo().constructionInfo.proj == null)
				{
					ConstructionProject proj = findAvailableProj();
					if(proj == null)
						return ExcuteResult.FALSE;
					else
					{
						proj.addWorker(mMan);
						mMan.getInfo().constructionInfo.proj = proj;
						return ExcuteResult.TRUE;
					}
				}
				else
					return ExcuteResult.TRUE;
			}
		};
		
		ConditionNode judgeTransport = new ConditionNode() {
			
			@Override
			public ExcuteResult execute(float deltaTime) {
				if(mMan.getInfo().constructionInfo.bCancel == false)
				{
					if(mMan.getInfo().constructionInfo.proj.remainResourceToTrans() || mMan.getInfo().constructionInfo.transportNeededAmount > 0)
						return ExcuteResult.TRUE;
					else
						return ExcuteResult.FALSE;
				}
				else
				{
					if(mMan.getInfo().constructionInfo.transportNeededAmount > 0)
						return ExcuteResult.TRUE;
					else
						return ExcuteResult.FALSE;
				}
			}
		};
		
		ConditionNode judgeUnfinish = new ConditionNode() {
			
			@Override
			public ExcuteResult execute(float deltaTime) {
				if(mMan.getInfo().constructionInfo.proj.isFinished() || mMan.getInfo().constructionInfo.bCancel)
					return ExcuteResult.FALSE;
				else
					return ExcuteResult.TRUE;
			}
		};
		
		this.addNode(constructProj)
			.addNode( new SelectorNode().addNode( new SequenceNode().addNode(judgeTransport)
																	.addNode(new ConstructionTransportBTN(mMan)) 
												)
										.addNode( new SequenceNode().addNode(judgeUnfinish)
																	.addNode(new ConstructionExecuteBTN(mMan)) 
												)
					);
		
	}
	
	
	
//	private void startProj()
//	{
//		mMan.getInfo().bIdle = false;
//	}

//	@Override
//	public ExcuteResult execute(float deltaTime) {
//		//if(mMan.getInfo().constructionInfo.proj == null)
//		//	startProj();
//		
//		return super.execute(deltaTime);
//	}

}
