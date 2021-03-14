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

public class Gegner3 extends Gegner
{
    @Override
    public void hitOnKopf() {
        setToDestroy = true;
    }

    private float statusZeit;
    private Animation<TextureRegion> laufAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;



    public Gegner3(PlayScreen screen, float x, float y) {
        super(screen,x+1, y);
        frames = new Array<TextureRegion>();
        for(int i = 0; i < 2; i++)
            //frames.add(new TextureRegion(screen.getAtlas().findRegion("gegner1"), i * 16, 0, 16, 16));

         //   frames.add(new TextureRegion(screen.getHeroAtlas().findRegion("Bandit_gehen"), 1+i * 64, 4, 64, 64));
        frames.add(new TextureRegion(screen.getFahrzeugeAtlas().findRegion("bus"), 1, 1, 900, 480));

        laufAnimation = new Animation(0.4f, frames);
        statusZeit = 0;
        setBounds(getX(), getY(), 130 / JumpAndRun.PPM, 70 / JumpAndRun.PPM);
        setToDestroy = false;
        tempo = new Vector2(-0.85f , 0);
        destroyed = false;




    }
    public TextureRegion getFrame(float dt){
        TextureRegion region;


        region = laufAnimation.getKeyFrame(statusZeit, true);


        if(tempo.x > 0 && region.isFlipX() == true && !setToDestroy){
            region.flip(false, false);
        }
        if(tempo.x < 0 && region.isFlipX() == false && !setToDestroy){
            region.flip(false, false);
        }


        return region;
    }
    public void update(float dt){
        if (!setToDestroy)
            setRegion(getFrame(dt));

        statusZeit += dt;
        if(setToDestroy && !destroyed){
            world.destroyBody(b2Body);
            destroyed = true;
            setRegion(new TextureRegion(screen.getHeroAtlas().findRegion("Bandit_sterben"), 1, -3, 64, 64));
            statusZeit = 0;
            Hud.addScore(500);

        }
        else if(!destroyed){
            tempo.y = b2Body.getLinearVelocity().y;

            b2Body.setLinearVelocity(tempo);
            setPosition(b2Body.getPosition().x - getWidth() / 2, b2Body.getPosition().y - getHeight() / 2);
            setRegion(laufAnimation.getKeyFrame(statusZeit, true));}
    }
    @Override
    protected void defineGegner(){

        BodyDef bdef = new BodyDef();
        bdef.position.set(getX() , getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2Body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
   //     CircleShape shape = new CircleShape();
   //     shape.setRadius(6 / JumpAndRun.PPM);
        PolygonShape shape = new PolygonShape();
        Vector2[] vertice = new Vector2[6];
        vertice[0] = new Vector2(-65, -23).scl(1 / JumpAndRun.PPM);
        vertice[1] = new Vector2(50, -23).scl(1 / JumpAndRun.PPM);
        vertice[2] = new Vector2(-65, 13).scl(1 / JumpAndRun.PPM);
        vertice[3] = new Vector2(50, 13).scl(1 / JumpAndRun.PPM);
        vertice[4] = new Vector2(-63, 25).scl(1 / JumpAndRun.PPM);
        vertice[5] = new Vector2(-63, 14).scl(1 / JumpAndRun.PPM);
        shape.set(vertice);
        fdef.filter.categoryBits = JumpAndRun.FAHRZEUG_BIT;
        fdef.filter.maskBits = JumpAndRun.BODEN_BIT | JumpAndRun.HERO_BIT; // | JumpAndRun.GEGNER_BIT;

        fdef.shape = shape;
        b2Body.createFixture(fdef).setUserData(this);


    }

    public void gegnerTrifftGegner(Gegner gegner){
            reverseVelocity(false, false);



    }
    public void gegnerTrifftHero(Gegner gegner){
        reverseVelocity(false, false);

    }
   // Verschwindet nach 1 sek
    public void draw(Batch batch){
        if(!destroyed || statusZeit < 1)
            super.draw(batch);
    }
}
