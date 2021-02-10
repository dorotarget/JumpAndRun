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
import com.doro.jumpandrun.Screens.PlayScreen;

public class Hero extends Sprite {
    public static boolean won;

    public enum State {FALLEN, SPRINGEN, STEHEN, RENNEN  }
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private TextureRegion heroStehen;
    private Animation<TextureRegion> heroRennen;
    private Animation <TextureRegion> heroSpringen;
    private float statusTimer;
    private boolean rennenRechts;

    private TextureAtlas heroAtlas;


/*
    public void hit(){
        won = true;

    }*/



    public Hero(World world, PlayScreen screen){
        //super(screen.getAtlas().findRegion("little_mario"));


        super(screen.getHeroAtlas().findRegion("hero_gehen"));


        this.world = world;

        currentState = State.STEHEN;
        previousState = State.STEHEN;
        statusTimer = 0;
        rennenRechts = true;

        won = false;


        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i = 1; i < 4; i++)
            frames.add(new TextureRegion(getTexture(), 1 +i * 32, 35, 32, 32));
        heroRennen = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 5; i < 6; i++)
            frames.add(new TextureRegion(getTexture(), 1, 1, 32, 32));
        heroSpringen = new Animation(0.1f, frames);



        heroStehen = new TextureRegion(getTexture(), 259, 67, 32, 32);

        defineHero();
        setBounds(0, 0, 16 / JumpAndRun.PPM, 16 / JumpAndRun.PPM);
        setRegion(heroStehen);

    }




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
        //shape.setRadius(6 / JumpAndRun.PPM);

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

        TextureRegion region;
        switch(currentState){
            case SPRINGEN:
                region = heroSpringen.getKeyFrame(statusTimer);
                break;
            case RENNEN:
                region = heroRennen.getKeyFrame(statusTimer, true);
                break;
            case FALLEN:
            case STEHEN:
            default:
                region = heroStehen;
                break;
        }if((b2body.getLinearVelocity().x < 0 || !rennenRechts) && !region.isFlipX()){
            region.flip(true, false);
            rennenRechts = false;
        }
        else if((b2body.getLinearVelocity().x > 0 || rennenRechts) && region.isFlipX()){
            region.flip(true, false);
            rennenRechts = true;
        }

        statusTimer = currentState == previousState ? statusTimer + dt : 0;
        previousState = currentState;
        return region;

    }
    public State getState(){
        if(b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.SPRINGEN))
            return State.SPRINGEN;
        else if(b2body.getLinearVelocity().y < 0)
            return State.FALLEN;
        else if(b2body.getLinearVelocity().x != 0)
            return State.RENNEN;
        else
            return State.STEHEN;
    }




}
