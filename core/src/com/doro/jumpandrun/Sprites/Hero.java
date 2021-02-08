package com.doro.jumpandrun.Sprites;


import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.doro.jumpandrun.JumpAndRun;
import com.doro.jumpandrun.Screens.PlayScreen;

public class Hero extends Sprite {
    public static boolean won;

    public enum State { WINNER, ELSE }
    public State currentState;
    public State previousState;


    public World world;
    public Body b2body;
    private TextureRegion heroStand;



    public void hit(){
        won = true;

    }



    public Hero(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("little_mario"));
        this.world = world;

        won = false;


        heroStand = new TextureRegion(getTexture(), 339, 27, 16, 16);

        defineHero();
        setBounds(0, 0, 16 / JumpAndRun.PPM, 16 / JumpAndRun.PPM);
        setRegion(heroStand);

    }




    public void defineHero(){

        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / JumpAndRun.PPM, 32 / JumpAndRun.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / JumpAndRun.PPM);

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

    }




}
