package com.erkin.lupusvitae.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.erkin.lupusvitae.model.HexGroundType;

public class Assets {
	TextureAtlas atlas;
	public TextureRegion waterTexture;
	public TextureRegion grassTexture;
	public TextureRegion sandTexture;
	public TextureRegion snowTexture;
	public TextureRegion rockTexture;
	public TextureRegion rockyTexture;
	public TextureRegion cliffTexture;
	public TextureRegion bushTexture;
	public TextureRegion treeTexture;
	public TextureRegion selectedTexture;
	
	
	public Assets()
	{
        atlas = new TextureAtlas(Gdx.files.internal("data/textures/textures.pack"));
	}
	
	public void load()
	{
		waterTexture = atlas.findRegion("hex_water");
		grassTexture = atlas.findRegion("hex_grass");
		sandTexture = atlas.findRegion("hex_soil");
		snowTexture = atlas.findRegion("hex_snow");
		rockTexture = atlas.findRegion("hex_rock");
		rockyTexture = atlas.findRegion("hex_rocky");
		cliffTexture = atlas.findRegion("hex_cliff");
		bushTexture = atlas.findRegion("hex_bush");
		treeTexture = atlas.findRegion("hex_tree");
		selectedTexture = atlas.findRegion("hex_selected");
	}
	
	public TextureRegion getGroundTexture(HexGroundType.type type)
	{
		switch (type) {
		case WATER:
			return waterTexture;
		case GRASS:
			return grassTexture;
		case SOIL:
			return sandTexture;
		case SNOW:
			return snowTexture;
		case ROCK:
			return rockTexture;
		case ROCKY:
			return rockyTexture;
		case CLIFF:
			return cliffTexture;
		case BUSH:
			return bushTexture;
		case TREE:
			return treeTexture;
		default:
			break;
		}
		return grassTexture;
	}
	
	public TextureRegion getSelectedHexTexture()
	{
		return selectedTexture;
	}
}
