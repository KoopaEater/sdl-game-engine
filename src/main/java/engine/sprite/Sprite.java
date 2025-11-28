package engine.sprite;

public interface Sprite {
    Vec2 getOrigin();
    void moveTo(Vec2 newOrigin);
    void move(Vec2 direction);
    void show();
}
