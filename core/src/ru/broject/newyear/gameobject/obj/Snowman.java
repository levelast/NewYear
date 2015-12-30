package ru.broject.newyear.gameobject.obj;

import com.badlogic.gdx.math.Rectangle;
import ru.broject.newyear.support.Constants;

/**
 * Created by vyacheslav.svininyh on 28.12.2015.
 */
public class Snowman extends GameObj {

    private static final int WIDTH = 110;
    private static final int HEIGHT = 180;
    private int snowpointsCount;
    private Rectangle rectangle;

    public Snowman() {
        rectangle = new Rectangle();
        rectangle.width = WIDTH;
        rectangle.height = HEIGHT;
        rectangle.x = Constants.WINDOW_WIDTH / 2 - rectangle.width / 2;
        rectangle.y = 0;

        snowpointsCount = 50;
    }

    public Rectangle getShape() {
        return rectangle;
    }

    public int getSnowpointsCount() {
        return snowpointsCount;
    }

    public void setSnowpointsCount(int snowpointsCount) {
        this.snowpointsCount = snowpointsCount;
    }
}
