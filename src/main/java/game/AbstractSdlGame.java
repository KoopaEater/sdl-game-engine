package game;

import engine.gameloop.GameLoop;
import engine.gameloop.StandardGameLoop;
import engine.image.Image;
import engine.image.StandardImage;
import engine.renderer.Color;
import engine.renderer.Renderer;
import engine.renderer.StandardRenderer;
import engine.sdlbase.SDLBase;
import engine.sdlbase.StandardSDLBase;
import engine.sprite.EllipseSprite;
import engine.sprite.ImageSprite;
import engine.sprite.RectangleSprite;
import engine.sprite.Vec2;

public abstract class AbstractSdlGame {
    private final Renderer renderer;
    public AbstractSdlGame(String title, int width, int height, int tickRate) {
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

    protected void setBackgroundColor(Color color) {
        renderer.setBackgroundColor(color);
    }
    protected RectangleSprite createRectangleSprite(Vec2 origin, Vec2 dimensions, Color color) {
        RectangleSprite sprite = new RectangleSprite(renderer);
        sprite.moveTo(origin);
        sprite.setDimensions(dimensions);
        sprite.setColor(color);
        return sprite;
    }
    protected EllipseSprite createEllipseSprite(Vec2 origin, Vec2 dimensions, Color color) {
        EllipseSprite sprite = new EllipseSprite(renderer);
        sprite.moveTo(origin);
        sprite.setDimensions(dimensions);
        sprite.setColor(color);
        return sprite;
    }
    protected ImageSprite createImageSprite(Vec2 origin, Vec2 dimensions, Image image) {
        ImageSprite sprite = new ImageSprite(renderer);
        sprite.moveTo(origin);
        sprite.setDimensions(dimensions);
        sprite.setImage(image);
        return sprite;
    }


    protected Image loadImage(String path) {
        return new StandardImage(renderer, path);
    }
}
