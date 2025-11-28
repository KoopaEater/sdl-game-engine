package engine.resources;

import io.github.libsdl4j.api.render.SDL_Texture;

public interface ResourceManager {
    void loadImage(String identifier, String path);
    SDL_Texture getTexture(String identifier);
}
