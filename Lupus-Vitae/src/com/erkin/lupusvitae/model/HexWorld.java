package com.erkin.lupusvitae.model;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.erkin.lupusvitae.WorldGenerator;
import com.erkin.lupusvitae.utils.*;

public class HexWorld {
	
	// Hexes
	private int[][]	groundHexes;
	private float[][] heightHexes;
	private ArrayList<Hex> loadedHexes = new ArrayList<Hex>();
	//private ArrayList<Hex>	livingHexes;
	//private ArrayList<Hex>	itemHexes; // Food at ground, etc

	public ArrayList<Hex> getLoadedHexes()
	{
		return loadedHexes;		
	}
	public Hex getLoadedHex(int row, int col)
	{
		for (Hex hex : this.loadedHexes) {
			if (row == hex.getRowIndice() && col == hex.getColumnIndice())
			{
				return hex;
			}
		}
		return null;
	}
	
	public int[][]getGroundHexes()
	{
		return groundHexes;
	}
	public int getGroundHex(int row, int col)
	{
		return groundHexes[col][row];
	}

	public float[][]getHeightHexes()
	{
		return heightHexes;
	}
	public float getHeightHex(int row, int col)
	{
		return heightHexes[col][row];
	}
	
	// Map width
	public	float width;
	
	// Height Map
	public	float height;

	// Hexes indices
	public int rows;
	public int cols;

	// Hex sizes
	public float side;
	public float hexWidth;
	public float hexHeight;
	public float colCalcul;
	public float rowCalcul;
	public float h;
	public float r;
	
	public Vector2 selectedHex;
	
	// Init
	public HexWorld(int worldRows, int worldCols, float hexSide)
	{
		// World Parameters
		this.rows = worldRows;
		this.cols = worldCols;
		groundHexes = new int[cols][rows];
		heightHexes = new float[cols][rows];
		// Hexes parameters
		this.side = hexSide;
		
		// World Size
		this.hexWidth = HexMath.getWidth(side, HexOrientation.POINT);
		this.hexHeight = HexMath.getHeight(side, HexOrientation.POINT);
		this.width = (cols * hexWidth) + hexWidth;
		this.height = rows * hexHeight;
		
        // Values to draw
		this.h = HexMath.getH(this.side, HexOrientation.POINT);
		this.r = HexMath.getR(this.side, HexOrientation.POINT);
        this.colCalcul = 2 * this.r;
        this.rowCalcul = this.h + this.side;	
        
        selectedHex = null;
        
        // World generation
        WorldGenerator worldGenerator = new WorldGenerator(2000);
        heightHexes = worldGenerator.generateWorld1(worldRows);
        float rocks[][] = worldGenerator.generateSceneries(worldRows,1.1,0.5);
        float trees[][] = worldGenerator.generateSceneries(worldRows,1.1,1);
        
		for(int x = 0; x < heightHexes.length; x++){
			for(int y = 0; y < heightHexes[0].length; y++){
				groundHexes[x][y] = HexGroundType.getTypeForHeight(heightHexes[x][y]).ordinal();
				
				HexGroundType.type rock = HexGroundType.getScenery(HexGroundType.type.ROCK, rocks[x][y], 0.100F, 0.102F);
				if (rock != null)
					groundHexes[x][y] = rock.ordinal();
				
				HexGroundType.type tree = HexGroundType.getScenery(HexGroundType.type.TREE, trees[x][y], 0.100F, 0.102F);
				if (tree != null)
					groundHexes[x][y] = tree.ordinal();
			}
		}
		
	}
	
	/*
	 * Generation methods
	 */
	
	// Generate ground base
	public void tempGroundGeneration()
	{
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				groundHexes[col][row] = HexGroundType.type.WATER.ordinal();
			}
		}
	}
	
	// Generate loaded hex
	public void generateLoadHexes(int minCol, int maxCol, int minRow, int maxRow)
	{
		this.loadedHexes.clear();
		
		// New tiles
		for (int col = minCol; col < maxCol; col++) {
			for (int row = minRow; row < maxRow; row++) {
				if (row >= 0 && row < rows && col >= 0 && col < cols)
					createLoadedHex(col,row,r,h);
			}
		}
	}
	
	// Create one loaded hex
	public void createLoadedHex(int col, int row, float r, float h)
	{
		// Calcul pixel position
		float pixelX;
		if (row % 2 == 0)
		{
			pixelX = col * 2 * r;
		}
		else
		{
			pixelX = (col * 2 * r) + r;
		}
		float pixelY = row * (h + side);
		Hex newHex = new Hex(pixelX, pixelY, side, row, col, hexWidth, hexHeight);
		this.loadedHexes.add(newHex);
	}

}
