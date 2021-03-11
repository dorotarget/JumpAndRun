package com.doro.jumpandrun.Sprites.PowerUps;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.doro.jumpandrun.JumpAndRun;
import com.doro.jumpandrun.Screens.PlayScreen;
import com.doro.jumpandrun.Sprites.Hero;

public class ExtraHerz extends PowerUp {

    public ExtraHerz(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        setRegion(screen.getAtlas().findRegion("turtle"), 0, 0, 16, 16);
        tempo = new Vector2(0.1f, 0);
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
        entfernen();
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        setPosition(body.getPosition().x-getWidth()/2, body.getPosition().y -  getHeight()/2);
        body.setLinearVelocity(tempo);
    }
}