package ru.broject.newyear;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import ru.broject.newyear.gameobject.DialogHandler;
import ru.broject.newyear.gameobject.img.*;
import ru.broject.newyear.gameobject.obj.*;
import ru.broject.newyear.support.Constants;

import java.util.Iterator;

/**
 * Created by vyacheslav.svininyh on 29.12.2015.
 */
public class GameScreen implements Screen {

    private NewYearGame game;

    private Snowman snowman;
    private SnowmanImage snowmanImage;
    private DropImage dropImage;
    private DedMoroz dedMoroz;
    private DedMorozImage dedMorozImage;
    private Tree treeBefore;
    private Tree treeAfter;
    private TreeImage treeImage;
    private StarsImage starsImage;
    private MoonImage moonImage;
    private RabbitImage rabbitImage;
    private SnowTreeImage snowTreeImage;
    private SnowflakeImage snowflakeImage;
    private Array<Raindrop> raindrops;
    private Array<Snowflake> snowflakes;
    private OrthographicCamera camera;
    private Label snowmanDialog;
    private Label dedMorozDialog;
    private Music rainMusic;
    private Music snowMusic;

    private DialogHandler dialogHandler;
    private int snowmanDialogIteration = 0;
    private int dedMorozDialogIteration = 0;
    private long lastDropTime;
    private long lastSnowflakeDrop;
    private long snowflakeSideTime;
    private int speed = Constants.DEFAULT_SPEED;
    private int timeBetweenDrops = Constants.DEFAULT_TIME_BETWEEN_DROPS;
    private int worldGeneratorAfterCheckpoint = 1;
    private int worldGeneratorBeforeCheckpoint = -1;

    public GameScreen(final NewYearGame game) {
        this.game = game;

        dialogHandler = new DialogHandler();

        snowmanImage = new SnowmanImage();
        snowman = new Snowman();
        dropImage = new DropImage();
        dedMorozImage = new DedMorozImage();
        dedMoroz = new DedMoroz();
        treeImage = new TreeImage();
        snowTreeImage = new SnowTreeImage();
        treeBefore = new Tree(0);
        treeAfter = new Tree(0);
        snowflakeImage = new SnowflakeImage();
        starsImage = new StarsImage();
        moonImage = new MoonImage();
        rabbitImage = new RabbitImage();
        Label.LabelStyle ls = new Label.LabelStyle(game.font25white, Color.WHITE);
        snowmanDialog = new Label("", ls);
        dedMorozDialog = new Label("", ls);

        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("music/rain.mp3"));
        rainMusic.setLooping(true);
        snowMusic = Gdx.audio.newMusic(Gdx.files.internal("music/snow.mp3"));
        snowMusic.setLooping(true);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

