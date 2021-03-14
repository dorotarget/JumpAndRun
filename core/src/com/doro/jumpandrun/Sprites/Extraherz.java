package com.doro.jumpandrun.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.doro.jumpandrun.JumpAndRun;
import com.doro.jumpandrun.Scenes.Hud;
import com.doro.jumpandrun.Screens.PlayScreen;

public class Extraherz extends PowerUp{

    private boolean entfernt;
    /**----------------------*/
    private float statusZeit;
    private Animation<TextureRegion> blinkAnimation;
    private Array<TextureRegion> frames;
    /**----------------------*/
    public Extraherz(PlayScreen screen, float x, float y) {

        super(screen, x, y);
        //setRegion(screen.getMuenzenHerzAtlas().findRegion("Herz"), 0,0,32,32);
        /**----------------------*/

        frames = new Array<TextureRegion>();
        for(int i = 0; i < 2; i++)

            frames.add(new TextureRegion(screen.getMuenzenHerzAtlas().findRegion("Herz"), i * 64, 1, 32, 32));

        blinkAnimation = new Animation(0.4f, frames);
        statusZeit = 0;
        setBounds(getX(), getY(), 16 / JumpAndRun.PPM, 16 / JumpAndRun.PPM);
        /**----------------------*/
        tempo = new Vector2(0.7f,0);
        entfernt = false;


    }
    /**----------------------*/

    public TextureRegion getFrame(float dt){
        TextureRegion region;


        region = blinkAnimation.getKeyFrame(statusZeit, true);

        //setRegion(screen.getMuenzenHerzAtlas().findRegion("Herz"), 0, 0, 64, 64);
        //tempo = new Vector2(0.2f, 40);

        return region;
        /**----------------------*/

    }


    @Override
    public void definePowerUp() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5/ JumpAndRun.PPM);

        fdef.filter.categoryBits = JumpAndRun.POWERUP_BIT;
        fdef.filter.maskBits = JumpAndRun.HERO_BIT |
                JumpAndRun.BODEN_BIT|
                JumpAndRun.OBJEKT_BIT;

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void use() {
        if (!entfernt)
            Hud.gewinneLeben(1);
        entfernen();


    }

    @Override
    public void update(float dt) {
        super.update(dt);
        if (statusZeit > 3)
            entfernen();
        setPosition(body.getPosition().x-getHeight()/2,body.getPosition().x-getHeight()/2);
        //setPosition(1,1);

        setPosition(body.getPosition().x - ( getWidth() / 2), body.getPosition().y - getHeight() / 2);
        setRegion(blinkAnimation.getKeyFrame(statusZeit, true));
        tempo.y = body.getLinearVelocity().y;
        body.setLinearVelocity(tempo);

        statusZeit += dt;

    }

    @Override
    public void draw(Batch batch) {
        super.draw(batch);
    }

    @Override
    public void entfernen() {
        if (!entfernt) {
            super.entfernen();
            entfernt = true;
        }
    }
}

