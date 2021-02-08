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
import com.doro.jumpandrun.Sprites.WinBrick;


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
                ((InteractiveTileObject) object.getUserData()).onHeadHit(Hero);
            }

        }*/
        switch (cDef){
            case JumpAndRun.HERO_BIT | JumpAndRun.BLOCK_BIT:
            case JumpAndRun.HERO_KOPF_BIT | JumpAndRun.GELD_BIT:
                if(fixA.getFilterData().categoryBits == JumpAndRun.HERO_KOPF_BIT)
                    ((InteractiveTileObject) fixB.getUserData()).onHeadHit((Hero) fixA.getUserData());
                else
                    ((InteractiveTileObject) fixA.getUserData()).onHeadHit((Hero) fixB.getUserData());
                break;
            case JumpAndRun.HERO_BIT | JumpAndRun.GEWINN_BIT:
                //((InteractiveTileObject) fixB.getUserData()).reachGoal((Hero) fixA.getUserData());
                //WinBrick.reachGoal();
                //screen.won();
                //game.setScreen(new WinScreen(game));
                Hero.won = true;

                Gdx.app.log("Kontakt","Kontakt");
                // screen.dispose();
                // Hero.hit();
                //game.setScreen(new WinScreen(game));
                //dispose();
/*
            case JumpAndRun.ENEMY_HEAD_BIT | JumpAndRun.MARIO_BIT:
                if(fixA.getFilterData().categoryBits == JumpAndRun.ENEMY_HEAD_BIT)
                    ((Enemy)fixA.getUserData()).hitOnHead((Hero) fixB.getUserData());
                else
                    ((Enemy)fixB.getUserData()).hitOnHead((Hero) fixA.getUserData());
                break;
            case MarioBros.ENEMY_BIT | MarioBros.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == MarioBros.ENEMY_BIT)
                    ((Enemy)fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy)fixB.getUserData()).reverseVelocity(true, false);
                break;
            case MarioBros.MARIO_BIT | MarioBros.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits == MarioBros.MARIO_BIT)
                    ((Mario) fixA.getUserData()).hit((Enemy)fixB.getUserData());
                else
                    ((Mario) fixA.getUserData()).hit((Enemy)fixA.getUserData());
                break;
            case MarioBros.ENEMY_BIT | MarioBros.ENEMY_BIT:
                ((Enemy)fixA.getUserData()).reverseVelocity(true, false);
                ((Enemy)fixB.getUserData()).reverseVelocity(true, false);
                break;
            case MarioBros.ITEM_BIT | MarioBros.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == MarioBros.ITEM_BIT)
                    ((Item)fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Item)fixB.getUserData()).reverseVelocity(true, false);
                break;
            case MarioBros.ITEM_BIT | MarioBros.MARIO_BIT:
                if(fixA.getFilterData().categoryBits == MarioBros.ITEM_BIT)
                    ((Item)fixA.getUserData()).use((Mario) fixB.getUserData());
                else
                    ((Item)fixB.getUserData()).use((Mario) fixA.getUserData());
                break;
*/
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
