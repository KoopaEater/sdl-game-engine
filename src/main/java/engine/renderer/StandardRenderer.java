package engine.renderer;

import engine.sprite.Vec2;
import io.github.libsdl4j.api.rect.SDL_FRect;
import io.github.libsdl4j.api.rect.SDL_Rect;
import io.github.libsdl4j.api.render.SDL_Renderer;
import io.github.libsdl4j.api.render.SDL_Texture;
import io.github.libsdl4j.api.render.SdlRender;

import static io.github.libsdl4j.api.render.SdlRender.*;

public class StandardRenderer implements Renderer {
    private Color backgroundColor;
    private final SDL_Renderer sdlRenderer;
    public StandardRenderer(SDL_Renderer sdlRenderer) {
        this.sdlRenderer = sdlRenderer;
        backgroundColor = new Color("#000000");
    }

    @Override
    public SDL_Renderer getSdlRenderer() {
        return sdlRenderer;
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
        rect.x = origin.x();
        rect.y = origin.y();
        rect.w = dimensions.x();
        rect.h = dimensions.y();
        SDL_RenderFillRectF(sdlRenderer, rect);
    }

    @Override
    public void drawEllipse(Vec2 origin, Vec2 dimensions, Color color) {
        SDL_SetRenderDrawColor(sdlRenderer, color.redAsByte(), color.greenAsByte(), color.blueAsByte(), color.alphaAsByte());
        int cx = origin.intX();
        int cy = origin.intY();
        int rx = dimensions.intX() / 2;
        int ry = dimensions.intY() / 2;
        int w = dimensions.intX();
        int h = dimensions.intY();
        for (int j = 0; j < h; j++) {
            int y = cy - ry + j;
            for (int i = 0; i < w; i++) {
                int x = cx - rx + i;
                double test = Math.pow(x - cx, 2) / Math.pow(rx, 2) + Math.pow(y - cy, 2) / Math.pow(ry, 2);
                if (test <= 1) {
                    SDL_RenderDrawPoint(sdlRenderer, x, y);
                }
            }
        }
    }

    @Override
    public void drawTexture(Vec2 origin, Vec2 dimensions, SDL_Texture texture) {
        SDL_Rect rect = new SDL_Rect();
        rect.x = origin.intX();
        rect.y = origin.intY();
        rect.w = dimensions.intX();
        rect.h = dimensions.intY();
        SdlRender.SDL_RenderCopy(sdlRenderer, texture, null, rect);
    }

    @Override
    public void render() {
        SDL_RenderPresent(sdlRenderer);
    }
}
