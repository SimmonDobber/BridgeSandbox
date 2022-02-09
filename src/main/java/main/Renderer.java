package main;

import java.awt.image.DataBufferInt;

public class Renderer
{
    private int screenW;
    private int screenH;
    private Camera camera;
    private Font font;
    private int[] p;
    private int[] pOwner;
    public Renderer(Window window)
    {
        screenW = (int)(window.getWidth() * Window.SCALE);
        screenH = (int)(window.getHeight() * Window.SCALE);
        camera = window.getCamera();
        font = new Font("/sansBoldplus.png", 2410, 40);
        p = ((DataBufferInt)window.getImage().getRaster().getDataBuffer()).getData();
        pOwner = new int[window.getWidth() * window.getHeight()];
        Button.setP(pOwner);
    }
    public void clear()
    {
        for(int i = 0; i < p.length; i++)
        {
            p[i] = 0xFF000000;
        }
    }
    private int alphaCompose(int color2, int color1)
    {
        double alpha1 = (color1 >>> 24) / 255f;
        double alpha2 = (color2 >>> 24) / 255f;
        double red1 = ((color1 << 8) >>> 24) / 255f;
        double red2 = ((color2 << 8) >>> 24) / 255f;
        double green1 = ((color1 << 16) >>> 24) / 255f;
        double green2 = ((color2 << 16) >>> 24) / 255f;
        double blue1 = ((color1 << 24) >>> 24) / 255f;
        double blue2 = ((color2 << 24) >>> 24) / 255f;
        double alpha0 = alpha1 + alpha2 - alpha1 * alpha2;
        int red0 = (int)(((red1 * alpha1 + (red2 * alpha2) * (1 - alpha1)) / alpha0) * 255);
        int green0 = (int)(((green1 * alpha1 + (green2 * alpha2) * (1 - alpha1)) / alpha0) * 255);
        int blue0 = (int)(((blue1 * alpha1 + (blue2 * alpha2) * (1 - alpha1)) / alpha0) * 255);
        int color0 = (int)((alpha0) * 255);
        color0 <<= 8;
        color0 += red0;
        color0 <<= 8;
        color0 += green0;
        color0 <<= 8;
        color0 += blue0;
        return color0;
    }
    private void drawPixel(int x,int y, int value, int fixed, int owner)
    {
        if((x < 0 || x >= screenW || y < 0 || y >= screenH) || value == 0xFFFF00FF)
            return;
        if((p[x + y * screenW] != 0xFF000000) && (value != 0xFF000000))
        {
            p[x + y * screenW] = alphaCompose(p[x + y * screenW], value);
        }
        else
        {
            p[x + y * screenW] = value;
        }
        pOwner[x + y * screenW] = owner;
    }
    public void drawRectangle(int x, int y, int w, int h, int value, int fixed)
    {
        drawRectangle(x, y, w, h, value, fixed, -1);
    }
    public void drawRectangle(int x, int y, int w, int h, int value, int fixed, int owner)
    {
        for(int i = Math.max(x, 0); i < Math.min(w + x, screenW); i++)
        {
            for(int j = Math.max(y, 0); j < Math.min(h + y, screenH); j++)
            {
                drawPixel(i + camera.offX * (1 - fixed), j + camera.offY * (1 - fixed), value, fixed, owner);
            }
        }
    }
    public void drawImage(Image image, int x, int y, int fixed)
    {
        drawImage(image, x, y, fixed, -1);
    }
    public void drawImage(Image image, int x, int y, int fixed, int owner)
    {
        int newW = Math.max(0, Math.min(image.getW(), screenW - x - camera.offX * (1 - fixed)));
        int newH = Math.max(0, Math.min(image.getH(), screenH - y - camera.offY * (1 - fixed)));
        for(int j = y; j < y + newH; j++)
        {
            for(int i = x; i < x + newW; i++)
            {
                drawPixel(i, j, image.getP()[(i - x) + (j - y) * image.getW()], fixed, owner);
            }
        }
    }
    public void drawText(String text, int offX, int offY, int color, int size, int fixed)
    {
        drawText(text, offX, offY, color, size, fixed, -1);
    }
    public void drawText(String text, int offX, int offY, int color, int size, int fixed, int owner)
    {
        int offsetX = 0;
        int offsetY = 0;
        for(int i = 0; i < text.length(); i++)
        {
            if(text.charAt(i) == '\n')
            {
                offsetX = 0;
                offsetY += font.getLetters()[size / 2][0].getH();
                continue;
            }
            int unicode = text.codePointAt(i) - 32;
            for(int y = 0; y < font.getLetters()[size / 2][unicode].getH(); y++)
            {
                for(int x = 0; x < font.getLetters()[size / 2][unicode].getW(); x++)
                {
                    if(font.getLetters()[size / 2][unicode].getP()[x + y * font.getLetters()[size / 2][unicode].getW()] == 0xFF000000)
                    {
                        drawPixel(x + offX + offsetX + camera.offX * (1 - fixed), y + offY + offsetY + + camera.offY * (1 - fixed), color, fixed, owner);
                    }
                }
            }
            offsetX += font.getLetters()[size / 2][unicode].getW();
        }
    }
}
