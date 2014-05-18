package com.TownSimulator.utility;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class Singleton {
	private static Map<Class<? extends Singleton>, Singleton> mInstaceMap = new HashMap<Class<? extends Singleton>, Singleton>();
		
	@SuppressWarnings("unchecked")
	public static <T extends Singleton> T getInstance(Class<T> classType)
	{
		if( !mInstaceMap.containsKey(classType) )
		{
			try {
				Constructor<T> constructor = classType.getDeclaredConstructor();
				constructor.setAccessible(true);
				mInstaceMap.put(classType, (Singleton)constructor.newInstance());
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}		
		return (T)mInstaceMap.get(classType);	
	}
	
	public static <T extends Singleton> void loadInstance(Class<T> clazz, T clazzInstance) {
		mInstaceMap.put(clazz, clazzInstance);
	}
	
	protected static void clearInstanceMap() {
		mInstaceMap.clear();
	}
}