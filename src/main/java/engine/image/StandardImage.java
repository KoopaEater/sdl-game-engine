package engine.image;

import com.sun.jna.Memory;
import engine.renderer.Renderer;
import engine.sprite.Vec2;
import io.github.libsdl4j.api.pixels.SDL_PixelFormatEnum;
import io.github.libsdl4j.api.render.SDL_Texture;
import io.github.libsdl4j.api.render.SdlRender;
import io.github.libsdl4j.api.surface.SDL_Surface;
import io.github.libsdl4j.api.surface.SdlSurface;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class StandardImage implements Image {
    private final Renderer renderer;
    private int[] pixels;
    private SDL_Texture texture;
    private Vec2 dimensions;
    public StandardImage(Renderer renderer, String path) {
        this.renderer = renderer;
        loadPixels(path);
        loadTexture();
    }
    private void loadPixels(String path) {
        try (InputStream imgStream = getClass().getClassLoader().getResourceAsStream(path)) {
            if  (imgStream == null) {
                throw new IllegalArgumentException("Cannot load image from " + path);
            }
            BufferedImage img = ImageIO.read(imgStream);
            int w = img.getWidth();
            int h = img.getHeight();
            dimensions = new Vec2(w, h);
            pixels = new int[w*h];
            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    pixels[x + y * w] = img.getRGB(x, y);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadTexture() {
        Memory mem = new Memory((long) pixels.length * 4);
        for (int i = 0; i < pixels.length; i++) {
            mem.setInt((long) i * 4, pixels[i]);
        }
        SDL_Surface surface = SdlSurface.SDL_CreateRGBSurfaceWithFormatFrom(mem, dimensions.intX(), dimensions.intY(), 32, dimensions.intX()*4, SDL_PixelFormatEnum.SDL_PIXELFORMAT_ARGB8888);
        SDL_Texture texture = SdlRender.SDL_CreateTextureFromSurface(renderer.getSdlRenderer(), surface);
        SdlSurface.SDL_FreeSurface(surface);
        this.texture = texture;
    }

    @Override
    public SDL_Texture getTexture() {
        return texture;
    }
}
