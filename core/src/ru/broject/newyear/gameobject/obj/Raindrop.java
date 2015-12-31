package ru.broject.newyear.gameobject.obj;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import ru.broject.newyear.support.Constants;

/**
 * Created by vyacheslav.svininyh on 28.12.2015.
 */
public class Raindrop extends GameObj {

    private static final int WIDTH = 40;
    private static final int HEIGHT = 35;
    private Rectangle rectangle;

    public Raindrop(float snowmanXPosition) {
        rectangle = new Rectangle();
        rectangle.x = MathUtils.random((float) (snowmanXPosition - Constants.WINDOW_WIDTH * 1.5), (float) (snowmanXPosition + Constants.WINDOW_WIDTH * 1.5));
        rectangle.y = Constants.WINDOW_HEIGHT;
        rectangle.width = WIDTH;
        rectangle.height = HEIGHT;
    }

    public Rectangle getShape() {
        return rectangle;
    }
}
