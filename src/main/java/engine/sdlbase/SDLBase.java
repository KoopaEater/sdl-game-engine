package engine.sdlbase;

import io.github.libsdl4j.api.render.SDL_Renderer;

public interface SDLBase {
    SDL_Renderer getRenderer();
    void start();
}
