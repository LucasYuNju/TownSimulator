package com.TownSimulator.ai.btnimpls.doctor;

import com.TownSimulator.ai.behaviortree.SelectorNode;
import com.TownSimulator.ai.btnimpls.general.GeneralBTN;
import com.TownSimulator.entity.Man;

public class DoctorNode extends SelectorNode{
	private static final long serialVersionUID = 1L;
	public DoctorNode(Man man) {
		addNode(new GeneralBTN(man));
		addNode(new MoveToHospitalNode(man));
	}
}
