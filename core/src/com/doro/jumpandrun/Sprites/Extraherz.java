package com.doro.jumpandrun.Sprites;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.doro.jumpandrun.JumpAndRun;
import com.doro.jumpandrun.Screens.PlayScreen;

public class Extraherz extends PowerUp{
    public Extraherz(PlayScreen screen, float x, float y) {

        super(screen, x, y);
        setRegion(screen.getMuenzenHerzAtlas().findRegion("Herz"), 0,0,32,32);
        tempo = new Vector2(0,0);
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

        fdef.filter.categoryBits = JumpAndRun.ITEM_BIT;
        fdef.filter.maskBits = JumpAndRun.HERO_BIT |
                JumpAndRun.BODEN_BIT;

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void use() {
        entfernen();

    }

    @Override
    public void update(float dt) {
        super.update(dt);
        setPosition(body.getPosition().x-getHeight()/2,body.getPosition().x-getHeight()/2);
        //setPosition(1,1);
        body.setLinearVelocity(tempo);
    }@Override
    public void draw(Batch batch) {
        super.draw(batch);
    }

    @Override
    public void entfernen() {
        super.entfernen();
    }
}

