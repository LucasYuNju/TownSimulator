package com.TownSimulator.entity;

public class Resource {
	private ResourceType type;
	private int amount;
	private int neededAmount;
	
	public Resource(ResourceType type) {
		this(type, 0, 0);
	}
	
	//plain resource
	public Resource(ResourceType type, int amount) {
		this(type, amount, 0);
	}
	
	//building resource
	public Resource(ResourceType type, int amount, int needAmount) {
		this.type = type;
		this.amount = amount;
		this.neededAmount = needAmount;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public ResourceType getType() {
		return type;
	}
	
	public int getNeededAmount() {
		return neededAmount;
	}
	
	public void setNeededAmount(int neededAmount) {
		this.neededAmount = neededAmount;
	}
	
	public void addAmount(int increment) {
		amount += increment;
	}
	
	public boolean isSufficient() {
		if(amount >= neededAmount)
			return true;
		return false;
	}
	
	@Override
	public int hashCode() {
		return type.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		Resource resource = (Resource)obj;
		if(resource.getType() == getType())
			return true;
		return false;
	}
}
