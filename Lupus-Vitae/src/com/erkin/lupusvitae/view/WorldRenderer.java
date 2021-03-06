package com.erkin.lupusvitae.view;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.erkin.lupusvitae.inputs.WorldInputProcessor;
import com.erkin.lupusvitae.model.Hex;
import com.erkin.lupusvitae.model.HexGroundType;
import com.erkin.lupusvitae.model.HexWorld;
import com.erkin.lupusvitae.utils.Assets;

public class WorldRenderer {

	private OrthographicCamera worldCam;
	private HexWorld world;
	private FPSLogger fpsLogger = new FPSLogger();
	private WorldInputProcessor inputProcessor;
	private Assets assets;
	
	// Cam values
    float rotationSpeed = 0.5f;
    static final int VIEWPORT_WIDTH_UNITS  = 10;
    static final int VIEWPORT_HEIGHT_UNITS = 10;
    
    // Rander objects
    private SpriteBatch spriteBatch;
	int sprite_margin;
	int previousMinCol = -1;
	int previousMaxCol = -1;
	int previousMinRow = -1;
	int previousMaxRow = -1;
	
	public WorldRenderer(HexWorld worldParam)
	{
		this.world = worldParam;
		
		// Init
        spriteBatch = new SpriteBatch();

		// Load images
        assets = new Assets();
        assets.load();

		sprite_margin = 4; // Used to pre-render sprite after bounds
	}
	
	 public void render() {
		 	
		 	spriteBatch.setProjectionMatrix(worldCam.combined);
		 	
			// Handle input of the camera
			inputProcessor.poolingKeyboard();
			
			spriteBatch.begin();
			
			// Cam pixel position
			float min_pixel_x = (worldCam.position.x - ((worldCam.viewportWidth * worldCam.zoom) / 2) - sprite_margin) ;
			float max_pixel_x = (worldCam.position.x + ((worldCam.viewportWidth * worldCam.zoom) / 2) + sprite_margin) ;
			float min_pixel_y = (worldCam.position.y - ((worldCam.viewportHeight * worldCam.zoom) / 2) - sprite_margin) ;
			float max_pixel_y = (worldCam.position.y + ((worldCam.viewportHeight * worldCam.zoom) / 2) + sprite_margin) ;
			
			// Cam position in col/row
			int min_col = (int) (min_pixel_x / world.colCalcul);
			int max_col = (int) (max_pixel_x / world.colCalcul);
			int min_row = (int) (min_pixel_y / world.rowCalcul);
			int max_row = (int) (max_pixel_y / world.rowCalcul);
			
			// Load hexes
			updateLoadedHex(min_col,max_col,min_row,max_row);
			
			// Render loaded hexes
			for (Hex hex : this.world.getLoadedHexes()) {
				renderHex(hex);
			}
			spriteBatch.end();
			
			// Update
			//fpsLogger.log();
			worldCam.update();
	 }
	 
	 // Compare with previous data, and check if loaded hexes need to be reloaded
	 public void updateLoadedHex(int minCol, int maxCol, int minRow, int maxRow)
	 {
		 if (previousMinCol == -1)
		 {
			 previousMinCol = minCol;
			 previousMaxCol = maxCol;
			 previousMinRow = minRow;
			 previousMaxRow = maxRow;
			 this.world.generateLoadHexes(minCol, maxCol, minRow, maxRow);
		 }
		 else if ((previousMinCol != minCol) || (previousMaxCol != maxCol) || (previousMinRow != minRow) || (previousMaxRow != maxRow))
		 {
			 this.world.generateLoadHexes(minCol, maxCol, minRow, maxRow);
		 }

		 previousMinCol = minCol;
		 previousMaxCol = maxCol;
		 previousMinRow = minRow;
		 previousMaxRow = maxRow;
	 }
	 
	 // Render one hex
	 public void renderHex(Hex hex)
	 {
		 int groundHex = world.getGroundHex(hex.getRowIndice(), hex.getColumnIndice());
		 float heightHex = world.getHeightHex(hex.getRowIndice(), hex.getColumnIndice());
		 


		 spriteBatch.setColor(1, 1, 1, 1);
		 float alphaSub = (float) ((2 - heightHex));
		 float alpha = 1 - alphaSub; 
		 spriteBatch.setColor(alphaSub/2, alphaSub/2, alphaSub/2, 1);
	
		if ((world.selectedHex != null) && (world.selectedHex.x == hex.getColumnIndice()) && (world.selectedHex.y == hex.getRowIndice()))
		{
			spriteBatch.draw(assets.getSelectedHexTexture(), hex.getPositionX(), hex.getPositionY(), world.hexWidth, world.hexHeight);
		}
		else
			spriteBatch.draw(assets.getGroundTexture(HexGroundType.type.values()[groundHex]), hex.getPositionX(), hex.getPositionY(), world.hexWidth, world.hexHeight);
	 }
	 
	 
	 public void resize(int width, int height)
	 {
		// Camera
		float aspectRatio = (float) width / (float) height;
	    worldCam = new OrthographicCamera(VIEWPORT_WIDTH_UNITS * aspectRatio, VIEWPORT_HEIGHT_UNITS);
	    worldCam.translate((VIEWPORT_WIDTH_UNITS * aspectRatio)/2, VIEWPORT_HEIGHT_UNITS/2, 0);
	    
	    // Input processor
		inputProcessor = new WorldInputProcessor(worldCam,world);
		Gdx.input.setInputProcessor(inputProcessor);
	 }
	 
	 public void dispose()
	 {
		 spriteBatch.dispose();
	 }

}
