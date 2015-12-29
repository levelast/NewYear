//package ru.broject.newyear;
//
//import com.badlogic.gdx.ApplicationAdapter;
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Input.Keys;
//import com.badlogic.gdx.graphics.Color;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.OrthographicCamera;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.BitmapFont;
//import com.badlogic.gdx.graphics.g2d.Sprite;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.math.Rectangle;
//import com.badlogic.gdx.scenes.scene2d.ui.Label;
//import com.badlogic.gdx.utils.Array;
//import com.badlogic.gdx.utils.TimeUtils;
//import ru.broject.newyear.gameobject.img.DedMorozImage;
//import ru.broject.newyear.gameobject.img.DropImage;
//import ru.broject.newyear.gameobject.img.SnowmanImage;
//import ru.broject.newyear.gameobject.obj.DedMoroz;
//import ru.broject.newyear.gameobject.obj.Drop;
//import ru.broject.newyear.gameobject.obj.Snowman;
//import ru.broject.newyear.support.Constants;
//
//import java.util.Iterator;
//
//public class MyGdxGame extends ApplicationAdapter {
//
////    private Texture snowmanImage;
////    private Rectangle snowman;
//
//
//    private Snowman snowman;
//    private SnowmanImage snowmanImage;
//    private DropImage dropImage;
//    private DedMoroz dedMoroz;
//    private DedMorozImage dedMorozImage;
//    private Texture backgroundTexture;
//    private Sprite backgroundSprite;
//
//    //    private Sound dropSound;
////    private Music rainMusic;
//    private SpriteBatch batch;
//    private OrthographicCamera camera;
//    private Array<Drop> raindrops;
//    private BitmapFont font45white;
//    private Label label;
//    private long lastDropTime;
//    private int speed = Constants.DEFAULT_SPEED;
//    private int timeBetweenDrops = Constants.DEFAULT_TIME_BETWEEN_DROPS;
//
//    @Override
//    public void create() {
//        // load the images for the droplet and the bucket, 64x64 pixels each
//        snowmanImage = new SnowmanImage();
//        snowman = new Snowman();
//        dropImage = new DropImage();
//        dedMorozImage = new DedMorozImage();
//        dedMoroz = new DedMoroz();
//        backgroundTexture = new Texture(Gdx.files.internal("background.jpg"));
//        backgroundSprite = new Sprite(backgroundTexture);
//
//        font45white = new BitmapFont(Gdx.files.internal("default.fnt"), Gdx.files.internal("default.png"), false);
//        Label.LabelStyle ls = new Label.LabelStyle(font45white, Color.WHITE);
//        label = new Label("This is text", ls);
//        label.setPosition(Constants.WINDOW_WIDTH / 2, Constants.WINDOW_HEIGHT / 2);
//
//
//
////        dropImage = new Texture(Gdx.files.internal("droplet.png"));
//
//
////        // load the drop sound effect and the rain backgroundImage "music"
////        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
////        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
////
////        // start the playback of the backgroundImage music immediately
////        rainMusic.setLooping(true);
////        rainMusic.play();
//
//        // create the camera and the SpriteBatch
//        camera = new OrthographicCamera();
//        camera.setToOrtho(false, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
//        batch = new SpriteBatch();
//
//
////        snowman = new Rectangle();
////        snowman.width = 120;
////        snowman.height = 190;
////        snowman.x = Window.WIDTH / 2 - snowman.width / 2;
////        snowman.y = 0;
//
//
////        // create a Rectangle to logically represent the bucket
////        bucket = new Rectangle();
////        bucket.x = 800 / 2 - 64 / 2; // center the bucket horizontally
////        bucket.y = 20; // bottom left corner of the bucket is 20 pixels above the bottom screen edge
////        bucket.width = 64;
////        bucket.height = 64;
//
//        // create the raindrops array and spawn the first raindrop
//        raindrops = new Array<Drop>();
//        spawnRaindrop();
//    }
//
//    private void spawnRaindrop() {
//        Drop raindrop = new Drop(snowman.getShape().x);
//        raindrops.add(raindrop);
//        lastDropTime = TimeUtils.nanoTime();
//    }
//
//    @Override
//    public void render() {
//        // clear the screen with a dark blue color. The
//        // arguments to glClearColor are the red, green
//        // blue and alpha component in the range [0,1]
//        // of the color to be used to clear the screen.
//        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//        // tell the camera to update its matrices.
//        camera.position.set(snowman.getShape().x, Constants.WINDOW_HEIGHT / 2, 0);
//        camera.update();
//
//        // tell the SpriteBatch to render in the
//        // coordinate system specified by the camera.
//        batch.setProjectionMatrix(camera.combined);
//
//        // begin a new batch and draw the bucket and
//        // all drops
//        batch.begin();
//        batch.draw(backgroundSprite, backgroundSprite.getX(), backgroundSprite.getY());
//        batch.draw(snowmanImage.getImage(), snowman.getShape().x, snowman.getShape().y);
//        for (Drop raindrop : raindrops) {
//            batch.draw(dropImage.getImage(), raindrop.getShape().x, raindrop.getShape().y);
//        }
//        batch.draw(dedMorozImage.getImage(), dedMoroz.getShape().x, dedMoroz.getShape().y);
//        label.draw(batch, 20);
//        batch.end();
//
//        if (snowman.getSnowpointsCount() <= 0) {
//            snowman = new Snowman();
//            timeBetweenDrops = Constants.DEFAULT_TIME_BETWEEN_DROPS;
//            speed = Constants.DEFAULT_SPEED;
//        }
//
//        // process user input
////        if(Gdx.input.isTouched()) {
////            Vector3 touchPos = new Vector3();
////            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
////            camera.unproject(touchPos);
////            snowman.getShape().x = touchPos.x - snowman.getShape().width / 2;
////        }
//        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
//            snowman.getShape().x -= 300 * Gdx.graphics.getDeltaTime();
//        }
//        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
//            snowman.getShape().x += 300 * Gdx.graphics.getDeltaTime();
//        }
//
//        // make sure the bucket stays within the screen bounds
////        if(bucket.x < 0) bucket.x = 0;
////        if(bucket.x > 800 - 64) bucket.x = 800 - 64;
//
//        // check if we need to create a new raindrop
//        if (TimeUtils.nanoTime() - lastDropTime > timeBetweenDrops) {
//            spawnRaindrop();
//            if (speed < Constants.MAX_SPEED) {
//                speed += Constants.DEFAULT_SPEED / 30;
//            }
//            if (timeBetweenDrops > Constants.MIN_TIME_BETWEEN_DROPS) {
//                timeBetweenDrops -= Constants.DEFAULT_TIME_BETWEEN_DROPS / 30;
//            }
//        }
//
//        // move the raindrops, remove any that are beneath the bottom edge of
//        // the screen or that hit the bucket. In the later case we play back
//        // a sound effect as well.
//        if (dedMoroz.getShape().x - snowman.getShape().x < 200) {
//            snowman.setSnowpointsCount(100);
//            if (dedMoroz.getShape().x - snowman.getShape().x < 100) {
//                snowman.getShape().x = dedMoroz.getShape().x - 100;
//            }
//        }
//
//        Iterator<Drop> iter = raindrops.iterator();
//        while (iter.hasNext()) {
//            Rectangle raindrop = iter.next().getShape();
//            raindrop.y -= speed * Gdx.graphics.getDeltaTime();
//            if (raindrop.y + raindrop.height < 0) {
//                iter.remove();
//            }
//            if (raindrop.overlaps(snowman.getShape())) {
////                dropSound.play();
//                iter.remove();
//                snowman.setSnowpointsCount(snowman.getSnowpointsCount() - 10);
//            }
//        }
//    }
//
//    @Override
//    public void dispose() {
//        // dispose of all the native resources
//        dropImage.getImage().dispose();
////        dropImage.dispose();
//        snowmanImage.getImage().dispose();
//        dedMorozImage.getImage().dispose();
//        backgroundTexture.dispose();
//        font45white.dispose();
////        dropSound.dispose();
////        rainMusic.dispose();
//        batch.dispose();
//    }
//}
