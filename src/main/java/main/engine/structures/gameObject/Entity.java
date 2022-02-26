package main.engine.structures.gameObject;

import lombok.Getter;
import lombok.Setter;
import main.engine.structures.features.Measurable;

@Getter
@Setter
public abstract class Entity implements Measurable
{
    public Position pos;
    public Dimensions dim;

    public Entity(Position pos, Dimensions dim)
    {
        this.pos = pos;
        this.dim = dim;
    }
}
