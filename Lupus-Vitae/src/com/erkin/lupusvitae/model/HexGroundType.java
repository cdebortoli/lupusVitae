package com.erkin.lupusvitae.model;

public class HexGroundType {
	public enum type
	{
		WATER,
		GRASS,
		SOIL,
		SNOW,
		ROCKY,
		CLIFF,
		ROCK,
		BUSH,
		TREE
	}
	
	public static type getTypeForHeight(float height)
	{
		if (height <= 0.2)
		{
			return type.SOIL;
		}
		
		if (height <= 0.5)
		{
			return type.GRASS;
		}
		
		if (height <= 0.8)
		{
			return type.TREE;
		}
		
		if (height <= 1.1)
		{
			return type.GRASS;
		}
		
		if (height <= 1.3)
		{
			return type.ROCKY;
		}
		
		return type.SNOW;
	}
	
	public static type getScenery(type hexType, float height, float min, float max)
	{
		if (height > min && height < max)
			return hexType;
		return null;
		
	}
}
