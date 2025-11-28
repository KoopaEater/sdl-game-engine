package game;

import engine.gameloop.GameLoop;
import engine.gameloop.StandardGameLoop;
import engine.renderer.Color;
import engine.renderer.Renderer;
import engine.renderer.StandardRenderer;
import engine.sdlbase.SDLBase;
import engine.sdlbase.StandardSDLBase;
import engine.sprite.RectangleSprite;
import engine.sprite.Vec2;

public abstract class AbstractGame {
    private final Renderer renderer;
    public AbstractGame(String title, int width, int height, int tickRate) {
        GameLoop gameLoop = new StandardGameLoop(tickRate);
        gameLoop.setSetupTick(this::fullSetup);
        gameLoop.setTick(this::fullTick);
        gameLoop.setFixedTick(this::fullFixedTick);
        SDLBase base = new StandardSDLBase(title, width, height, gameLoop);
        renderer = new StandardRenderer(base.getRenderer());
        base.start();
    }

    private void fullSetup() {
        setup();
    }
    private void fullTick(double deltaTime) {
        renderer.drawBackground();
        tick(deltaTime);
        renderer.render();
    }
    private void fullFixedTick() {
        fixedTick();
    }

    abstract void setup();
    abstract void tick(double deltaTime);
    abstract void fixedTick();

    public void setBackgroundColor(Color color) {
        renderer.setBackgroundColor(color);
    }
    public RectangleSprite createRectangleSprite(Vec2 origin, Vec2 dimensions, Color color) {
        return new RectangleSprite(renderer, origin, dimensions, color);
    }
}
