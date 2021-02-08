package com.doro.jumpandrun.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.doro.jumpandrun.Screens.PlayScreen;


//------------haben wir zwar noch nicht, brauchen wir aber wahrscheinlich

public class Brick extends com.doro.jumpandrun.Tools.InteractiveTileObject {
    public Brick(World world, TiledMap map, Rectangle bounce){
        super(world, map, bounce);
        fixture.setUserData(this);
    }

    @Override
    public void onHeadHit(Hero hero) {
        Gdx.app.log("Normaler Block", "Bumm");
    }

    @Override
    public void reachGoal(Hero hero) {

    }




}
