package main.engine.structures;

import lombok.Getter;
import lombok.Setter;
import main.engine.display.Image;
import main.engine.display.Renderer;

public class Entity
{
    @Getter
    @Setter
    protected int x;
    @Getter
    @Setter
    protected int y;
    @Getter
    protected int w;
    @Getter
    protected int h;
    @Getter
    @Setter
    protected Image img;
    public Entity(String path, int x, int y, int w, int h, int fixed)
    {
        this.img = new Image(path, w, h, fixed);
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        img.rescale(w, h);
    }
    public Entity(Image image, int x, int y, int w, int h, int fixed)
    {
        this.img = image;
        image.setFixed(fixed);
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        img.rescale(w, h);
    }
    public Entity(Image image, int x, int y, int fixed)
    {
        this.img = image;
        image.setFixed(fixed);
        this.x = x;
        this.y = y;
        this.w = image.getW();
        this.h = image.getH();
    }
    public Entity(int x, int y, int w, int h, int fixed)
    {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        img = new Image(w, h, fixed);
    }
    public void render(Renderer r)
    {
        r.drawImage(img, x, y, img.getFixed());
    }

    public void setW(int w) {
        this.w = w;
        if(img != null)
            img.rescale(w, h);
    }

    public void setH(int h) {
        this.h = h;
        if(img != null)
            img.rescale(w, h);
    }
}
