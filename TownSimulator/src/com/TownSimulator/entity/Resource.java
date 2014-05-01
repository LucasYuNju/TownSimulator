package com.TownSimulator.entity;

/**
 * 重写了equals(Object object)和toString()方法，以简化collection操作
 * 为了判断collection是否包含某个类型的Resource，可以写成collection.contains(new Resource(desiredType))
 * 
 */
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
		if(obj instanceof Resource)
		{
			Resource resource = (Resource)obj;
			if(resource.getType() == getType())
				return true;
		}
		else if(obj instanceof ResourceType)
		{
			if(getType() == (ResourceType)obj)
				return true;
		}
		
		return false;
	}
}
