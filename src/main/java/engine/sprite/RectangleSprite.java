package engine.sprite;

import engine.renderer.Color;
import engine.renderer.Renderer;

public class RectangleSprite implements Sprite {
    private Renderer renderer;
    private Vec2 origin;
    private Vec2 dimensions;
    private Color color;
    public RectangleSprite(Renderer renderer, Vec2 origin, Vec2 dimensions, Color color) {
        this.renderer = renderer;
        this.origin = origin;
        this.dimensions = dimensions;
        this.color = color;
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
        renderer.drawRectangle(origin, dimensions, color);
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
