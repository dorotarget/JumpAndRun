package com.doro.jumpandrun.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.doro.jumpandrun.Screens.PlayScreen;


//------------haben wir zwar noch nicht, brauchen wir aber wahrscheinlich

public class Brick extends com.doro.jumpandrun.Tools.InteractiveTileObject {
    public Brick(PlayScreen screen, Rectangle bounds){
        super(screen, bounds);
        fixture.setUserData(this);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Normaler Block", "Bumm");
    }

}
