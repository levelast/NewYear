package ru.broject.newyear.gameobject.img;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by vyacheslav.svininyh on 28.12.2015.
 */
public abstract class Image {

    protected Texture image;

    public Image(String imageName) {
        this.image = new Texture(Gdx.files.internal(imageName));
    }

    public Texture getImage() {
        return image;
    }
}
