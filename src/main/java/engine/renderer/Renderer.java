package engine.renderer;

import engine.sprite.Vec2;
import io.github.libsdl4j.api.render.SDL_Texture;

public interface Renderer {
    void setBackgroundColor(Color color);

    void drawBackground();
    void drawRectangle(Vec2 origin, Vec2 dimensions, Color color);
    void drawTexture(Vec2 origin, Vec2 dimensions, SDL_Texture texture);

    void render();
}
