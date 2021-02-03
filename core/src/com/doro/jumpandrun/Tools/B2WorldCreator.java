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
import com.doro.jumpandrun.JumpAndRun;


public class B2WorldCreator {
    public B2WorldCreator(World world, TiledMap map){

        //-------------Körper
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //------------Boden
        for(MapObject object : map.getLayers().get(0).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / JumpAndRun.PPM, (rect.getY() + rect.getHeight() / 2) / JumpAndRun.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / JumpAndRun.PPM, rect.getHeight() / 2 / JumpAndRun.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }


        //-----------------Hindernisse
        for(MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / JumpAndRun.PPM, (rect.getY() + rect.getHeight() / 2) / JumpAndRun.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / JumpAndRun.PPM, rect.getHeight() / 2 / JumpAndRun.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //---------------Blöcke
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / JumpAndRun.PPM, (rect.getY() + rect.getHeight() / 2) / JumpAndRun.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / JumpAndRun.PPM, rect.getHeight() / 2 / JumpAndRun.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //----------------Münzen/Sonderblöcke
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            //new Coin(world, map, rect);

        }
    }
}
