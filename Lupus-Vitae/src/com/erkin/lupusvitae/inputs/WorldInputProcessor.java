package com.erkin.lupusvitae.inputs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.erkin.lupusvitae.model.HexWorld;

public class WorldInputProcessor implements InputProcessor {
	private OrthographicCamera cam;
	private float dragOldX;
	private float dragOldY;
	private HexWorld world;
	private Vector3 touchPos;
	public WorldInputProcessor(OrthographicCamera camParam, HexWorld worldParam)
	{
		dragOldX 	= -999999;
		dragOldY 	= -999999;
		this.cam 	= camParam;
		this.world	= worldParam;
		touchPos = new Vector3();
	}
	
	@Override
	public boolean keyDown(int keycode) {

        return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		cam.unproject(touchPos.set(screenX, screenY, 0));
		touchHex(touchPos);
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		
//		// Drag left
//		if (screenX < dragOldX)
//		{
//            if (cam.position.x > 0 - 5)
//            	cam.translate(-1, 0, 0);
//		}
//		// Drag right
//		else if(screenX > dragOldX)
//		{
//			cam.translate(1, 0, 0);
//		}
//		// Drag up
//		if (screenY < dragOldY)
//		{
//
//			cam.translate(0, 1, 0);
//		}
//		// Drag down
//		else if(screenY > dragOldY)
//		{
//
//            if (cam.position.y >  0 - 5)
//            	cam.translate(0, -1, 0);
//		}
//		if (dragOldX == -999999)
//		{
//			dragOldX = screenX;
//			dragOldY = screenY;
//		}
		
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	// Pooling
	public void poolingKeyboard() {
		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			if(cam.zoom < 1000)
				cam.zoom += 0.5;        
		}
		if(Gdx.input.isKeyPressed(Input.Keys.Q)) {
			if(cam.zoom > 0.5)
				cam.zoom -= 0.5;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
		        if (cam.position.x > 0 - 5)
		        	cam.translate(-1, 0, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
		        if (cam.position.x <  world.width + 5)
		        	cam.translate(1, 0, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
		        if (cam.position.y >  0 - 5)
		        	cam.translate(0, -1, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
		        if (cam.position.y <  world.height + 5)
		        	cam.translate(0, 1, 0);
		}
	}

	
	/*
	 * Game functions
	 */
	private void touchHex(Vector3 vec)
	{
		//http://www.gamedev.net/page/resources/_/technical/game-programming/coordinates-in-hexagon-based-tile-maps-r1800
	    
		// Section coor
		float sectX = (int)Math.floor(vec.x  / world.colCalcul);
	    float sectY = (int)Math.floor(vec.y / world.rowCalcul);

	    // Pixel coor in sections
		float sectPixelX = vec.x  % world.colCalcul;
		float sectPixelY = vec.y % world.rowCalcul;

		// Final coor
		int arrayY = 0;
		int arrayX = 0;

		// Used to calculate angle
		float m = world.h / world.r;
		
		// Even line : Type A
		if (sectY % 2 < 1)
		{
			 // Middle
			 arrayY = (int)sectY;
			 arrayX = (int)sectX;
			 
			 // Left
			 if (sectPixelY < (world.h - sectPixelX * m))
			 {
				 arrayY = (int)sectY - 1;
				 arrayX = (int)sectX - 1; 
			 }
			 // Right
			 else if (sectPixelY < (-world.h + sectPixelX * m))
			 {
				 arrayY = (int)sectY - 1;
				 arrayX = (int)sectX; 
			 }
		}
		// Odd line : Type B
		else
		{
			 // Right of intersection
			 if (sectPixelX >= world.r)
			 {
				 // Hex under (y)
				 if (sectPixelY < (2 * world.h - sectPixelX * m))
				 {
					 arrayY = (int)sectY - 1;
					 arrayX = (int)sectX; 
				 }
				 else
				 {
					 arrayY = (int)sectY;
					 arrayX = (int)sectX; 
				 }
			 }
			 // Left of intersection
			 if (sectPixelX < world.r)
			 {
				 // Hex under (y)
				 if (sectPixelY < sectPixelX * m)
				 {
					 arrayY = (int)sectY - 1;
					 arrayX = (int)sectX; 
				 }
				 else
				 {
					 arrayY = (int)sectY;
					 arrayX = (int)sectX - 1; 
				 }
			 }
		}

		if ((world.selectedHex != null) && (world.selectedHex.x == arrayX) && (world.selectedHex.y == arrayY))
		{
			world.selectedHex = null;
		}
		else
		{
			world.selectedHex = new Vector2(arrayX, arrayY);
		}
	}
}
