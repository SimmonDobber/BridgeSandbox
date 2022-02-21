package main.game.contractChoosePanel;

import main.engine.ProgramContainer;
import main.engine.display.Window;
import main.engine.structures.Scene;
import main.engine.structures.drawable.Rectangle;
import main.engine.structures.gameObject.Dimensions;
import main.engine.structures.gameObject.GameObject;
import main.engine.structures.gameObject.Position;
import main.engine.structures.observer.Observer;

import static main.game.GameConstants.*;

public class ContractChoosePanel extends GameObject implements Scene
{
    private ContractChooseButton[][] buttons;
    public ContractChoosePanel()
    {
        super(new Position(), new Dimensions(Window.WIDTH, Window.HEIGHT), null);
        initializeSpriteList();
        initializeButtons();
    }

    private void initializeButtons()
    {
        buttons = new ContractChooseButton[COLOR_COUNT + 1][];
        for(int i = 0; i < COLOR_COUNT + 1; i++)
        {
            buttons[i] = new ContractChooseButton[CONTRACT_VALUES];
            for(int j = 0; j < CONTRACT_VALUES; j++)
            {
               initializeButton(i, j);
            }
        }
    }

    private void initializeButton(int i, int j)
    {
        buttons[i][j] = new ContractChooseButton(new Position(i, j), this);
        children.add(buttons[i][j]);
        buttons[i][j].attach((Observer)(ProgramContainer.getProgramContainer().getTable()));
    }

    private void initializeSpriteList()
    {
        spriteList.add(new Rectangle(new Position(411, 166), new Dimensions(375, 343), GREEN, 1));
        spriteList.add(new Rectangle(new Position(411, 166), new Dimensions(375, 343), CYAN, BROWN, 1));
    }
}
