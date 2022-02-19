package main.engine.display;

import lombok.Getter;
import main.engine.structures.drawable.Image;
import main.engine.structures.drawable.Text;

import java.awt.image.DataBufferInt;
import java.util.Arrays;

public class Renderer
{
    public static final int DEFAULT_COLOR = 0xFF000001;
    public static final int UNICODE_OFFSET = 32;
    private final int screenW;
    private final int screenH;
    private final  Camera camera;
    private final Font font;
    private final int[] p;
    @Getter
    private final int[] pOwner;

    public Renderer(Window window)
    {
        screenW = (int)(window.getWidth() * Window.SCALE);
        screenH = (int)(window.getHeight() * Window.SCALE);
        p = ((DataBufferInt)window.getImage().getRaster().getDataBuffer()).getData();
        pOwner = new int[window.getWidth() * window.getHeight()];
        font = new Font("/sansBoldplus.png");
        camera = window.getCamera();
    }

    public void clear()
    {
        Arrays.fill(p, 0xFF000001);
    }

    private int alphaCompose(int color2, int color1)
    {
        float alpha = ((color1 & 0xFF000000) >>> 24) * (1.0f / 255.0f);
        int red = (int)((((color1 & 0x00FF0000) >>> 16) * alpha) + (((color2 & 0x00FF0000) >>> 16) * (1.0 - alpha)));
        int green = (int)((((color1 & 0x0000FF00) >>> 8) * alpha) + (((color2 & 0x0000FF00) >>> 8) * (1.0 - alpha)));
        int blue = (int)(((color1 & 0x000000FF) * alpha) + ((color2 & 0x000000FF) * (1.0 - alpha)));
        return 255 << 24 | red << 16 | green << 8 | blue;
    }

    private void drawPixel(int x,int y, int value, int owner)
    {
        int id = x + y * screenW;
        if(isPixelOutOfScreen(x, y))
            return;
        pOwner[id] = owner;
        p[id] = (isNotTransparent(id, value) ? value : alphaCompose(p[id], value));
    }

    private boolean isNotTransparent(int id, int value)
    {
        return (value >>> 24 == 0xFF) | (p[id] == DEFAULT_COLOR);
    }

    private boolean isPixelOutOfScreen(int x, int y)
    {
        return x < 0 & y < 0 & x >= screenW & y >= screenH;
    }

    public void drawRectangle(int x, int y, int w, int h, int color, int fixed, int owner)
    {
        if(fixed == 0)
            drawNonFixedRectangle(x, y, w, h, color, owner);
        else
            drawFixedRectangle(x, y, w, h, color, owner);

    }

    private void drawNonFixedRectangle(int x, int y, int w, int h, int color, int owner)
    {
        for(int i = Math.max(x, 0) + camera.offX; i < Math.min(w + x, screenW) + camera.offX; i++)
        {
            for(int j = Math.max(y, 0) + camera.offY; j < Math.min(h + y, screenH) + camera.offY; j++)
            {
                drawPixel(i, j, color, owner);
            }
        }
    }

    private void drawFixedRectangle(int x, int y, int w, int h, int color, int owner)
    {
        for(int i = Math.max(x, 0); i < Math.min(w + x, screenW); i++)
        {
            for(int j = Math.max(y, 0); j < Math.min(h + y, screenH); j++)
            {
                drawPixel(i, j, color, owner);
            }
        }
    }

    public void drawImage(Image image, int x, int y, int owner)
    {
        int newW = Math.max(0, Math.min(image.getW(), screenW - x - camera.offX * (1 - image.getFixed())));
        int newH = Math.max(0, Math.min(image.getH(), screenH - y - camera.offY * (1 - image.getFixed())));
        drawImagePixels(image, x, y, newW, newH, owner);
    }

    private void drawImagePixels(Image image, int x, int y, int newW, int newH, int owner)
    {
        for(int j = y; j < y + newH; j++)
        {
            for(int i = x; i < x + newW; i++)
            {
                drawPixel(i, j, image.getP()[(i - x) + (j - y) * image.getW()], owner);
            }
        }
    }

    public void drawText(Text text, int textOffsetX, int textOffsetY, int owner)
    {
        int symbolOffsetX = 0;
        int symbolOffsetY = 0;
        for(int i = 0; i < text.getText().length(); i++)
        {
            int unicodeId = text.getText().codePointAt(i) - UNICODE_OFFSET;
            if(text.getText().codePointAt(i) == '\n')
            {
                symbolOffsetX = 0;
                symbolOffsetY += font.getLetters()[text.getSize() / 2][0].getH();
                continue;
            }
            drawSymbol(unicodeId, text, symbolOffsetX + textOffsetX, symbolOffsetY + textOffsetY, owner);
            symbolOffsetX += font.getLetters()[text.getSize() / 2][unicodeId].getW();
        }
    }
    private void drawSymbol(int unicodeId, Text text, int offsetX, int offsetY, int owner)
    {
        for(int y = 0; y < font.getLetters()[text.getSize() / 2][unicodeId].getH(); y++)
        {
            for(int x = 0; x < font.getLetters()[text.getSize() / 2][unicodeId].getW(); x++)
            {
                if(isPixelInSymbol(unicodeId, text.getSize(), x, y))
                {
                    int absoluteX = x + offsetX + camera.offX * (1 - text.getFixed());
                    int absoluteY = y + offsetY + camera.offY * (1 - text.getFixed());
                    drawPixel(absoluteX, absoluteY, text.getColor(), owner);
                }
            }
        }
    }
    private boolean isPixelInSymbol(int unicodeId, int size, int x, int y)
    {
        Image letter = font.getLetters()[size / 2][unicodeId];
        return letter.getP()[x + y * letter.getW()] == 0xFF000000;
    }
}
