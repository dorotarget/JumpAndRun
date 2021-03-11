package com.doro.jumpandrun.Sprites.PowerUps;

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
import com.doro.jumpandrun.Scenes.Hud;
import com.doro.jumpandrun.Screens.PlayScreen;
import com.doro.jumpandrun.Sprites.Hero;

public class ExtraHerz extends PowerUp {
    private float statusZeit;

    private Animation<TextureRegion> blinkAnimation;
    private Array<TextureRegion> frames;
    private boolean zuEntfernen;
    private boolean entfernt;

    public ExtraHerz(PlayScreen screen, float x, float y) {
        super(screen, x + 1, y);
        frames = new Array<TextureRegion>();
        for(int i = 0; i < 2; i++)

            frames.add(new TextureRegion(screen.getMuenzenHerzAtlas().findRegion("Herz"), i * 64, 1, 32, 32));

        blinkAnimation = new Animation(0.4f, frames);
        statusZeit = 0;
        setBounds(getX(), getY(), 16 / JumpAndRun.PPM, 16 / JumpAndRun.PPM);
        zuEntfernen = false;

        entfernt = false;


    }

    public TextureRegion getFrame(float dt){
        TextureRegion region;


        region = blinkAnimation.getKeyFrame(statusZeit, true);

        //setRegion(screen.getMuenzenHerzAtlas().findRegion("Herz"), 0, 0, 64, 64);
        tempo = new Vector2(0.2f, 0);

        return region;
    }
    public void update(float dt){
        if (!zuEntfernen)
            setRegion(getFrame(dt));

        statusZeit += dt;
        if(zuEntfernen && !entfernt){
            world.destroyBody(body);
            entfernt = true;
            statusZeit = 0;
            Hud.gewinneLeben();

        }
        else if(!entfernt){
            body.setLinearVelocity(tempo);
            //body.applyForceToCenter(0,10, true);
            setPosition(body.getPosition().x - ( getWidth() / 2), body.getPosition().y - getHeight() / 2);
            setRegion(blinkAnimation.getKeyFrame(statusZeit, true));}
    }

    public void eingesammelt() {
        zuEntfernen = true;
    }

    @Override
    public void definierePowerUp() {

        BodyDef bdef = new BodyDef();
        bdef.position.set(getX() , getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(2/JumpAndRun.PPM);

        fdef.filter.categoryBits = JumpAndRun.ITEM_BIT;
        fdef.filter.maskBits = JumpAndRun.BODEN_BIT | JumpAndRun.OBJEKT_BIT | JumpAndRun.HERO_BIT;

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);


    }

    @Override
    public void use(Hero hero) {

    }

    public void draw(Batch batch){
        if(!entfernt || statusZeit < 0.1f)
            super.draw(batch);
    }

  /*  @Override
    public void use(Hero hero) {
        entfernen();
    }*/
/*
    @Override
    public void update(float dt) {
        super.update(dt);
        setPosition(body.getPosition().x-getWidth()/2, body.getPosition().y -  getHeight()/2);
        body.setLinearVelocity(tempo);
    }*/
}