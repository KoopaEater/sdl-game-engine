package engine.sprite;

import engine.image.Image;
import engine.renderer.Renderer;

public class ImageSprite implements Sprite {
    private final Renderer renderer;
    private Vec2 origin;
    private Vec2 dimensions;
    private Image image;
    public ImageSprite(Renderer renderer) {
        this.renderer = renderer;
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
        renderer.drawTexture(origin, dimensions, image.getTexture());
    }

    public void setDimensions(Vec2 dimensions) {
        this.dimensions = dimensions;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
