package ru.broject.newyear;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import ru.broject.newyear.support.Constants;

/**
 * Created by vyacheslav.svininyh on 29.12.2015.
 */
public class GameOverScreen implements Screen {

    private NewYearGame game;
    private OrthographicCamera camera;

    public GameOverScreen(final NewYearGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font45white.draw(game.batch, "Game Over :(", Constants.WINDOW_WIDTH / 5, (float) (Constants.WINDOW_HEIGHT / 1.5));
        game.font25white.draw(game.batch, "Try again?", Constants.WINDOW_WIDTH / 3, Constants.WINDOW_HEIGHT / 3);
        game.batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            game.setScreen(new GameScreen(game));
            dispose();
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

    }
}
