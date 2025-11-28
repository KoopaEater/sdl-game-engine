package engine.renderer;

import engine.sprite.Vec2;
import io.github.libsdl4j.api.rect.SDL_FRect;
import io.github.libsdl4j.api.rect.SDL_Rect;
import io.github.libsdl4j.api.render.SDL_Renderer;
import io.github.libsdl4j.api.render.SDL_Texture;
import io.github.libsdl4j.api.render.SdlRender;

import static io.github.libsdl4j.api.render.SdlRender.*;

public class SdlRenderer implements Renderer {
    private Color backgroundColor;
    private final SDL_Renderer sdlRenderer;
    public SdlRenderer(SDL_Renderer sdlRenderer) {
        this.sdlRenderer = sdlRenderer;
        backgroundColor = new Color("#000000");
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
    public void drawRectangle(Vec2 origin, Vec2 dimensions, Color color) {
        SDL_SetRenderDrawColor(sdlRenderer, color.redAsByte(), color.greenAsByte(), color.blueAsByte(), color.alphaAsByte());
        SDL_FRect rect = new SDL_FRect();
        rect.x = (float) origin.x();
        rect.y = (float) origin.y();
        rect.w = (float) dimensions.x();
        rect.h = (float) dimensions.y();
        SDL_RenderFillRectF(sdlRenderer, rect);
    }

    @Override
    public void drawTexture(Vec2 origin, Vec2 dimensions, SDL_Texture texture) {
        SDL_Rect rect = new SDL_Rect();
        rect.x = Math.round((float) origin.x());
        rect.y = Math.round((float) origin.y());
        rect.w = Math.round((float) dimensions.x());
        rect.h = Math.round((float) dimensions.y());
        SdlRender.SDL_RenderCopy(sdlRenderer, texture, null, rect);
    }

    @Override
    public void render() {
        SDL_RenderPresent(sdlRenderer);
    }
}
