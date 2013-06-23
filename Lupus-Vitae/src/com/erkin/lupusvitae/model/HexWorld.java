package com.erkin.lupusvitae.model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

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
        float rivers[][] = worldGenerator.generateRivers(worldRows, 2000*5);
        float rocks[][] = worldGenerator.generateSceneries(worldRows,1.1,0.5);
        float trees[][] = worldGenerator.generateSceneries(worldRows,1.1,1);
        
		for(int x = 0; x < heightHexes.length; x++){
			for(int y = 0; y < heightHexes[0].length; y++){
				
				/*
				 *  Ground
				 */
				groundHexes[x][y] = HexGroundType.getTypeForHeight(heightHexes[x][y]).ordinal();
				
				/*
				 * Sceneries
				 */
				// Rocks
				float rockRangeMin = 0.100F;
				float rockRangeMax = 0.102F;
				HexGroundType.type rock = HexGroundType.getScenery(HexGroundType.type.ROCK, rocks[x][y], rockRangeMin, rockRangeMax);
				if (rock != null)
					groundHexes[x][y] = rock.ordinal();
				
				// Trees
				float treeRangeMin = 0.100F;
				float treeRangeMax = 0.102F;
				if (groundHexes[x][y] == HexGroundType.type.ROCKY.ordinal())
				{
					treeRangeMax = 0.12F;
				}
				HexGroundType.type tree = HexGroundType.getScenery(HexGroundType.type.TREE, trees[x][y], treeRangeMin, treeRangeMax);
				if (tree != null)
					groundHexes[x][y] = tree.ordinal();
				
				
				/*
				 * Rivers
				 */
				if (rivers[x][y] == 1)
					groundHexes[x][y] = HexGroundType.type.WATER.ordinal();
			}
		}
		// Debug
		createWorldBitmap();
		
	}
	
	/*
	 * Generation methods
	 */
	
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

	
	private void createWorldBitmap()
	{
		BufferedImage img = new BufferedImage((int)this.rows, (int)this.cols, BufferedImage.TYPE_INT_ARGB);
        
		for(int x = 0; x < groundHexes.length; x++){
		
			for(int y = 0; y < groundHexes[0].length; y++){
				int R = 0;
				int G = 0;
				int B = 0;
				if (groundHexes[x][y]== HexGroundType.type.WATER.ordinal())
				{
					R = 0;
					G = 0;
					B = 255;
				}
				else if (groundHexes[x][y]== HexGroundType.type.GRASS.ordinal())
				{
					R = 113;
					G = 255;
					B = 12;
				}
				else if (groundHexes[x][y]== HexGroundType.type.ROCKY.ordinal())
				{
					R = 128;
					G = 128;
					B = 128;
				}
				else if (groundHexes[x][y]== HexGroundType.type.SNOW.ordinal())
				{
					R = 255;
					G = 255;
					B = 255;
				}
				else if (groundHexes[x][y]== HexGroundType.type.TREE.ordinal())
				{
					R = 0;
					G = 114;
					B = 19;
				}
				else if (groundHexes[x][y]== HexGroundType.type.SOIL.ordinal())
				{
					R = 188;
					G = 138;
					B = 81;
				}
				img.setRGB(x, y, WorldGeneratorHelper.RGB(R, G, B));
			}
		}
		
		File outputFile = new File("debug/map.png");
		try {
			ImageIO.write(img, "PNG", outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
