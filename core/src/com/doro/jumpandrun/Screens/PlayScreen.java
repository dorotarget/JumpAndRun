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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.doro.jumpandrun.JumpAndRun;
import com.doro.jumpandrun.Scenes.Hud;
import com.doro.jumpandrun.Sprites.Extraherz;
import com.doro.jumpandrun.Sprites.Gegner;
import com.doro.jumpandrun.Sprites.Hero;
import com.doro.jumpandrun.Sprites.Muenzen;
import com.doro.jumpandrun.Sprites.PowerUp;
import com.doro.jumpandrun.Sprites.PowerUpDef;
import com.doro.jumpandrun.Tools.B2WorldCreator;
import com.doro.jumpandrun.Tools.WorldContactListener;

import java.util.concurrent.LinkedBlockingQueue;


public class PlayScreen implements Screen{

    private JumpAndRun game;
    private TextureAtlas atlas;
    private TextureAtlas heroAtlas;
    private TextureAtlas muenzenAtlas;
    private TextureAtlas muenzenHerzAtlas;
    private TextureAtlas fahrzeugeAtlas;



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
    private B2WorldCreator creator;

    //-----------Held
    private Hero heroSprite;


    private Array<PowerUp> powerUps;
    //private PriorityQueue<PowerUpDef> powerUpsToSpawn;
    //public LinkedBlockingQueue<PowerUpDef> powerUpsToSpawn;
    private LinkedBlockingQueue<PowerUpDef> powerUpsZuErstellen;

