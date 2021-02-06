package com.doro.jumpandrun;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.doro.jumpandrun.Screens.PlayScreen;

public class JumpAndRun extends Game {
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 208;
	public static final float PPM = 100;

	public static final short NOTHING_BIT = 0;
	public static final short GROUND_BIT = 1;
	public static final short MARIO_BIT = 2;
	public static final short BRICK_BIT = 4;
	public static final short COIN_BIT = 8;
	public static final short WINNING_BIT = 16;
	public static final short OBJECT_BIT = 32;
	public static final short ENEMY_BIT = 64;
	public static final short ENEMY_HEAD_BIT = 128;
	public static final short ITEM_BIT = 256;
	public static final short MARIO_HEAD_BIT = 512;


	public SpriteBatch batch;


	private Boolean hasWon;
	
	@Override
	public void create () {
		hasWon = false;
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	/** */

	public Boolean getHasWon() {
		return hasWon;
	}

	public void setHasWon(Boolean hasWon) {
		this.hasWon = hasWon;
	}

}
