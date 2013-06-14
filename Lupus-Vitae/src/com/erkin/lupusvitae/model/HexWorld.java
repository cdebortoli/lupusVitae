package com.erkin.lupusvitae.model;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.erkin.lupusvitae.utils.*;

public class HexWorld {
	
	// Hexes
	private int[][]	groundHexes;
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
	
	// Init
	public HexWorld(int worldRows, int worldCols, float hexSide)
	{
		// World Parameters
		this.rows = worldRows;
		this.cols = worldCols;
		groundHexes = new int[cols][rows];
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
	}
	
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
		for (int col = minCol; col <= maxCol; col++) {
			for (int row = minRow; row <= maxRow; row++) {
				if (row >= 0 && row <= rows && col >= 0 && col <= cols)
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
