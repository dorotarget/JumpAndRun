package com.doro.jumpandrun.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.doro.jumpandrun.Screens.PlayScreen;
import com.doro.jumpandrun.Tools.InteractiveTileObject;


public class Coin extends InteractiveTileObject {
    public Coin(PlayScreen screen, Rectangle bounds){
        super(screen, bounds);
        fixture.setUserData(this);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Sonderblock", "Bumm");
    }
}
