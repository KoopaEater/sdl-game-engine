package engine.renderer;

import engine.sprite.Vec2;
import io.github.libsdl4j.api.blendmode.SDL_BlendMode;
import io.github.libsdl4j.api.rect.SDL_FRect;
import io.github.libsdl4j.api.rect.SDL_Rect;
import io.github.libsdl4j.api.render.SDL_Renderer;
import io.github.libsdl4j.api.render.SDL_Texture;
import io.github.libsdl4j.api.render.SdlRender;

import java.util.function.DoubleBinaryOperator;

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

    // For antialiasing
    final int SUBSAMPLE_COUNT = 16; // Preferably something that can be square-rooted
    final int SUBSAMPLES_PER_AXIS = (int) Math.sqrt(SUBSAMPLE_COUNT);
    final double SUBSAMPLE_DIST = 1.0 / SUBSAMPLES_PER_AXIS;
    final double SUBSAMPLE_START_OFFSET = SUBSAMPLE_DIST / 2 - 0.5;
    @Override
    public void drawEllipse(Vec2 origin, Vec2 dimensions, Color color) {
        int cx = origin.intX();
        int cy = origin.intY();
        int rx = dimensions.intX() / 2;
        int ry = dimensions.intY() / 2;
        if (rx <= 0 || ry <= 0) return;
        double inv_rx_sq = 1.0 / (rx * rx);
        double inv_ry_sq = 1.0 / (ry * ry);
        int w = dimensions.intX();
        int h = dimensions.intY();

        DoubleBinaryOperator testInsideEllipse = (x, y) -> {
            double dx = x - cx;
            double dy = y - cy;
            return (dx * dx) * inv_rx_sq + (dy * dy) * inv_ry_sq;
        };

        for (int j = 0; j <= h; j++) {
            int py = cy - ry + j;
            for (int i = 0; i <= w; i++) {
                int px = cx - rx + i;

                // Courtesy of AI
                double f = testInsideEllipse.applyAsDouble(px, py);
                double g = f - 1.0;
                double gx = 2.0 * (px - cx) * inv_rx_sq;
                double gy = 2.0 * (py - cy) * inv_ry_sq;
                double gradLen = Math.hypot(gx, gy);
                double distPx = (gradLen > 1e-12) ? Math.abs(g) / gradLen : Double.POSITIVE_INFINITY;
                if (distPx > 0.707) {
                    if (f <= 1) {
                        SDL_SetRenderDrawColor(sdlRenderer, color.redAsByte(), color.greenAsByte(), color.blueAsByte(), color.alphaAsByte());
                        SDL_RenderDrawPoint(sdlRenderer, px, py);
                    }
                    continue;
                }

                // MSAA
                double positives = 0;
                for (int k = 0; k < SUBSAMPLES_PER_AXIS; k++) {
                    for (int l = 0; l < SUBSAMPLES_PER_AXIS; l++) {
                        double xJitter = HashUtil.jitterCentered(px, py, k, l, 0);
                        double yJitter = HashUtil.jitterCentered(px, py, k, l, 1);
                        double x = px + SUBSAMPLE_START_OFFSET + k * SUBSAMPLE_DIST + xJitter * SUBSAMPLE_DIST;
                        double y = py + SUBSAMPLE_START_OFFSET + l * SUBSAMPLE_DIST + yJitter * SUBSAMPLE_DIST;
                        positives += testInsideEllipse.applyAsDouble(x, y) <= 1 ? 1 : 0;
                    }
                }

                Color colorWithAA = color.withOpacity(positives / SUBSAMPLE_COUNT);
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

