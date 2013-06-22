package com.erkin.lupusvitae;

import com.badlogic.gdx.Game;
import com.me.lupusvitae.screens.GameScreen;

public class LupusVitaeGame extends Game {
    
	@Override
	public void create() {
		setScreen(new GameScreen(this));
	}

}