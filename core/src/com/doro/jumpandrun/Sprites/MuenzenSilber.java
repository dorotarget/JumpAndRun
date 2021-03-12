package com.doro.jumpandrun.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.doro.jumpandrun.JumpAndRun;
import com.doro.jumpandrun.Scenes.Hud;
import com.doro.jumpandrun.Screens.PlayScreen;

public class MuenzenSilber extends Muenzen {


    private float statusZeit;
    private Animation<TextureRegion> drehAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;


    public MuenzenSilber(PlayScreen screen, float x, float y) {
        super(screen,x+1, y);
        frames = new Array<TextureRegion>();
        for(int i = 0; i < 2; i++)
            //frames.add(new TextureRegion(screen.getAtlas().findRegion("gegner1"), i * 16, 0, 16, 16));
            //frames.add(new TextureRegion(screen.getHeroAtlas().findRegion("Bandit_gehen"), 1+i * 64, 4, 64, 64));

            frames.add(new TextureRegion(screen.getMuenzenAtlas().findRegion("muenzen_silber"), i * 64, 1, 32, 32));

        drehAnimation = new Animation(0.4f, frames);
        statusZeit = 0;
        setBounds(getX(), getY(), 16 / JumpAndRun.PPM, 16 / JumpAndRun.PPM);
        setToDestroy = false;

        destroyed = false;


    }

    public TextureRegion getFrame(float dt){
        TextureRegion region;


        region = drehAnimation.getKeyFrame(statusZeit, true);


        return region;
    }
    public void update(float dt){
        if (!setToDestroy)
            setRegion(getFrame(dt));

        statusZeit += dt;
        if(setToDestroy && !destroyed){
            world.destroyBody(b2Body);
            destroyed = true;
           // setRegion(new TextureRegion(screen.getHeroAtlas().findRegion("Bandit_sterben"), 1, -3, 64, 64));
            statusZeit = 0;
            Hud.addScore(1000);

        }
        else if(!destroyed){
            b2Body.setLinearVelocity(tempo);
            b2Body.applyForceToCenter(0,10, true);
            setPosition(b2Body.getPosition().x - getWidth() / 2, b2Body.getPosition().y - getHeight() / 2);
            setRegion(drehAnimation.getKeyFrame(statusZeit, true));}
    }

    @Override
    public void eingesammelt() {
        setToDestroy = true;
    }

    @Override
    protected void defineMuenze(){

        BodyDef bdef = new BodyDef();
        bdef.position.set(getX() , getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2Body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        //     CircleShape shape = new CircleShape();
        //     shape.setRadius(6 / JumpAndRun.PPM);
        PolygonShape shape = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-4, 3).scl(1 / JumpAndRun.PPM);
        vertice[1] = new Vector2(4, 3).scl(1 / JumpAndRun.PPM);
        vertice[2] = new Vector2(-4, -5).scl(1 / JumpAndRun.PPM);
        vertice[3] = new Vector2(4, -5).scl(1 / JumpAndRun.PPM);
        shape.set(vertice);
        fdef.filter.categoryBits = JumpAndRun.MUENZEN_BIT;
        fdef.filter.maskBits = JumpAndRun.BODEN_BIT | JumpAndRun.HERO_BIT | JumpAndRun.HERO_KOPF_BIT | JumpAndRun.OBJEKT_BIT ;

        fdef.shape = shape;
        b2Body.createFixture(fdef).setUserData(this);

        /*
        PolygonShape kopf = new PolygonShape();
        Vector2[] vertice2 = new Vector2[4];
        vertice2[0] = new Vector2(-3, 9).scl(1 / JumpAndRun.PPM);
        vertice2[1] = new Vector2(3, 9).scl(1 / JumpAndRun.PPM);
        vertice2[2] = new Vector2(-1, 4).scl(1 / JumpAndRun.PPM);
        vertice2[3] = new Vector2(1, 4).scl(1 / JumpAndRun.PPM);
        kopf.set(vertice2);

        fdef.shape = kopf;
        fdef.restitution = 1.0f;
        fdef.filter.categoryBits = JumpAndRun.GEGNER_KOPF_BIT;
        b2Body.createFixture(fdef).setUserData(this);*/
    }
    public void draw(Batch batch){
        if(!destroyed || statusZeit < 0.1f)
            super.draw(batch);
    }


}
