package com.doro.jumpandrun.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.doro.jumpandrun.JumpAndRun;
import com.doro.jumpandrun.Screens.PlayScreen;

import javax.swing.Spring;

public abstract class Muenzen extends Sprite {
    protected World world;
    protected PlayScreen screen;
    public Body b2Body;
    public Vector2 tempo;


    public Muenzen(PlayScreen screen, float x, float y){
        this.world = screen.getWorld();
        world = screen.getWorld();
        //world = new World(new Vector2(0, 0), true);

        this.screen = screen;
        setPosition(x -64 / JumpAndRun.PPM, y + 4/ JumpAndRun.PPM);
        defineMuenze();
        tempo = new Vector2(0, 0);

        b2Body.setActive(true);

    }


    protected abstract void defineMuenze();
    public abstract void update(float dt);
    public abstract void eingesammelt();


}

