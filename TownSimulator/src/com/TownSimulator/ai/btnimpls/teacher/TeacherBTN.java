package com.TownSimulator.ai.btnimpls.teacher;

import com.TownSimulator.ai.behaviortree.SelectorNode;
import com.TownSimulator.ai.btnimpls.general.GeneralBTN;
import com.TownSimulator.entity.Man;

public class TeacherBTN extends SelectorNode{
	private Man man;
	
	public TeacherBTN(Man man){
		this.man=man;
		init();
	}

	public void init(){
		this.addNode(new GeneralBTN(man))
		    .addNode(new TeachworkBTN(man));
	}

}
