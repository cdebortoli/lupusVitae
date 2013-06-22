package com.me.lupusvitae.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.erkin.lupusvitae.LupusVitaeGame;

public class AbstractScreen implements Screen {

	protected LupusVitaeGame _game;
	public AbstractScreen(LupusVitaeGame game)
	{
		this._game = game;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		
	};

	@Override
	public void resize(int width, int height) {};

	@Override
	public void show() {};

	@Override
	public void hide() {};

	@Override
	public void pause() {};
	
	@Override
	public void resume() {};

	@Override
	public void dispose() {};

}
