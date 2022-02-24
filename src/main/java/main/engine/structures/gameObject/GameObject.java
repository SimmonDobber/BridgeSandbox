package main.engine.structures.gameObject;

import lombok.Getter;
import lombok.Setter;
import main.engine.MainLoop;
import main.engine.display.renderer.Renderer;
import main.engine.structures.Scene;
import main.engine.structures.drawable.Drawable;
import main.engine.structures.features.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public abstract class GameObject extends Entity implements Serializable
{
    @Setter
    protected List<Drawable> spriteList;
    @Setter
    protected GameObject parent;
    protected List<GameObject> children;
    protected int id;

    protected GameObject(Position pos, Dimensions dim, GameObject parent)
    {
        super(pos, dim);
        initializeGameObject(parent);
    }

    private void initializeGameObject(GameObject parent)
    {
        this.id = giveId();
        this.parent = parent;
        this.spriteList = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    public void update(double dt)
    {
        updateChildren(dt);
    }

    public void updateChildren(double dt)
    {
        for (int i = 0; i < children.size(); i++)
            children.get(i).update(dt);
    }

    public void render(Renderer r)
    {
        spriteRender(r);
        childrenRender(r);
    }

    public void spriteRender(Renderer r)
    {
        for (int i = 0; i < spriteList.size(); i++)
            spriteList.get(i).render(r, pos, id);
    }

    public void childrenRender(Renderer r)
    {
        for(int i = 0; i < children.size(); i++)
            children.get(i).render(r);
    }

    public String getSceneName()
    {
        GameObject g = this;
        while (g.getParent() != null)
            g = g.getParent();
        return ((Scene)(g)).getName();
    }

    public boolean belongsToCurrentScene()
    {
        return Objects.equals(getSceneName(), MainLoop.getMainLoop().getCurrentSceneName());
    }
}
