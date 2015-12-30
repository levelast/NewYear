package ru.broject.newyear.gameobject.obj;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by vyacheslav.svininyh on 30.12.2015.
 */
public class Tree extends GameObj {

    private static final int WIDTH = 230;
    private static final int HEIGHT = 240;
    private Rectangle rectangle;

    public Tree(float xPosition) {
        rectangle = new Rectangle();
        rectangle.width = WIDTH;
        rectangle.height = HEIGHT;
        rectangle.x = xPosition;
        rectangle.y = 0;
    }

    @Override
    public Rectangle getShape() {
        return rectangle;
    }
}
