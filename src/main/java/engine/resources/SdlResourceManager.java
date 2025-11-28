package engine.resources;

import com.sun.jna.Memory;
import io.github.libsdl4j.api.pixels.SDL_PixelFormatEnum;
import io.github.libsdl4j.api.render.SDL_Renderer;
import io.github.libsdl4j.api.render.SDL_Texture;
import io.github.libsdl4j.api.render.SdlRender;
import io.github.libsdl4j.api.surface.SDL_Surface;
import io.github.libsdl4j.api.surface.SdlSurface;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class SdlResourceManager implements ResourceManager {
    private Map<String, SDL_Texture> textures;
    private SDL_Renderer renderer;
    public SdlResourceManager(SDL_Renderer renderer) {
        this.renderer = renderer;
        textures = new HashMap<>();
    }

    @Override
    public void loadImage(String identifier, String path) {
        try (InputStream imgStream = getClass().getClassLoader().getResourceAsStream(path)) {
            if  (imgStream == null) {
                throw new IllegalArgumentException("Cannot load image from " + path);
            }
            BufferedImage img = ImageIO.read(imgStream);
            int w =  img.getWidth();
            int h =  img.getHeight();
            int[] pixels = new int[w*h];
            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    pixels[x + y * w] = img.getRGB(x, y);
                }
            }
            Memory mem = new Memory((long) pixels.length * 4);
            for (int i = 0; i < pixels.length; i++) {
                mem.setInt((long) i * 4, pixels[i]);
            }
            SDL_Surface surface = SdlSurface.SDL_CreateRGBSurfaceWithFormatFrom(mem, w, h, 32, w*4, SDL_PixelFormatEnum.SDL_PIXELFORMAT_ABGR8888);
            SDL_Texture texture = SdlRender.SDL_CreateTextureFromSurface(renderer, surface);
            SdlSurface.SDL_FreeSurface(surface);
            textures.put(identifier, texture);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SDL_Texture getTexture(String identifier) {
        if (!textures.containsKey(identifier)) {
            throw new IllegalArgumentException("Trying to get a texture that is not loaded: " + identifier);
        }
        return textures.get(identifier);
    }
}
