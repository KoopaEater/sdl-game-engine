package engine.renderer;

import engine.sprite.Vec2;

public interface Renderer {
    void setBackgroundColor(Color color);
    void drawRectangle(Vec2 origin, Vec2 dimensions, Color color);

    void drawBackground();
    void render();
}
