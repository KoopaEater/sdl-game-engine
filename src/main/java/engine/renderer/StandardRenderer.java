package engine.renderer;

import engine.sprite.Vec2;
import io.github.libsdl4j.api.blendmode.SDL_BlendMode;
import io.github.libsdl4j.api.rect.SDL_FRect;
import io.github.libsdl4j.api.rect.SDL_Rect;
import io.github.libsdl4j.api.render.SDL_Renderer;
import io.github.libsdl4j.api.render.SDL_Texture;
import io.github.libsdl4j.api.render.SdlRender;

import java.util.List;

import static io.github.libsdl4j.api.render.SdlRender.*;

public class StandardRenderer implements Renderer {
    private Color backgroundColor;
    private final SDL_Renderer sdlRenderer;
    public StandardRenderer(SDL_Renderer sdlRenderer) {
        this.sdlRenderer = sdlRenderer;
        backgroundColor = new Color("#000000");
        SDL_SetRenderDrawBlendMode(sdlRenderer, SDL_BlendMode.SDL_BLENDMODE_BLEND);
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
        int cx = origin.intX();
        int cy = origin.intY();
        int rx = dimensions.intX() / 2;
        int ry = dimensions.intY() / 2;
        double rx_sq = rx * rx;
        double ry_sq = ry * ry;
        int w = dimensions.intX();
        int h = dimensions.intY();
        for (int j = 0; j < h; j++) {
            int y = cy - ry + j;
            double y0 = y;
            double y1 = y + 0.5;
            double y2 = y - 0.5;
            for (int i = 0; i < w; i++) {
                int x = cx - rx + i;
                double x0 = x;
                double x1 = x + 0.5;
                double x2 = x - 0.5;
                boolean test0 = Math.pow(x0 - cx, 2) / rx_sq + Math.pow(y0 - cy, 2) / Math.pow(ry, 2) <= 1;
                boolean test1 = Math.pow(x1 - cx, 2) / rx_sq + Math.pow(y0 - cy, 2) / Math.pow(ry, 2) <= 1;
                boolean test2 = Math.pow(x2 - cx, 2) / rx_sq + Math.pow(y0 - cy, 2) / Math.pow(ry, 2) <= 1;
                boolean test3 = Math.pow(x0 - cx, 2) / rx_sq + Math.pow(y1 - cy, 2) / Math.pow(ry, 2) <= 1;
                boolean test4 = Math.pow(x1 - cx, 2) / rx_sq + Math.pow(y1 - cy, 2) / Math.pow(ry, 2) <= 1;
                boolean test5 = Math.pow(x2 - cx, 2) / rx_sq + Math.pow(y1 - cy, 2) / Math.pow(ry, 2) <= 1;
                boolean test6 = Math.pow(x0 - cx, 2) / rx_sq + Math.pow(y2 - cy, 2) / Math.pow(ry, 2) <= 1;
                boolean test7 = Math.pow(x1 - cx, 2) / rx_sq + Math.pow(y2 - cy, 2) / Math.pow(ry, 2) <= 1;
                boolean test8 = Math.pow(x2 - cx, 2) / rx_sq + Math.pow(y2 - cy, 2) / Math.pow(ry, 2) <= 1;
                List<Boolean> tests = List.of(test0, test1, test2, test3, test4, test5, test6, test7, test8);
                int positives = (int) tests.stream().filter(b -> b).count();
                double transparency = 1 - (positives / 9.0);
                Color colorWithAA = color.withTransparency(transparency);
                System.out.println(colorWithAA.alphaAsByte());
                SDL_SetRenderDrawColor(sdlRenderer, colorWithAA.redAsByte(), colorWithAA.greenAsByte(), colorWithAA.blueAsByte(), colorWithAA.alphaAsByte());
                SDL_RenderDrawPoint(sdlRenderer, x, y);
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
