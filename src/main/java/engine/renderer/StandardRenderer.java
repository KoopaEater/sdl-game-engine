package engine.renderer;

import io.github.libsdl4j.api.render.SDL_Renderer;

import static io.github.libsdl4j.api.render.SdlRender.*;

public class StandardRenderer implements Renderer {
    private Color backgroundColor;
    private final SDL_Renderer sdlRenderer;
    public StandardRenderer(SDL_Renderer sdlRenderer) {
        System.out.println("Hej?");
        this.sdlRenderer = sdlRenderer;
        backgroundColor = new Color("#000000");
        System.out.println(sdlRenderer);
    }

    @Override
    public void setBackgroundColor(Color color) {
        backgroundColor = color;
    }

    @Override
    public void drawBackground() {
        SDL_SetRenderDrawColor(sdlRenderer, backgroundColor.redAsByte(), backgroundColor.greenAsByte(), backgroundColor.blueAsByte(), backgroundColor.alphaAsByte());
        SDL_RenderClear(sdlRenderer);
    }

    @Override
    public void render() {
        SDL_RenderPresent(sdlRenderer);
    }
}
