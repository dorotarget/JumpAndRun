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

    public enum State { WINNER, ELSE };
    public State currentState;
    public State previousState;


    public World world;
    public Body b2body;
    private TextureRegion heroStand;

    private boolean hasWon;


    public void hit(){
        won = true;

    }
    public State getState(){
        //Test to Box2D for velocity on the X and Y-Axis
        //if mario is going positive in Y-Axis he is jumping... or if he just jumped and is falling remain in jump state
        if(hasWon)
            return State.WINNER;
        else
            return State.ELSE;
    }

    public boolean isDead(){
        return hasWon;
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
        fdef.filter.categoryBits = JumpAndRun.MARIO_BIT;
        fdef.filter.maskBits = JumpAndRun.GROUND_BIT |
                JumpAndRun.COIN_BIT |
                JumpAndRun.BRICK_BIT |
                JumpAndRun.ENEMY_BIT |
                JumpAndRun.OBJECT_BIT |
                JumpAndRun.ENEMY_HEAD_BIT |
                JumpAndRun.WINNING_BIT |
                JumpAndRun.ITEM_BIT;


        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / JumpAndRun.PPM, 6 / JumpAndRun.PPM), new Vector2(2 / JumpAndRun.PPM, 6 / JumpAndRun.PPM));
        fdef.filter.categoryBits = JumpAndRun.MARIO_HEAD_BIT;
        fdef.shape = head;
        fdef.isSensor = true;

        //b2body.createFixture(fdef).setUserData("head");
    }
    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);

    }


}
