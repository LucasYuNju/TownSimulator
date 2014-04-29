package com.TownSimulator.ai.btnimpls.construct;

import com.TownSimulator.ai.behaviortree.ConditionNode;
import com.TownSimulator.ai.behaviortree.ExcuteResult;
import com.TownSimulator.ai.behaviortree.SelectorNode;
import com.TownSimulator.ai.behaviortree.SequenceNode;
import com.TownSimulator.entity.Man;

public class ConstructBTN extends SelectorNode{
	private ConstructInfo		mConstructInfo;
	
	public ConstructBTN(Man man) {
		mConstructInfo = new ConstructInfo();
		mConstructInfo.man = man;
		init();
	}
	
	private void init()
	{
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
		
		this.addNode( new SequenceNode().addNode(judgeTransport)
										.addNode(new ConstructTransportBTN(mConstructInfo)) )
			.addNode( new SequenceNode().addNode(judgeUnfinish)
										.addNode(new ConstructExecuteBTN(mConstructInfo)) );
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
