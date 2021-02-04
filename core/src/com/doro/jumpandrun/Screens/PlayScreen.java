package com.doro.jumpandrun.Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.doro.jumpandrun.JumpAndRun;
import com.doro.jumpandrun.Scenes.Hud;
import com.doro.jumpandrun.Sprites.Hero;
import com.doro.jumpandrun.Tools.B2WorldCreator;
import com.doro.jumpandrun.Tools.WorldContactListener;


public class PlayScreen implements Screen{

    private JumpAndRun game;
    private TextureAtlas atlas;

    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;

    //-------------Variablen für TiledMap
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //----------Variablen für Box2D
    private World world;
    private Box2DDebugRenderer b2dr;


    //-----------Held
    private Hero player;


    public PlayScreen(JumpAndRun game){
       atlas = new TextureAtlas("Mario_and_Enemies.pack");

        this.game = game;

        //---------------gamecamera wird erstellt
        gamecam = new OrthographicCamera();

        //----------------gleiches Verhältnis trotz veränderter screengröße
        gamePort = new FitViewport(JumpAndRun.V_WIDTH / JumpAndRun.PPM, JumpAndRun.V_HEIGHT / JumpAndRun.PPM, gamecam);

        //--------------hud wird gebaut
        hud = new Hud(game.batch);

        //----------map wird geladen
        maploader = new TmxMapLoader();
        map = maploader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1  / JumpAndRun.PPM);

        //--------------gamecam ist anfangs am Mapbeginn
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        //--------Box2D-Welt wird erstellt, Gravitation
        world = new World(new Vector2(0, -10), true);

        //---------keine Ahnung? De
        b2dr = new Box2DDebugRenderer();


        new B2WorldCreator(world, map);

        //-------Held wird in Welt erstellt
        player = new Hero(world, this);

        world.setContactListener(new WorldContactListener());
    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    @Override
    public void show() {


    }

    public void handleInput(float dt){
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP))
            player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
        if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN))
            player.b2body.applyLinearImpulse(new Vector2(0, -2f), player.b2body.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2)
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2)
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);

    }
    //testkomentar2

    public void update(float dt){

        //------------Input vom Spieler geht vor
        handleInput(dt);

        //-------------wie oft pro sekunde
        world.step(1 / 60f, 6, 2);

        player.update(dt);

        //----------gamecam bleibt bei Held
        gamecam.position.x = player.b2body.getPosition().x;

        //-------------aktualisiert gamecam
        gamecam.update();
        //-------------das was man sieht wird gerendert
        renderer.setView(gamecam);
    }

    @Override
    public void render(float delta) {
        update(delta);


        //--------------game screen wird mit schwarz
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        //render GameMap und Box2DDebugLines
        renderer.render();
        b2dr.render(world, gamecam.combined);


        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();

    }
}
