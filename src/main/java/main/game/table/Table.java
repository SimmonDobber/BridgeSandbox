package main.game.table;

import lombok.Getter;
import main.engine.display.Window;
import main.engine.structures.gameObject.Dimensions;
import main.engine.structures.gameObject.GameObject;
import main.engine.structures.Scene;
import main.engine.structures.drawable.Rectangle;
import main.engine.structures.gameObject.Position;
import main.game.table.buttons.*;
import main.game.table.solver.Solver;

import static main.game.GameConstants.*;

@Getter
public class Table extends GameObject implements Scene
{
    private GameManager gameManager;
    private Solver solver;
    private TableButtonManager buttonManager;
    private TableTextManager textManager;

    public Table() {
        super(new Position(), new Dimensions(Window.WIDTH, Window.HEIGHT), null);
        initializeSpriteList();
        initializeGameManager();
        initializeSolver();
        initializeButtonManager();
        initializeTextManager();
    }

    public String getCurrentPlayerAsciiString() {
        return gameManager.getCurrentPlayerAsciiString();
    }

    public int[] getTaken()
    {
        return gameManager.getTaken();
    }

    public int getContractId(){
        return gameManager.getContractId();
    }

    public void reloadTexts(){
        textManager.reloadTexts();
    }

    public void reloadButtons(){
        buttonManager.reloadButtons();
    }

    public String getName() {
        return "Table";
    }

    private void initializeSpriteList() {
        spriteList.add(new Rectangle(new Position(), dim, GREEN, 1));
        spriteList.add(new Rectangle(new Position(411, 166), new Dimensions(375, 343), CYAN, BROWN, 1));
    }

    private void initializeGameManager() {
        gameManager = new GameManager(this);
        children.add(gameManager);
    }

    private void initializeSolver() {
        solver = new Solver(this);
    }

    private void initializeButtonManager() {
        buttonManager = new TableButtonManager(this);
        children.add(buttonManager);
    }

    private void initializeTextManager() {
        textManager = new TableTextManager(this);
        children.add(textManager);
    }
}
