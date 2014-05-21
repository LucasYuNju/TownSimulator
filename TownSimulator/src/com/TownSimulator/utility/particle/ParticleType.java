package com.TownSimulator.utility.particle;

public enum ParticleType {
	Snow("snow.p"),
	Rain("rain.p");
	
	private String particleFileName;
	
	private ParticleType(String particleFileName){
		this.particleFileName=particleFileName;
	}
	public String getFileName(){
		return this.particleFileName;
	}

}
