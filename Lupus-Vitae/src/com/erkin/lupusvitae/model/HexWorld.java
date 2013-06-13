package com.erkin.lupusvitae.model;

import java.util.ArrayList;

import com.erkin.lupusvitae.utils.*;

public class HexWorld {
	
	// Hexes
	private int[][]	groundHexes;
	private ArrayList<Hex> loadedHexes = new ArrayList<Hex>();
	//private Hex[][]	livingHexes;
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
	private	float width;
	public float getWidth()
	{
		return width;
	}
	
	// Height Map
	private	float height;
	public float getHeight()
	{
		return height;
	}

	// Hexes indices
	private int rows;
	public float getRows()
	{
		return rows;
	}
	
	private int cols;
	public float getCols()
	{
		return cols;
	}

	// Hex sizes
	private float side;
	public float getSide()
	{
		return side;
	}
	
	private float hexWidth;
	public float getHexWidth()
	{
		return hexWidth;
	}
	
	private float hexHeight;
	public float getHexHeight()
	{
		return hexHeight;
	}
	
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
	}
	
	public void tempGroundGeneration()
	{
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				groundHexes[col][row] = HexGroundType.type.PLAIN.ordinal();
			}
		}
	}
	
	public void generateLoadHexes(int minCol, int maxCol, int minRow, int maxRow)
	{
		this.loadedHexes.clear();
		// Hex Params
		final float h = HexMath.getH(side, HexOrientation.POINT);
		final float r = HexMath.getR(side, HexOrientation.POINT);
		
		// New tiles
		for (int col = minCol; col <= maxCol; col++) {
			for (int row = minRow; row <= maxRow; row++) {
				if (row > 0 && row < getRows() && col > 0 && col < getCols())
					createLoadedHex(col,row,r,h);
			}
		}
	}
	
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
