package ru.broject.newyear.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.broject.newyear.NewYearGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "MyGdxGame";
		config.width = 800;
		config.height = 480;
		new LwjglApplication(new NewYearGame(), config);
	}
}
