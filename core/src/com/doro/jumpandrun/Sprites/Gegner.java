package com.doro.jumpandrun.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.doro.jumpandrun.Screens.PlayScreen;

public abstract class Gegner extends Sprite {
    protected World world;
    protected PlayScreen screen;
    public Body b2Body;
    public Vector2 tempo;


    public Gegner(PlayScreen screen, float x, float y){
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
        defineGegner();

    }

    protected abstract void defineGegner();
    public abstract void update(float dt);
    public abstract void hitOnKopf();
    public abstract void gegnerTrifftGegner(Gegner gegner);

    public void umdrehTempo(boolean x, boolean y) {
        if (x)
            tempo.x = -tempo.x;
        if (y)
            tempo.y = -tempo.y;
    }


}
