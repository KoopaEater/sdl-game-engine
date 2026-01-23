package engine.renderer;

import engine.sprite.Vec2;
import io.github.libsdl4j.api.blendmode.SDL_BlendMode;
import io.github.libsdl4j.api.rect.SDL_FRect;
import io.github.libsdl4j.api.rect.SDL_Rect;
import io.github.libsdl4j.api.render.SDL_Renderer;
import io.github.libsdl4j.api.render.SDL_Texture;
import io.github.libsdl4j.api.render.SdlRender;

import java.util.List;
import java.util.function.BiPredicate;

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

        BiPredicate<Double, Double> testInsideEllipse = (x, y) ->
             Math.pow(x - cx, 2) / rx_sq + Math.pow(y - cy, 2) / ry_sq <= 1;

        // For antialiasing
        final double MAX_SAMPLE_DIST = 0.5;
        double sampleDist = 2 * MAX_SAMPLE_DIST;
        final int SAMPLE_COUNT = 9; // Preferably something that can be square-rooted
        int samplesPerAxis = (int) Math.sqrt(SAMPLE_COUNT);
        double distBetweenSamples = sampleDist / samplesPerAxis;

        for (int j = 0; j < h; j++) {
            int py = cy - ry + j;
            for (int i = 0; i < w; i++) {
                int px = cx - rx + i;

                double positives = 0;
                for (int k = 0; k < samplesPerAxis; k++) {
                    for (int l = 0; l < samplesPerAxis; l++) {
                        double x = px - MAX_SAMPLE_DIST + k * distBetweenSamples;
                        double y = py - MAX_SAMPLE_DIST + l * distBetweenSamples;
                        positives += testInsideEllipse.test(x, y) ? 1 : 0;
                    }
                }

                Color colorWithAA = color.withOpacity(positives / SAMPLE_COUNT);
                SDL_SetRenderDrawColor(sdlRenderer, colorWithAA.redAsByte(), colorWithAA.greenAsByte(), colorWithAA.blueAsByte(), colorWithAA.alphaAsByte());
                SDL_RenderDrawPoint(sdlRenderer, px, py);
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
