package com.doro.jumpandrun;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.doro.jumpandrun.Screens.PlayScreen;

public class JumpAndRun extends Game {
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 208;
	public static final float PPM = 100;

	//hier standen im Tutorial diese Sachen:
	public static final short BODEN_BIT = 1;
	public static final short HERO_BIT = 2;
	public static final short OBJEKT_BIT = 4;
	//public static final short
	//public static final short
	//public static final short
	public static final short GEGNER_BIT = 64;
	public static final short GEGNER_KOPF_BIT = 128;

	public SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
}
