package com.doro.jumpandrun.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.doro.jumpandrun.JumpAndRun;
import com.doro.jumpandrun.Tools.InteractiveTileObject;

import java.util.jar.JarEntry;


public class Coin extends InteractiveTileObject {
    public Coin(World world, TiledMap map, Rectangle bounds){
        super(world, map, bounds);
        fixture.setUserData(this);
        setCategoryFilter(JumpAndRun.COIN_BIT);

    }

    @Override
    public void onHeadHit(Hero hero) {
        Gdx.app.log("Sonderblock", "Bumm");
    }

    @Override
    public void reachGoal(Hero hero) {

    }


}
