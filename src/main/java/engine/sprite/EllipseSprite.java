package engine.sprite;

import engine.renderer.Color;
import engine.renderer.Renderer;

public class EllipseSprite implements Sprite {
    private final Renderer renderer;
    private Vec2 origin;
    private Vec2 dimensions;
    private Color color;
    public EllipseSprite(Renderer renderer) {
        this.renderer = renderer;
        this.origin = Vec2.ZERO;
        this.dimensions = Vec2.ZERO;
        this.color = Color.WHITE;
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
        renderer.drawEllipse(origin, dimensions, color);
    }

    public void setDimensions(Vec2 dimensions) {
        this.dimensions = dimensions;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
