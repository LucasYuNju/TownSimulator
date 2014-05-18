package com.TownSimulator.ai.btnimpls.general;

import java.util.List;

import com.TownSimulator.ai.behaviortree.ActionNode;
import com.TownSimulator.ai.behaviortree.ExecuteResult;
import com.TownSimulator.ai.behaviortree.SequenceNode;
import com.TownSimulator.entity.EntityInfoCollector;
import com.TownSimulator.entity.Man;
import com.TownSimulator.entity.ManAnimeType;
import com.TownSimulator.entity.building.Building;
import com.TownSimulator.entity.building.BuildingType;
import com.TownSimulator.entity.building.Hospital;
import com.TownSimulator.utility.Singleton;
import com.TownSimulator.utility.quadtree.QuadTreeType;

public class SeeADoctorBTN extends SequenceNode{
	private static final long serialVersionUID = 7800595047323124796L;
	private Man man;
	
	public SeeADoctorBTN(Man man) {
		this.man = man;
		initSubNodes();
	}

	private void initSubNodes() {		
		ActionNode seeADoctorNode = new ActionNode() {

			private static final long serialVersionUID = -6422737239494273816L;

			@Override
			public ExecuteResult execute(float deltaTime) {
				Hospital hospital;
				if((hospital=getAdmittedHospital()) != null) {
					if(!man.getInfo().isHealthy()) {
						if(hospital.getCurWorkerCnt() > 0) {
							man.getInfo().receiveTreatment(deltaTime);
							hospital.updateHospitalViewWindow();
						}
						return ExecuteResult.RUNNING;
					}
					else {
						getAdmittedHospital().removePatient(man);
						return ExecuteResult.FALSE;
					}
				}
				if (man.getInfo().isSick()) {
					if((hospital=getEmptyHospital()) != null) {
						float destX = hospital.getAABBWorld(QuadTreeType.COLLISION).getCenterX();
						float destY = hospital.getAABBWorld(QuadTreeType.COLLISION).getCenterY();
						man.setMoveDestination(destX, destY);
						if( !man.move(deltaTime) )
						{
							man.getInfo().animeType = ManAnimeType.STANDING;
							hospital.addPatient(man);
						}
						else
							man.getInfo().animeType = ManAnimeType.MOVE;

						return ExecuteResult.TRUE;
					}
				}
				return ExecuteResult.FALSE;
			}

		};
		addNode(seeADoctorNode);
	}
	
	
	private Hospital getEmptyHospital() {
		List<Building> hospitals = Singleton.getInstance(EntityInfoCollector.class).getBuildings(BuildingType.Hospital);
		for(Building building : hospitals) {
			Hospital hospital = (Hospital) building;
			if(hospital.hasCapacity())
				return hospital;
		}
		return null;
	}
	
	/**
	 * @return man所在的医院
	 * 	return null 如果man不属于任何医院
	 */
	private Hospital getAdmittedHospital() {
		List<Building> hospitals = Singleton.getInstance(EntityInfoCollector.class).getBuildings(BuildingType.Hospital);
		for(Building building : hospitals) {
			Hospital hospital = (Hospital) building;
			if(hospital.containsPatient(man))
				return hospital;
		}		
		return null;
	}
}
