package com.doro.jumpandrun.Sprites;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.doro.jumpandrun.JumpAndRun;
import com.doro.jumpandrun.Screens.PlayScreen;
import com.doro.jumpandrun.Tools.InteractiveTileObject;

public class WinBrick extends InteractiveTileObject {
    public WinBrick(World world, TiledMap map, Rectangle bounds){
        super(world, map, bounds);
        fixture.setUserData(this);
        setCategoryFilter(JumpAndRun.BRICK_BIT);
    }

    @Override
    public void onHeadHit(Hero hero) {

    }
}
