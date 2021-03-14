package com.doro.jumpandrun.Sprites;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.doro.jumpandrun.JumpAndRun;
import com.doro.jumpandrun.Screens.PlayScreen;

public abstract class PowerUp extends Sprite {
    protected PlayScreen screen;
    protected World world;
    protected Vector2 tempo;
    protected boolean zuEntfernen;
    protected boolean entfernt;
    protected Body body;

    public PowerUp(PlayScreen screen, float x, float y) {
        this.screen = screen;
        this.world = screen.getWorld();

        zuEntfernen = false;
        entfernt = false;

        setPosition(x, y);
        setBounds(getX(), getY(), 16 / JumpAndRun.PPM, 16 / JumpAndRun.PPM);
        definePowerUp();
    }

    public abstract void definePowerUp();

    public abstract void use();

    public void update(float dt){
        if (zuEntfernen && !entfernt){
            world.destroyBody(body);
            entfernt=true;
        }
    }

    public void draw(Batch batch){
        if(!entfernt)
            super.draw(batch);
    }

    public void entfernen(){
        zuEntfernen = true;
    }

    public void umdrehTempo(boolean x, boolean y) {
        if (x)
            tempo.x = -tempo.x;
        if (y)
            tempo.y = -tempo.y;
    }


}
