package game;

import engine.renderer.Color;
import engine.sprite.RectangleSprite;
import engine.sprite.Vec2;

import java.util.Random;

public class ExampleGame extends AbstractGame {
    private RectangleSprite rect;
    public ExampleGame() {
        super("Example", 1024, 768, 2);
    }

    @Override
    void setup() {
        rect = createRectangleSprite(new Vec2(400,300), new Vec2(200, 100), Color.RED);
    }

    @Override
    void tick(double deltaTime) {
        rect.move(new Vec2(50 * deltaTime, 0));
        rect.show();
    }

    @Override
    void fixedTick() {
        Random rand = new Random();
        int grey = rand.nextInt(255);
        setBackgroundColor(new Color(grey));
    }
}
