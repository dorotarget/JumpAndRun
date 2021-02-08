package com.doro.jumpandrun.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.doro.jumpandrun.JumpAndRun;
import com.doro.jumpandrun.Screens.PlayScreen;

public class Gegner1 extends Gegner
{
    @Override
    public void hitOnKopf() {
        setToDestroy = true;
    }

    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;

    public Gegner1(PlayScreen screen, float x, float y) {
        super(screen,x+1, y);
        frames = new Array<TextureRegion>();
        for(int i = 0; i < 2; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("gegner1"), i * 16, 0, 16, 16));
        walkAnimation = new Animation(0.4f, frames);
        stateTime = 0;
        setBounds(getX(), getY(), 16 / JumpAndRun.PPM, 16 / JumpAndRun.PPM);
        setToDestroy = false;
        destroyed = false;
    }
    public void update(float dt){
        stateTime += dt;
        if(setToDestroy && !destroyed){
            world.destroyBody(b2Body);
            destroyed = true;
            setRegion(new TextureRegion(screen.getAtlas().findRegion("gegner1"), 32, 0, 16, 16));
            stateTime = 0;
        }
        else if(!destroyed){
            b2Body.setLinearVelocity(tempo);
            setPosition(b2Body.getPosition().x - getWidth() / 2, b2Body.getPosition().y - getHeight() / 2);
            setRegion(walkAnimation.getKeyFrame(stateTime, true));}
    }
    @Override
    protected void defineGegner(){

        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2Body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / JumpAndRun.PPM);
        fdef.filter.categoryBits = JumpAndRun.GEGNER_BIT;
        fdef.filter.maskBits = JumpAndRun.BODEN_BIT | JumpAndRun.OBJEKT_BIT | JumpAndRun.HERO_BIT;

        fdef.shape = shape;
        b2Body.createFixture(fdef).setUserData(this);

        PolygonShape kopf = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-5, 8).scl(1 / JumpAndRun.PPM);
        vertice[1] = new Vector2(5, 8).scl(1 / JumpAndRun.PPM);
        vertice[2] = new Vector2(-3, 3).scl(1 / JumpAndRun.PPM);
        vertice[3] = new Vector2(3, 3).scl(1 / JumpAndRun.PPM);
        kopf.set(vertice);

        fdef.shape = kopf;
        fdef.restitution = 1.0f;
        fdef.filter.categoryBits = JumpAndRun.GEGNER_KOPF_BIT;
        b2Body.createFixture(fdef).setUserData(this);
    }
    //Verschwindet nach 1 sek
    public void draw(Batch batch){
        if(!destroyed || stateTime < 1)
            super.draw(batch);
    }
}
