package com.doro.jumpandrun.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.doro.jumpandrun.JumpAndRun;
import com.doro.jumpandrun.Screens.PlayScreen;

public abstract class Gegner extends Sprite {
    protected World world;
    protected PlayScreen screen;
    public Body b2Body;
    public Vector2 tempo;


    public Gegner(PlayScreen screen, float x, float y){
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x -64 /JumpAndRun.PPM, y + 4/ JumpAndRun.PPM);
        defineGegner();
        tempo = new Vector2(1 , 0);
        b2Body.setActive(false);
    }

    protected abstract void defineGegner();
    public abstract void update(float dt);
    public abstract void hitOnKopf();
    //public abstract void gegnerTrifftGegner(Gegner gegner);

    public void umdrehTempo(boolean x, boolean y) {
        if (x)
            tempo.x = -tempo.x;
        if (y)
            tempo.y = -tempo.y;
    }

    public void reverseVelocity(boolean x, boolean y){

        if(x) {
            tempo.x = -tempo.x;

        }
        if(y) {
            tempo.y = -tempo.y;
        }
    }

}
