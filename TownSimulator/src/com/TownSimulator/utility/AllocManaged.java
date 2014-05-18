package com.TownSimulator.utility;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class AllocManaged {
	private static HashMap<Class<? extends AllocManaged>, List<AllocManaged>> allocedMap = new HashMap<Class<? extends AllocManaged>, List<AllocManaged>>();
	private static HashMap<Class<? extends AllocManaged>, List<AllocManaged>> freeMap = new HashMap<Class<? extends AllocManaged>, List<AllocManaged>>();
	
	@SuppressWarnings("unchecked")
	protected static <T extends AllocManaged> T alloc(Class<T> classType)
	{
		if( !freeMap.containsKey(classType) )
		{
			allocedMap.put(classType, new ArrayList<AllocManaged>());
			freeMap.put(classType, new ArrayList<AllocManaged>());
		}
		
		List<AllocManaged> list = freeMap.get(classType);
		if(list.size() <= 0)
		{
			try {
				Constructor<T> constructor = classType.getDeclaredConstructor();
				constructor.setAccessible(true);
				list.add((AllocManaged)constructor.newInstance());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		AllocManaged inst = list.remove(list.size() - 1);
		allocedMap.get(classType).add(inst);
		return (T)inst;
	}
	
	@SuppressWarnings("unchecked")
	protected static <T extends AllocManaged> List<T> getAllocedList(Class<T> classType)
	{
		return (List<T>) allocedMap.get(classType);
	}
	
	@SuppressWarnings("unchecked")
	protected static <T extends AllocManaged> List<T> getFreeList(Class<T> classType)
	{
		return (List<T>) freeMap.get(classType);
	}
	
	public <T extends AllocManaged> void release()
	{
		@SuppressWarnings("unchecked")
		Class<T> classType = getClassType();
		if(allocedMap.get(classType).remove(this))
			freeMap.get(classType).add(this);
	}
	
	@SuppressWarnings("rawtypes")
	abstract protected Class getClassType();
}
