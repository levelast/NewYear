package ru.broject.newyear.gameobject.obj;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import ru.broject.newyear.support.Constants;

/**
 * Created by vyacheslav.svininyh on 28.12.2015.
 */
public class Drop extends GameObj {

    private static final int WIDTH = 64;
    private static final int HEIGHT = 64;
    private Rectangle rectangle;

    public Drop(float snowmanXPosition) {
        rectangle = new Rectangle();
        rectangle.x = MathUtils.random(snowmanXPosition - Constants.WINDOW_WIDTH, snowmanXPosition + Constants.WINDOW_WIDTH);
        rectangle.y = Constants.WINDOW_HEIGHT;
        rectangle.width = WIDTH;
        rectangle.height = HEIGHT;
    }

    public Rectangle getShape() {
        return rectangle;
    }
}