    public PlayScreen(JumpAndRun game){
       //atlas = new TextureAtlas("Mario_and_Enemies.pack");
       heroAtlas = new TextureAtlas("Hero_und_Bandit.pack");
        muenzenAtlas = new TextureAtlas("Muenzen.pack");
        muenzenHerzAtlas = new TextureAtlas("Muenzen_und_Herz.pack");
        fahrzeugeAtlas = new TextureAtlas("Fahrzeuge.pack");


        this.game = game;

        //---------------gamecamera wird erstellt
        gamecam = new OrthographicCamera();

        //----------------gleiches Verhältnis trotz veränderter screengröße
        gamePort = new FitViewport(JumpAndRun.V_WIDTH / JumpAndRun.PPM, JumpAndRun.V_HEIGHT / JumpAndRun.PPM, gamecam);

        //--------------hud wird gebaut
        hud = new Hud(game.batch);

        //----------map wird geladen
        maploader = new TmxMapLoader();
        map = maploader.load("Test2.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1  / JumpAndRun.PPM);

        //--------------gamecam ist anfangs am Mapbeginn
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        //--------Box2D-Welt wird erstellt, Gravitation
        world = new World(new Vector2(0, -10), true);

        //---------keine Ahnung? De
        b2dr = new Box2DDebugRenderer();


        creator = new B2WorldCreator(this);

        //-------Held wird in Welt erstellt
        heroSprite = new Hero(world, this);
        //gegner1 = new Gegner1(this, .32f, .32f);

        world.setContactListener(new WorldContactListener());

        powerUps = new Array<PowerUp>();
        //powerUpsToSpawn = new PriorityQueue<PowerUpDef>();
        //powerUpsToSpawn = new LinkedBlockingQueue<PowerUpDef>();
        powerUpsZuErstellen = new LinkedBlockingQueue<PowerUpDef>();

    }

    public void spawnPowerUp (PowerUpDef pudef){
        //powerUpsToSpawn.add(pudef);
        powerUpsZuErstellen.add(pudef);
    }

    public void handlePowerUpSpawns(){
        if(!powerUpsZuErstellen.isEmpty()){
            PowerUpDef idef = powerUpsZuErstellen.poll();
            if(idef.type == Extraherz.class){
                powerUps.add(new Extraherz(this, idef.position.x, idef.position.y));
            }
        }
    }

    // public TextureAtlas getAtlas(){ return atlas; }
    public TextureAtlas getHeroAtlas(){
        return heroAtlas;
    }
    public TextureAtlas getMuenzenAtlas(){
        return muenzenAtlas;
    }
    public TextureAtlas getMuenzenHerzAtlas(){
        return muenzenHerzAtlas;
    }

    public TextureAtlas getFahrzeugeAtlas(){
        return fahrzeugeAtlas;
    }



    @Override
    public void show() {


    }

    public void handleInput(float dt){
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP) && heroSprite.b2body.getLinearVelocity().y <= 0 && heroSprite.b2body.getLinearVelocity().y >= 0 && heroSprite.getState() != Hero.State.VERLETZT)
            heroSprite.b2body.applyLinearImpulse(new Vector2(0, 4f), heroSprite.b2body.getWorldCenter(), true);
        if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN) &&heroSprite.getState() != Hero.State.VERLETZT)
            heroSprite.b2body.applyLinearImpulse(new Vector2(0, -2f), heroSprite.b2body.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && heroSprite.b2body.getLinearVelocity().x <= 2 && heroSprite.getState() != Hero.State.VERLETZT)
            heroSprite.b2body.applyLinearImpulse(new Vector2(0.1f, 0), heroSprite.b2body.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && heroSprite.b2body.getLinearVelocity().x >= -2 && heroSprite.getState() != Hero.State.VERLETZT)
            heroSprite.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), heroSprite.b2body.getWorldCenter(), true);

    }

    public void update(float dt){

        //------------Input vom Spieler geht vor
        handleInput(dt);
        handlePowerUpSpawns();

        //-------------wie oft pro sekunde
        world.step(1 / 60f, 6, 2);

        heroSprite.update(dt);

        //-------Gegner und Münzen werden upgedatet
        for (Gegner gegner : creator.getGegner()) {
            gegner.update(dt);
            if (gegner.getX() < heroSprite.getX() + 2.75f)
                gegner.b2Body.setActive(true);
        }
        for (Muenzen muenzen : creator.getMuenzen()) {
            muenzen.update(dt);}

        for(PowerUp powerUp : powerUps)
            powerUp.update(dt);

        //----------gamecam bleibt bei Held
        gamecam.position.x = heroSprite.b2body.getPosition().x;

        //-------------aktualisiert gamecam
        gamecam.update();


//_-----------überprüfen, ob Hero gewonnen hat oder heruntergefallen ist
        if (Hero.gewonnen == true) {
            game.setScreen(new WinScreen(game));
        }
        if (Hero.unten ){
            game.setScreen(new LostScreen(game));
        }
        hud.update(dt);

        if ((Hero.verloren == true && heroSprite.getStatusTimer() > 2) | Hud.spielTimer < 1){

            game.setScreen(new LostScreen(game));
        }

        //-------------das, was man sieht, wird gerendert
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
        heroSprite.draw(game.batch);
        for (Gegner gegner : creator.getGegner())
            gegner.draw(game.batch);
        for (Muenzen muenzen : creator.getMuenzen())
            muenzen.draw(game.batch);
        /** wird erst funktionieren, wenn alles eingestellt ist
        for (Gegner gegner : creator.getGegner())
            gegner.draw(game.batch);
         */
        for (PowerUp powerUp : powerUps)
            powerUp.draw(game.batch);
        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();


        /*if ((Hero.verloren == true && heroSprite.getStatusTimer() > 2) | Hud.spielTimer < 1){

            game.setScreen(new LostScreen(game));
            dispose();
        }
*/


    }
   /* public boolean gameOver(){
        if(heroSprite.currentState == Hero.State.TOT && heroSprite.getStatusTimer() > 2){
            return true;
        }
        return false;
    }*/

    public boolean won(){

        return true;


    }



    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);

    }
    public TiledMap getMap(){
        return map;
    }
    public World getWorld(){
        return world;
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
    public Hud getHud(){ return hud; }

}
