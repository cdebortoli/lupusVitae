package com.erkin.lupusvitae.inputs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
		
	    float sectX = touchPos.x  / world.colCalcul;
	    float sectY = touchPos.y / world.rowCalcul;
	    float sectPixelX = touchPos.x  % world.colCalcul;
	    float sectPixelY = touchPos.y % world.rowCalcul;
	    
	    if (sectY % 2 >= 1)
	    {
	    	Gdx.app.log("a","A");
	    }
	    else
	    	
	    {
	    	Gdx.app.log("b","b");
	    }
		
//If (SectY AND 1 = 0) then SectTyp := A else SectTyp := B;
		
		
		
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		
		// Drag left
		if (screenX < dragOldX)
		{
            if (cam.position.x > 0 - 5)
            	cam.translate(-1, 0, 0);
		}
		// Drag right
		else if(screenX > dragOldX)
		{
			cam.translate(1, 0, 0);
		}
		// Drag up
		if (screenY < dragOldY)
		{

			cam.translate(0, 1, 0);
		}
		// Drag down
		else if(screenY > dragOldY)
		{

            if (cam.position.y >  0 - 5)
            	cam.translate(0, -1, 0);
		}
		if (dragOldX == -999999)
		{
			dragOldX = screenX;
			dragOldY = screenY;
		}
		
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

}
