package com.TownSimulator.utility;

import java.lang.reflect.Constructor;
import java.util.HashMap;

import com.badlogic.gdx.utils.Array;

public abstract class AllocManaged {
	private static HashMap<Class<? extends AllocManaged>, Array<AllocManaged>> allocedMap = new HashMap<Class<? extends AllocManaged>, Array<AllocManaged>>();
	private static HashMap<Class<? extends AllocManaged>, Array<AllocManaged>> freeMap = new HashMap<Class<? extends AllocManaged>, Array<AllocManaged>>();
	
	@SuppressWarnings("unchecked")
	protected static <T extends AllocManaged> T alloc(Class<T> classType)
	{
		if( !freeMap.containsKey(classType) )
		{
			allocedMap.put(classType, new Array<AllocManaged>());
			freeMap.put(classType, new Array<AllocManaged>());
		}
		
		Array<AllocManaged> list = freeMap.get(classType);
		if(list.size <= 0)
		{
			try {
				Constructor<T> constructor = classType.getDeclaredConstructor();
				constructor.setAccessible(true);
				list.add((AllocManaged)constructor.newInstance());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		AllocManaged inst = list.pop();
		allocedMap.get(classType).add(inst);
		return (T)inst;
	}
	
	@SuppressWarnings("unchecked")
	protected static <T extends AllocManaged> Array<T> getAllocedList(Class<T> classType)
	{
		return (Array<T>) allocedMap.get(classType);
	}
	
	@SuppressWarnings("unchecked")
	protected static <T extends AllocManaged> Array<T> getFreeList(Class<T> classType)
	{
		return (Array<T>) freeMap.get(classType);
	}
	
	public <T extends AllocManaged> void release()
	{
		@SuppressWarnings("unchecked")
		Class<T> classType = getClassType();
		if(allocedMap.get(classType).removeValue(this, false))
			freeMap.get(classType).add(this);
	}
	
	@SuppressWarnings("rawtypes")
	abstract protected Class getClassType();
}
