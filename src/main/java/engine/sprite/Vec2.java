package engine.sprite;

public class Vec2 {
    static public Vec2 ZERO = new Vec2(0, 0);
    private final float x, y;
    public Vec2(double x, double y) {
        this.x = (float) x;
        this.y = (float) y;
    }
    public Vec2 add(Vec2 other) {
        return new Vec2(x + other.x, y + other.y);
    }
    public float x() { return x; }
    public float y() { return y; }
    public int intX() { return (int) x; }
    public int intY() { return (int) y; }
    public String toString() { return "(" + x + ", " + y + ")"; }
}
