package com.TownSimulator.utility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Publisher<T> implements Serializable{
	private static final long serialVersionUID = -329905989975985190L;
	protected List<T> mListeners = new ArrayList<T>();
	
	public void addListener(T value)
	{
		mListeners.add(value);
	}
	
	public void removeListener(T value)
	{
		mListeners.remove(value);
	}
}
