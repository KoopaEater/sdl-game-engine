package engine.sprite;

import engine.renderer.Renderer;
import engine.resources.ResourceManager;
import io.github.libsdl4j.api.render.SDL_Texture;

public class ImageSprite implements Sprite {
    private final Renderer renderer;
    private final ResourceManager resourceManager;
    private Vec2 origin;
    private Vec2 dimensions;
    private SDL_Texture texture;
    public ImageSprite(Renderer renderer, ResourceManager resourceManager) {
        this.renderer = renderer;
        this.resourceManager = resourceManager;
        this.origin = Vec2.ZERO;
        this.dimensions = Vec2.ZERO;
    }
    @Override
    public Vec2 getOrigin() {
        return origin;
    }

    @Override
    public void moveTo(Vec2 newOrigin) {
        origin = newOrigin;
    }

    @Override
    public void move(Vec2 direction) {
        origin = origin.add(direction);
    }

    @Override
    public void show() {
        renderer.drawTexture(origin, dimensions, texture);
    }

    public void setDimensions(Vec2 dimensions) {
        this.dimensions = dimensions;
    }

    public void setImage(String identifier) {
        texture = resourceManager.getTexture(identifier);
    }
}
