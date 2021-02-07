package com.doro.jumpandrun.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.doro.jumpandrun.JumpAndRun;
import com.doro.jumpandrun.Sprites.Gegner;


public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        if(fixA.getUserData() == "head" || fixB.getUserData() == "head"){
            Fixture head = fixA.getUserData() == "head" ? fixA : fixB;
            Fixture object = head == fixA ? fixB : fixA;

            if(object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())){
                ((InteractiveTileObject) object.getUserData()).onHeadHit();
            }

        }
        switch (cDef){
            case JumpAndRun.GEGNER_KOPF_BIT | JumpAndRun.HERO_BIT:
                if(fixA.getFilterData().categoryBits == JumpAndRun.GEGNER_KOPF_BIT)
                    ((Gegner)fixA.getUserData()).hitOnKopf();
                else
                    ((Gegner)fixB.getUserData()).hitOnKopf();
                break;
            case JumpAndRun.GEGNER_BIT | JumpAndRun.OBJEKT_BIT:
                if(fixA.getFilterData().categoryBits == JumpAndRun.GEGNER_BIT)
                    ((Gegner)fixA.getUserData()).umdrehTempo(true, false);
                else
                    ((Gegner)fixB.getUserData()).umdrehTempo(true, false);
                break;
            case JumpAndRun.HERO_BIT | JumpAndRun.GEGNER_BIT:
                Gdx.app.log("HERO", "DIED");
        }
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
