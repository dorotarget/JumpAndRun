package com.doro.jumpandrun.Sprites;


import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.doro.jumpandrun.JumpAndRun;
import com.doro.jumpandrun.Scenes.Hud;
import com.doro.jumpandrun.Screens.PlayScreen;

import java.sql.Struct;

public class Hero extends Sprite {
    public static boolean won;
    public static boolean lost;


    public enum State {FALLEN, SPRINGEN, STEHEN, RENNEN, TOT, VERLETZT  }
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private TextureRegion heroStehen;
    private Animation<TextureRegion> heroRennen;
    private Animation <TextureRegion> heroSpringen;
    private TextureRegion heroTot;
    private TextureRegion heroVerletzt;


    private float statusTimer;
    private boolean rennenRechts;
    private boolean heroIstTot;
    private boolean heroIstVerletzt;



    private TextureAtlas heroAtlas;


/*
    public void hit(){
        won = true;

    }*/



    public Hero(World world, PlayScreen screen){
        //super(screen.getAtlas().findRegion("little_mario"));


        super(screen.getHeroAtlas().findRegion("Hero_gehen"));


        this.world = world;

        currentState = State.STEHEN;
        previousState = State.STEHEN;
        statusTimer = 0;
        rennenRechts = true;

        won = false;
        lost= false;


        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i = 1; i < 4; i++)
            frames.add(new TextureRegion(getTexture(), 1 +i * 64, 67, 64, 64));
        heroRennen = new Animation(0.2f, frames);
        frames.clear();

        for(int i = 5; i < 6; i++)
            frames.add(new TextureRegion(getTexture(), 67, 1, 64, 64));
        heroSpringen = new Animation(0.1f, frames);



        heroStehen = new TextureRegion(getTexture(), 839, 131, 64, 64);

        heroTot = new TextureRegion(getTexture(), 133, 1, 64, 64);

        heroVerletzt = new TextureRegion(getTexture(), 133, 1, 64, 64);


       // heroTot = new TextureRegion(screen.getHeroAtlas().findRegion("hero_sterben"), 0, 0, 64, 64);


        defineHero();
        setBounds(0, 0, 16 / JumpAndRun.PPM, 16 / JumpAndRun.PPM);
        setRegion(heroStehen);

    }

    public void die() {

        if (!istTot() && !istVerletzt()) {

            // MarioBros.manager.get("audio/music/mario_music.ogg", Music.class).stop();
            if (Hud.verloren()) {
                heroIstTot = true;
                Filter filter = new Filter();
                filter.maskBits = JumpAndRun.NOTHING_BIT;

                for (Fixture fixture : b2body.getFixtureList()) {
                    fixture.setFilterData(filter);
                }

                b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
            }
            else {
                heroIstVerletzt = true;
                //b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);

            }
        }
    }
    public boolean istTot(){
        return heroIstTot;
    }
    public boolean istVerletzt(){
        return heroIstVerletzt;
    }
    public float getStatusTimer(){
        return statusTimer;
    }


    public void getroffen(Gegner gegner){
        Hud.verliereLeben(1);
        die();



    }
    /*public boolean todesCheck{
        if (Hud.verloren())
            return true;
        else
            return false;
    }*/
    public void defineHero(){

        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / JumpAndRun.PPM, 32 / JumpAndRun.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-2, -6).scl(1 / JumpAndRun.PPM);
        vertice[1] = new Vector2(2, -6).scl(1 / JumpAndRun.PPM);
        vertice[2] = new Vector2(-2, 4).scl(1 / JumpAndRun.PPM);
        vertice[3] = new Vector2(2, 4).scl(1 / JumpAndRun.PPM);
        shape.set(vertice);
    //    CircleShape shape = new CircleShape();
    //    shape.setRadius(6 / JumpAndRun.PPM);
        fdef.filter.categoryBits = JumpAndRun.HERO_BIT;
        fdef.filter.maskBits = JumpAndRun.BODEN_BIT |
                JumpAndRun.GEGNER_KOPF_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        //b2body.createFixture(fdef);
        fdef.filter.categoryBits = JumpAndRun.HERO_BIT;
        fdef.filter.maskBits = JumpAndRun.BODEN_BIT |
                JumpAndRun.GELD_BIT |
                JumpAndRun.BLOCK_BIT |
                JumpAndRun.GEGNER_BIT |
                JumpAndRun.OBJEKT_BIT |
                JumpAndRun.GEGNER_KOPF_BIT |
                JumpAndRun.GEWINN_BIT |
                JumpAndRun.ITEM_BIT;
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / JumpAndRun.PPM, 6 / JumpAndRun.PPM), new Vector2(2 / JumpAndRun.PPM, 6 / JumpAndRun.PPM));
        fdef.filter.categoryBits = JumpAndRun.HERO_KOPF_BIT;
        fdef.shape = head;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData(this);

        //b2body.createFixture(fdef).setUserData("head");
    }
    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));


    }
    public TextureRegion getFrame(float dt){
        currentState = getState();
        statusTimer = currentState == previousState ? statusTimer + dt : 0;
        previousState = currentState;

        TextureRegion region;
        switch(currentState){
            case SPRINGEN:
                region = heroSpringen.getKeyFrame(statusTimer);
                break;
            case RENNEN:
                region = heroRennen.getKeyFrame(statusTimer, true);
                break;

            default:
                region = heroStehen;
                break;
            case TOT:
                region = heroTot;
                break;
            case VERLETZT:
                if (statusTimer < 1  )
                    region = heroVerletzt;
                else{
                    region = heroStehen;
                    getState();
                    heroIstVerletzt = false;
                }

                break;
        }if((b2body.getLinearVelocity().x < 0 || !rennenRechts) && !region.isFlipX()){
            region.flip(true, false);
            rennenRechts = false;
        }
        else if((b2body.getLinearVelocity().x > 0 || rennenRechts) && region.isFlipX()){
            region.flip(true, false);
            rennenRechts = true;
        }


        return region;

    }
    public State getState(){
        if(heroIstTot)
            return State.TOT;
        //if (previousState == State.VERLETZT) {
           // return State.STEHEN;
        //}
        if(heroIstVerletzt)
            return State.VERLETZT;
        if(b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.SPRINGEN))
            return State.SPRINGEN;
        else if(b2body.getLinearVelocity().y < 0)
            return State.FALLEN;
        else if(b2body.getLinearVelocity().x != 0)
            return State.RENNEN;
        else
            return State.STEHEN;

    }
    public State wiederbeleben(){
        return State.STEHEN;
    }





}
