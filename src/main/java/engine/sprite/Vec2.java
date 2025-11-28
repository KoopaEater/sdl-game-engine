package engine.sprite;

public record Vec2(double x, double y) {
    static public Vec2 ZERO = new Vec2(0.0, 0.0);
    public Vec2 add(Vec2 other) {
        return new Vec2(x + other.x, y + other.y);
    }
}
