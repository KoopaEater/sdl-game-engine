package game;

import engine.gameloop.GameLoop;
import engine.gameloop.StandardGameLoop;
import engine.renderer.Color;
import engine.renderer.Renderer;
import engine.renderer.SdlRenderer;
import engine.resources.ResourceManager;
import engine.resources.SdlResourceManager;
import engine.sdlbase.SDLBase;
import engine.sdlbase.StandardSDLBase;
import engine.sprite.ImageSprite;
import engine.sprite.RectangleSprite;
import engine.sprite.Vec2;

public abstract class AbstractSdlGame {
    private final Renderer renderer;
    private final ResourceManager resourceManager;
    public AbstractSdlGame(String title, int width, int height, int tickRate) {
        GameLoop gameLoop = new StandardGameLoop(tickRate);
        gameLoop.setSetupTick(this::fullSetup);
        gameLoop.setTick(this::fullTick);
        gameLoop.setFixedTick(this::fullFixedTick);
        SDLBase base = new StandardSDLBase(title, width, height, gameLoop);
        renderer = new SdlRenderer(base.getRenderer());
        resourceManager = new SdlResourceManager(base.getRenderer());
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
    protected ImageSprite createImageSprite(Vec2 origin, Vec2 dimensions, String imgIdentifier) {
        ImageSprite sprite = new ImageSprite(renderer, resourceManager);
        sprite.moveTo(origin);
        sprite.setDimensions(dimensions);
        sprite.setImage(imgIdentifier);
        return sprite;
    }


    protected void loadImage(String identifier, String path) {
        resourceManager.loadImage(identifier, path);
    }
}
