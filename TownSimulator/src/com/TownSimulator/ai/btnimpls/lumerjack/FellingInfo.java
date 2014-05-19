package com.TownSimulator.ai.btnimpls.lumerjack;

import java.io.Serializable;

import com.TownSimulator.entity.Tree;

public class FellingInfo implements Serializable{
	private static final long serialVersionUID = 2837725167951301451L;
	public Tree fellingTree;
	public boolean hasWood = false;
}
