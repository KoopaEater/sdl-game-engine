package engine.sprite;

import engine.renderer.Color;
import engine.renderer.Renderer;

public class CircleSprite implements Sprite {
    private final EllipseSprite ellipseSprite;
    public CircleSprite(Renderer renderer) {
        this.ellipseSprite = new EllipseSprite(renderer);
    }
    @Override
    public Vec2 getOrigin() {
        return ellipseSprite.getOrigin();
    }

    @Override
    public void moveTo(Vec2 newOrigin) {
        ellipseSprite.moveTo(newOrigin);
    }

    @Override
    public void move(Vec2 direction) {
        ellipseSprite.move(direction);
    }

    @Override
    public void show() {
        ellipseSprite.show();
    }

    public void setRadius(double radius) {
        ellipseSprite.setDimensions(new Vec2(radius*2, radius*2));
    }

    public void setColor(Color color) {
        ellipseSprite.setColor(color);
    }
}
