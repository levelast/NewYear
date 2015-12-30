package ru.broject.newyear;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by vyacheslav.svininyh on 29.12.2015.
 */
public class NewYearGame extends Game {

    public SpriteBatch batch;
    public BitmapFont font45white;
    public BitmapFont font25white;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font25white = new BitmapFont(Gdx.files.internal("fonts/press25_white.fnt"), false);
        font45white = new BitmapFont(Gdx.files.internal("fonts/press45_white.fnt"), false);
        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font25white.dispose();
        font45white.dispose();
    }
}
