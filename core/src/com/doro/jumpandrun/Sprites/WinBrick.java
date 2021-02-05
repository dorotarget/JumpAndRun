package com.doro.jumpandrun.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.doro.jumpandrun.JumpAndRun;
import com.doro.jumpandrun.Screens.PlayScreen;
import com.doro.jumpandrun.Tools.InteractiveTileObject;

public class WinBrick extends InteractiveTileObject {

    public boolean gewonnen;
    private PlayScreen playScreen;

    public WinBrick(World world, TiledMap map, Rectangle bounds){
        super(world, map, bounds);
        FixtureDef fdef = new FixtureDef();

        fixture.setUserData(this);
        setCategoryFilter(JumpAndRun.BRICK_BIT);
        fdef.filter.categoryBits = JumpAndRun.MARIO_BIT;
        fdef.filter.maskBits = JumpAndRun.GROUND_BIT ;

        gewonnen = false;

    }

    @Override
    public void onHeadHit(Hero hero) {

    }

    @Override
    public void reachGoal(Hero hero) {
        Gdx.app.log("Win", "Collision");
        playScreen.won();

    }
}
