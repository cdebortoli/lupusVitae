package com.me.lupusvitae.screens;

import com.erkin.lupusvitae.LupusVitaeConstants;
import com.erkin.lupusvitae.LupusVitaeGame;
import com.erkin.lupusvitae.model.HexWorld;
import com.erkin.lupusvitae.view.WorldRenderer;

public class GameScreen extends AbstractScreen {

    // Model
    private HexWorld world;
    private WorldRenderer worldRenderer;
    
    public GameScreen(LupusVitaeGame game) {
		super(game);
				
		world = new HexWorld(LupusVitaeConstants.WORLD_WIDTH_UNITS, LupusVitaeConstants.WORLD_HEIGHT_UNITS, LupusVitaeConstants.HEX_SIDE_UNIT);
		worldRenderer = new WorldRenderer(world);
		//world.tempGroundGeneration();
	}
    

	@Override
	public void render(float delta) {
		super.render(delta);
		worldRenderer.render();
	}

	@Override
	public void resize(int width, int height) {
		worldRenderer.resize(width, height);
	}

	@Override
	public void show() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {		
	}

	@Override
	public void resume() {	
	}

	@Override
	public void dispose() {
		worldRenderer.dispose();
	}
}
