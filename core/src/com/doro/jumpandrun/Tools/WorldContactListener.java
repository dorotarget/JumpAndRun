package com.doro.jumpandrun.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.doro.jumpandrun.JumpAndRun;
import com.doro.jumpandrun.Screens.PlayScreen;
import com.doro.jumpandrun.Screens.WinScreen;
import com.doro.jumpandrun.Sprites.Hero;
import com.doro.jumpandrun.Screens.PlayScreen;
import com.doro.jumpandrun.Sprites.Muenzen;
import com.doro.jumpandrun.Sprites.PowerUp;
import com.doro.jumpandrun.Sprites.WinBrick;
import com.doro.jumpandrun.Sprites.Gegner;


public class WorldContactListener implements ContactListener {
    private JumpAndRun game;
    private PlayScreen screen;

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

/*
        if(fixA.getUserData() == "head" || fixB.getUserData() == "head"){
            Fixture head = fixA.getUserData() == "head" ? fixA : fixB;
            Fixture object = head == fixA ? fixB : fixA;

            if(object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())){
                ((InteractiveTileObject) object.getUserData()).onHeadHit();
            }

        }*/
        switch (cDef){
            case JumpAndRun.GEGNER_KOPF_BIT | JumpAndRun.HERO_BIT:
                if(fixA.getFilterData().categoryBits == JumpAndRun.GEGNER_KOPF_BIT)
                    ((Gegner)fixA.getUserData()).hitOnKopf();
                else
                    ((Gegner)fixB.getUserData()).hitOnKopf();
                break;
            case JumpAndRun.MUENZEN_BIT | JumpAndRun.HERO_BIT:
            case JumpAndRun.MUENZEN_BIT | JumpAndRun.HERO_KOPF_BIT:
                if(fixA.getFilterData().categoryBits == JumpAndRun.MUENZEN_BIT){
                    Gdx.app.log("Münze", "Silber");

                    ((Muenzen)fixA.getUserData()).eingesammelt();

                }
                else {
                    Gdx.app.log("Münze", "Silber");
                    ((Muenzen)fixB.getUserData()).eingesammelt();
                }
                break;
            case JumpAndRun.POWERUP_BIT | JumpAndRun.OBJEKT_BIT:
                if(fixA.getFilterData().categoryBits == JumpAndRun.POWERUP_BIT)
                    ((PowerUp)fixA.getUserData()).umdrehTempo(true, false);
                else
                    ((PowerUp)fixB.getUserData()).umdrehTempo(true, false);
                break;
            case JumpAndRun.POWERUP_BIT | JumpAndRun.HERO_BIT:
                if(fixA.getFilterData().categoryBits == JumpAndRun.POWERUP_BIT)
                    ((PowerUp)fixA.getUserData()).use();
                else
                    ((PowerUp)fixB.getUserData()).use();
                break;


            case JumpAndRun.GEGNER_BIT | JumpAndRun.OBJEKT_BIT:
                if(fixA.getFilterData().categoryBits == JumpAndRun.GEGNER_BIT)
                    ((Gegner)fixA.getUserData()).umdrehTempo(true, false);
                else
                    ((Gegner)fixB.getUserData()).umdrehTempo(true, false);
                break;
            case JumpAndRun.GEGNER_KOPF_BIT | JumpAndRun.OBJEKT_BIT:
                if(fixA.getFilterData().categoryBits == JumpAndRun.GEGNER_KOPF_BIT)
                    ((Gegner)fixA.getUserData()).umdrehTempo(true, false);
                else
                    ((Gegner)fixB.getUserData()).umdrehTempo(true, false);
                break;

            case JumpAndRun.GEGNER_BIT | JumpAndRun.GEGNER_BIT:
                //((Gegner)fixA.getUserData()).gegnerTrifftGegner((Gegner)fixB.getUserData());
                //((Gegner)fixB.getUserData()).gegnerTrifftGegner((Gegner)fixA.getUserData());
                ((Gegner)fixA.getUserData()).reverseVelocity(true, false);
                ((Gegner)fixB.getUserData()).reverseVelocity(true, false);
                break;

            case JumpAndRun.HERO_BIT | JumpAndRun.GEGNER_BIT:
                Gdx.app.log("HERO", "DIED");
                if(fixA.getFilterData().categoryBits == JumpAndRun.HERO_BIT) {
                    ((Hero) fixA.getUserData()).getroffen((Gegner) fixB.getUserData());
                    ((Gegner)fixB.getUserData()).umdrehTempo(true, false);
                }

                else {
                    ((Hero) fixB.getUserData()).getroffen((Gegner) fixA.getUserData());
                    ((Gegner) fixA.getUserData()).umdrehTempo(true, false);
                }

                    break;

            case JumpAndRun.HERO_BIT | JumpAndRun.BLOCK_BIT:

            case JumpAndRun.HERO_BIT | JumpAndRun.GEWINN_BIT:
                //((InteractiveTileObject) fixB.getUserData()).reachGoal((Hero) fixA.getUserData());
                //WinBrick.reachGoal();
                //screen.won();
                //game.setScreen(new WinScreen(game));
                Hero.won = true;

                //Gdx.app.log("Kontakt","Kontakt");


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
