package ru.broject.newyear.gameobject.obj;

import com.badlogic.gdx.math.Rectangle;
import ru.broject.newyear.support.Constants;

/**
 * Created by vyacheslav.svininyh on 28.12.2015.
 */
public class DedMoroz extends GameObj {

    private static final int WIDTH = 120;
    private static final int HEIGHT = 190;
    private Rectangle rectangle;

    public DedMoroz() {
        rectangle = new Rectangle();
        rectangle.width = WIDTH;
        rectangle.height = HEIGHT;
        rectangle.x = Constants.PATH_LENGTH;
        rectangle.y = 0;
    }

    public Rectangle getShape() {
        return rectangle;
    }
}
