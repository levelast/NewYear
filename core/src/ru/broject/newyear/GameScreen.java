package ru.broject.newyear;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import ru.broject.newyear.gameobject.DialogHandler;
import ru.broject.newyear.gameobject.img.DedMorozImage;
import ru.broject.newyear.gameobject.img.DropImage;
import ru.broject.newyear.gameobject.img.SnowmanImage;
import ru.broject.newyear.gameobject.obj.DedMoroz;
import ru.broject.newyear.gameobject.obj.Drop;
import ru.broject.newyear.gameobject.obj.Snowman;
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
    private Texture backgroundTexture;
    private Sprite backgroundSprite;
    private Array<Drop> raindrops;
    private OrthographicCamera camera;
    private Label snowmanDialog;
    private Label dedMorozDialog;


    private DialogHandler dialogHandler;
    private int snowmanDialogIteration = 0;
    private int dedMorozDialogIteration = 0;
    private long lastDropTime;
    private int speed = Constants.DEFAULT_SPEED;
    private int timeBetweenDrops = Constants.DEFAULT_TIME_BETWEEN_DROPS;


    public GameScreen(final NewYearGame game) {
        this.game = game;

        dialogHandler = new DialogHandler();

        snowmanImage = new SnowmanImage();
        snowman = new Snowman();
        dropImage = new DropImage();
        dedMorozImage = new DedMorozImage();
        dedMoroz = new DedMoroz();
        backgroundTexture = new Texture(Gdx.files.internal("background.jpg"));
        backgroundSprite = new Sprite(backgroundTexture);
        Label.LabelStyle ls = new Label.LabelStyle(game.font25white, Color.WHITE);
        snowmanDialog = new Label("", ls);
        dedMorozDialog = new Label("", ls);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

        raindrops = new Array<Drop>();
        spawnRaindrop();
    }

    private void spawnRaindrop() {
        Drop raindrop = new Drop(snowman.getShape().x);
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void show() {
//        rainMusic.play();
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
        if (dialogHandler.isActive()) {
            if (snowmanDialogIteration == DialogHandler.snowmanDialogs.size() - 1
                    && dedMorozDialogIteration == DialogHandler.dedMorozDialogs.size()) {

                dialogHandler.setActive(false);
                dialogHandler.setComplete(true);
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
//            game.font25white.draw(game.batch, "What about\n some snow?", snowman.getShape().x - snowman.getShape().width, (float) (snowman.getShape().y + snowman.getShape().height * 1.3));
        }
//        game.batch.draw(backgroundSprite, backgroundSprite.getX(), backgroundSprite.getY());
        game.batch.draw(snowmanImage.getImage(), snowman.getShape().x, snowman.getShape().y);
        for (Drop raindrop : raindrops) {
            game.batch.draw(dropImage.getImage(), raindrop.getShape().x, raindrop.getShape().y);
        }
        game.batch.draw(dedMorozImage.getImage(), dedMoroz.getShape().x, dedMoroz.getShape().y);
        game.batch.end();

        if (!dialogHandler.isActive()) {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                snowman.getShape().x -= 900 * Gdx.graphics.getDeltaTime();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                snowman.getShape().x += 900 * Gdx.graphics.getDeltaTime();
            }
        }

        if (snowman.getSnowpointsCount() <= 0) {
            game.setScreen(new GameOverScreen(game));
            dispose();
        }

        if (TimeUtils.nanoTime() - lastDropTime > timeBetweenDrops) {
            spawnRaindrop();
            if (speed < Constants.MAX_SPEED) {
                speed += Constants.DEFAULT_SPEED / 30;
            }
            if (timeBetweenDrops > Constants.MIN_TIME_BETWEEN_DROPS) {
                timeBetweenDrops -= Constants.DEFAULT_TIME_BETWEEN_DROPS / 30;
            }
        }

        if (dedMoroz.getShape().x - snowman.getShape().x < dedMoroz.getShape().width * 1.5) {
            snowman.setSnowpointsCount(100);
            if (dedMoroz.getShape().x - snowman.getShape().x < dedMoroz.getShape().width * 1.2) {
                snowman.getShape().x = (float) (dedMoroz.getShape().x - dedMoroz.getShape().width * 1.2);
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !dialogHandler.isActive() && !dialogHandler.isComplete()) {
                snowmanDialog.setText(DialogHandler.snowmanDialogs.get(snowmanDialogIteration));
                dialogHandler.setActive(true);
            }
        }

        Iterator<Drop> iter = raindrops.iterator();
        while (iter.hasNext()) {
            Rectangle raindrop = iter.next().getShape();
            raindrop.y -= speed * Gdx.graphics.getDeltaTime();
            if (raindrop.y + raindrop.height < 0) {
                iter.remove();
            }
            if (raindrop.overlaps(snowman.getShape())) {
                iter.remove();
                snowman.setSnowpointsCount(snowman.getSnowpointsCount() - 10);
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
        backgroundTexture.dispose();
    }
}
