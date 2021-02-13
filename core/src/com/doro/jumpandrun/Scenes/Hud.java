package com.doro.jumpandrun.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.doro.jumpandrun.JumpAndRun;


public class Hud implements Disposable{

    public Stage stage;
    private Viewport viewport;

    //------------Score und Zeit
    private Integer playTimer;
    private float timeCount;
    private static Integer score;

    //Scene2D widgets
    private Label countdownLabel;
    static Label scoreLabel;
    private Label liveLabel;
    private Label liveCountLabel;
    private Label timeLabel;
    private Label coinLabel;

    public Hud(SpriteBatch sb){
        //------------Variablen zu Beginn: Zeitguthaben, Zeitzähler, Punkte
        playTimer = 300;
        timeCount = 0;
        score = 0;


        //--------------neue Kamera für HUD viewport
        viewport = new FitViewport(JumpAndRun.V_WIDTH, JumpAndRun.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        //----------Tabelle für HUD-Elemente
        Table table = new Table();
        table.top();
        table.setFillParent(true);

        //--------------Label-Definitionen
        countdownLabel = new Label(String.format("%03d", playTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel =new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        liveLabel = new Label("Leben", new Label.LabelStyle(new BitmapFont(), Color.GREEN));
        liveCountLabel = new Label("3", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("Zeit", new Label.LabelStyle(new BitmapFont(), Color.GREEN));
        coinLabel = new Label("Coins", new Label.LabelStyle(new BitmapFont(), Color.GREEN));

        //-----------------Labels in tabelle anordnen und zu Stage packen
        table.add(coinLabel).expandX().padTop(10);
        table.add(liveLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);

        table.row();
        table.add(scoreLabel).expandX();
        table.add(liveCountLabel).expandX();
        table.add(countdownLabel).expandX();

        stage.addActor(table);

    }
    public void update (float dt){
        timeCount += dt;
        if(timeCount>=1){
            playTimer -=1;
            countdownLabel.setText(String.format("%03d", playTimer));
            timeCount = 0;
        }
    }
    public static void addScore(int value){
        score += value;
        scoreLabel.setText(String.format("%06d", score));
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