        raindrops = new Array<Raindrop>();
        spawnRaindrop();
        snowflakes = new Array<Snowflake>();
    }

    private void spawnRaindrop() {
        Raindrop raindrop = new Raindrop(snowman.getShape().x);
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

    private void spawnSnowfall() {
        Snowflake snowflake = new Snowflake(snowman.getShape().x);
        snowflakes.add(snowflake);
        lastSnowflakeDrop = TimeUtils.nanoTime();
    }

    @Override
    public void show() {
        rainMusic.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.position.set(snowman.getShape().x, Constants.WINDOW_HEIGHT / 2, 0);
        camera.update();

        snowmanDialog.setPosition(snowman.getShape().x - snowman.getShape().width * 3, (float) (snowman.getShape().y + snowman.getShape().height * 1.3));
        dedMorozDialog.setPosition(dedMoroz.getShape().x, (float) (dedMoroz.getShape().y + dedMoroz.getShape().height * 1.3));

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font25white.draw(game.batch, "snowpoints:" + snowman.getSnowpointsCount(), snowman.getShape().x - Constants.WINDOW_WIDTH / 2 + 20, Constants.WINDOW_HEIGHT - 20);
        game.batch.draw(starsImage.getImage(), (float) (snowman.getShape().x - Constants.WINDOW_WIDTH / 2.2), (float) (Constants.WINDOW_HEIGHT * 0.55));
        game.batch.draw(moonImage.getImage(), (float) (snowman.getShape().x + Constants.WINDOW_WIDTH / 4), (float) (Constants.WINDOW_HEIGHT * 0.7));
        game.batch.draw(rabbitImage.getImage(), (float) (Constants.WINDOW_WIDTH * 5.45), 0);
        if (!dialogHandler.isComplete()) {
            game.batch.draw(treeImage.getImage(), treeBefore.getShape().x, treeBefore.getShape().y);
            game.batch.draw(treeImage.getImage(), treeAfter.getShape().x, treeAfter.getShape().y);
        } else {
            game.batch.draw(snowTreeImage.getImage(), treeBefore.getShape().x, treeBefore.getShape().y);
            game.batch.draw(snowTreeImage.getImage(), treeAfter.getShape().x, treeAfter.getShape().y);
        }

        if (dialogHandler.isActive()) {
            if (snowmanDialogIteration == DialogHandler.snowmanDialogs.size() - 1
                    && dedMorozDialogIteration == DialogHandler.dedMorozDialogs.size()) {

                dialogHandler.setActive(false);
                dialogHandler.setComplete(true);
                spawnSnowfall();
                rainMusic.pause();
                snowMusic.play();
            } else {
                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    if (snowmanDialog.getText().length() == 0) {
                        snowmanDialogIteration++;
                        dedMorozDialog.setText("");
                        snowmanDialog.setText(DialogHandler.snowmanDialogs.get(snowmanDialogIteration));
                    } else {
                        snowmanDialog.setText("");
                        dedMorozDialog.setText(DialogHandler.dedMorozDialogs.get(dedMorozDialogIteration));
                        dedMorozDialogIteration++;
                    }
                }
            }
            snowmanDialog.draw(game.batch, 20);
            dedMorozDialog.draw(game.batch, 20);
        }
        game.batch.draw(snowmanImage.getImage(), snowman.getShape().x, snowman.getShape().y);
        if (!dialogHandler.isComplete()) {
            for (Raindrop raindrop : raindrops) {
                game.batch.draw(dropImage.getImage(), raindrop.getShape().x, raindrop.getShape().y);
            }
        } else {
            for (Snowflake snowflake : snowflakes) {
                game.batch.draw(snowflakeImage.getImage(), snowflake.getShape().x, snowflake.getShape().y);
            }
        }
        game.batch.draw(dedMorozImage.getImage(), dedMoroz.getShape().x, dedMoroz.getShape().y);
        game.batch.end();

        if (snowman.getShape().x > worldGeneratorAfterCheckpoint) {
            worldGeneratorBeforeCheckpoint = worldGeneratorAfterCheckpoint;
            worldGeneratorAfterCheckpoint += Constants.WINDOW_WIDTH * 1.3;
            treeAfter = new Tree(worldGeneratorAfterCheckpoint);
            treeBefore = new Tree(worldGeneratorBeforeCheckpoint);
        }

        if (snowman.getShape().x < worldGeneratorBeforeCheckpoint) {
            worldGeneratorAfterCheckpoint = worldGeneratorBeforeCheckpoint;
            worldGeneratorBeforeCheckpoint -= Constants.WINDOW_WIDTH * 1.3;
            treeAfter = new Tree(worldGeneratorAfterCheckpoint);
            treeBefore = new Tree(worldGeneratorBeforeCheckpoint);
        }

        if (snowman.getShape().x < -Constants.WINDOW_WIDTH / 4) {
            snowman.getShape().x = -Constants.WINDOW_WIDTH / 4;
        }

        if (!dialogHandler.isActive()) {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                snowman.getShape().x -= 350 * Gdx.graphics.getDeltaTime();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                snowman.getShape().x += 350 * Gdx.graphics.getDeltaTime();
            }
        }

        if (snowman.getSnowpointsCount() <= 0) {
            game.setScreen(new GameOverScreen(game));
            dispose();
        } else if (snowman.getSnowpointsCount() >= 100) {
            game.setScreen(new VictoryScreen(game));
            dispose();
        }

        if (!dialogHandler.isComplete()) {
            if (TimeUtils.nanoTime() - lastDropTime > timeBetweenDrops) {
                spawnRaindrop();
                if (speed < Constants.MAX_SPEED) {
                    speed += Constants.DEFAULT_SPEED / 75;
                }
                if (timeBetweenDrops > Constants.MIN_TIME_BETWEEN_DROPS) {
                    timeBetweenDrops -= Constants.DEFAULT_TIME_BETWEEN_DROPS / 33;
                }
            }
        } else {
            if (TimeUtils.nanoTime() - lastSnowflakeDrop > 200000000) {
                spawnSnowfall();
            }
        }

        if (dedMoroz.getShape().x - snowman.getShape().x < dedMoroz.getShape().width * 1.5) {
            if (snowman.getSnowpointsCount() < 20) {
                snowman.setSnowpointsCount(20);
            }
            if (dedMoroz.getShape().x - snowman.getShape().x < dedMoroz.getShape().width * 1.2) {
                snowman.getShape().x = (float) (dedMoroz.getShape().x - dedMoroz.getShape().width * 1.2);
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !dialogHandler.isActive() && !dialogHandler.isComplete()) {
                snowmanDialog.setText(DialogHandler.snowmanDialogs.get(snowmanDialogIteration));
                dialogHandler.setActive(true);
            }
        }

        if (dialogHandler.isComplete()) {
            if (TimeUtils.nanoTime() - snowflakeSideTime > 2000000000L) {
                snowflakeSideTime = TimeUtils.nanoTime();
            }
        }

        if (!dialogHandler.isComplete()) {
            Iterator<Raindrop> iter = raindrops.iterator();
            while (iter.hasNext()) {
                Rectangle raindrop = iter.next().getShape();
                raindrop.y -= speed * Gdx.graphics.getDeltaTime();
                if (raindrop.y + raindrop.height < 0
                        || raindrop.overlaps(treeBefore.getShape())
                        || raindrop.overlaps(treeAfter.getShape())
                        || raindrop.overlaps(dedMoroz.getShape())) {

                    iter.remove();
                } else if (raindrop.overlaps(snowman.getShape())) {
                    iter.remove();
                    snowman.setSnowpointsCount(snowman.getSnowpointsCount() - 5);
                }
            }
        } else {
            Iterator<Snowflake> iter = snowflakes.iterator();
            while (iter.hasNext()) {
                Rectangle snowflake = iter.next().getShape();
                snowflake.y -= 200 * Gdx.graphics.getDeltaTime();

                if (TimeUtils.nanoTime() - snowflakeSideTime < 1000000000L) {
                    snowflake.x -= 300 * Gdx.graphics.getDeltaTime();
                } else {
                    snowflake.x += 300 * Gdx.graphics.getDeltaTime();
                }
                if (snowflake.y + snowflake.height < 0
                        || snowflake.overlaps(treeBefore.getShape())
                        || snowflake.overlaps(treeAfter.getShape())
                        || snowflake.overlaps(dedMoroz.getShape())) {

                    iter.remove();
                } else if (snowflake.overlaps(snowman.getShape())) {
                    iter.remove();
                    snowman.setSnowpointsCount(snowman.getSnowpointsCount() + 2);
                }
            }
        }
    }

    @Override
    public void resize(int width, int height) {

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
        dropImage.getImage().dispose();
        snowmanImage.getImage().dispose();
        dedMorozImage.getImage().dispose();
        treeImage.getImage().dispose();
        snowTreeImage.getImage().dispose();
        snowflakeImage.getImage().dispose();
        rainMusic.dispose();
        snowMusic.dispose();
    }
}
