package game;

import engine.image.Image;
import engine.renderer.Color;
import engine.sprite.ImageSprite;
import engine.sprite.RectangleSprite;
import engine.sprite.Vec2;

import java.util.Random;

public class ExampleGame extends AbstractSdlGame {
    private RectangleSprite rect;
    private ImageSprite cat, dog;
    public ExampleGame() {
        super("Example", 1024, 768, 2);
    }

    @Override
    void setup() {
        rect = createRectangleSprite(new Vec2(400,300), new Vec2(200, 100), Color.RED);

        Image catImg = loadImage("cat.jpg");
        cat = createImageSprite(new Vec2(800, 20), new Vec2(200, 100), catImg);

        Image dogImg = loadImage("dog.png");
        dog = createImageSprite(new Vec2(0, 600), new Vec2(200, 200), dogImg);
    }

    @Override
    void tick(double deltaTime) {
        rect.move(new Vec2(50 * deltaTime, 0));
        rect.show();

        cat.move(new Vec2(-50 * deltaTime, 0));
        cat.show();

        dog.show();
    }

    @Override
    void fixedTick() {
        Random rand = new Random();
        int grey = rand.nextInt(255);
        setBackgroundColor(new Color(grey));
    }
}
