package com.TownSimulator.utility;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;



public class Singleton {
	protected static Map<Class<? extends Singleton>, Singleton> mInstaceMap = new HashMap<Class<? extends Singleton>, Singleton>();
		
	@SuppressWarnings("unchecked")
	public synchronized static <T extends Singleton> T getInstance(Class<T> classType)
	{
		if( !mInstaceMap.containsKey(classType) )
		{
			try {
				Constructor<T> constructor = classType.getDeclaredConstructor();
				constructor.setAccessible(true);
				mInstaceMap.put(classType, (Singleton)constructor.newInstance());
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return (T)mInstaceMap.get(classType);	
	}
}
