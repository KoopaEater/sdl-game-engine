package game;

import engine.renderer.Color;

import java.util.Random;

public class ExampleGame extends AbstractGame {
    public ExampleGame() {
        super("Example", 1024, 768, 2);
    }

    @Override
    void setup() {
        System.out.println("SETUP");
    }

    @Override
    void tick(double deltaTime) {

    }

    @Override
    void fixedTick() {
        Random rand = new Random();
        int grey = rand.nextInt(255);
        setBackgroundColor(new Color(grey));
    }
}
