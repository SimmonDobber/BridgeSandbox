package main.game.contractChoosePanel;

import lombok.Getter;
import main.engine.ProgramContainer;
import main.engine.display.Window;
import main.engine.structures.Scene;
import main.engine.structures.drawable.Rectangle;
import main.engine.structures.gameObject.Dimensions;
import main.engine.structures.gameObject.GameObject;
import main.engine.structures.gameObject.Position;
import main.engine.structures.observer.Observer;
import main.game.table.Table;

import static main.game.GameConstants.*;

public class ContractChoosePanel extends GameObject implements Scene
{
    private ContractChooseButton[][] buttons;
    public ContractChoosePanel(Position pos)
    {
        super(pos, new Dimensions(Window.WIDTH, Window.HEIGHT), null);
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
        Position buttonPosition = new Position(i * ContractChooseButton.DEFAULT_WIDTH + pos.getX() + 2,
                                                  j * ContractChooseButton.DEFAULT_HEIGHT + pos.getY() + 2);
        buttons[i][j] = new ContractChooseButton(buttonPosition,this, (i + j * (COLOR_COUNT + 1)));
        children.add(buttons[i][j]);
        buttons[i][j].attach(((Table)(ProgramContainer.getProgramContainer().getTable())).getGameManager());
        buttons[i][j].attach(ProgramContainer.getProgramContainer());

    }

    private void initializeSpriteList()
    {
        spriteList.add(new Rectangle(new Position(), new Dimensions(379, 347), GREEN, BROWN, 1));
    }

    public String getName()
    {
        return "ContractChoosePanel";
    }
}
