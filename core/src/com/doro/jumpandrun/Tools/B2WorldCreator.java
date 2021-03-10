package com.doro.jumpandrun.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.doro.jumpandrun.JumpAndRun;
import com.doro.jumpandrun.Screens.PlayScreen;
import com.doro.jumpandrun.Sprites.Gegner1;
import com.doro.jumpandrun.Sprites.Muenzen;
import com.doro.jumpandrun.Sprites.MuenzenGold;
import com.doro.jumpandrun.Sprites.MuenzenSilber;


public class B2WorldCreator {
    private Array<Gegner1> gegner1Array;
    private Array<MuenzenSilber> muenzenSilberArray;
    private Array<MuenzenGold> muenzenGoldArray;


    public B2WorldCreator(PlayScreen screen){
        World world = screen.getWorld();
        TiledMap map = screen.getMap();

        //-------------Körper
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //------------Boden
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / JumpAndRun.PPM, (rect.getY() + rect.getHeight() / 2) / JumpAndRun.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / JumpAndRun.PPM, rect.getHeight() / 2 / JumpAndRun.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }


        //-----------------Hindernisse
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / JumpAndRun.PPM, (rect.getY() + rect.getHeight() / 2) / JumpAndRun.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / JumpAndRun.PPM, rect.getHeight() / 2 / JumpAndRun.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = JumpAndRun.OBJEKT_BIT;
            body.createFixture(fdef);
        }
        //-----------gegner1

        gegner1Array = new Array<Gegner1>();
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            gegner1Array.add(new Gegner1(screen, rect.getX() / JumpAndRun.PPM, rect.getY() / JumpAndRun.PPM));

        }
        //-----------SilberMünzen

        muenzenSilberArray = new Array<MuenzenSilber>();
        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            muenzenSilberArray.add(new MuenzenSilber(screen, rect.getX() / JumpAndRun.PPM, rect.getY() / JumpAndRun.PPM));

        }
        //-----------Goldmünzen

         muenzenGoldArray = new Array<MuenzenGold>();
        for(MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            muenzenGoldArray.add(new MuenzenGold(screen, rect.getX() / JumpAndRun.PPM, rect.getY() / JumpAndRun.PPM));

        }


        /*
        //---------------Blöcke
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / JumpAndRun.PPM, (rect.getY() + rect.getHeight() / 2) / JumpAndRun.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / JumpAndRun.PPM, rect.getHeight() / 2 / JumpAndRun.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //----------------Münzen/Sonderblöcke
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            //new Coin(world, map, rect);


        }*/



//------------winblock

        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2)/ JumpAndRun.PPM, (rect.getY() + rect.getHeight() / 2)/ JumpAndRun.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2/ JumpAndRun.PPM, rect.getHeight() / 2/ JumpAndRun.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = JumpAndRun.GEWINN_BIT;
            body.createFixture(fdef);
        }
    }

    public Array<Gegner1> getGegner1Array() {
        return gegner1Array;
    }
    public Array<MuenzenSilber> getMuenzenSilberArray() {
        return muenzenSilberArray;
    }
    public Array<MuenzenGold> getMuenzenGoldArray() {
        return muenzenGoldArray;
    }
    public Array<Muenzen> getMuenzen(){
        Array<Muenzen> muenzen = new Array<Muenzen>();
        muenzen.addAll(muenzenSilberArray);
        muenzen.addAll(muenzenGoldArray);
        return muenzen;
    }

}


