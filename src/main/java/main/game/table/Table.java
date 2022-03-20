package main.game.table;

import lombok.Getter;
import main.engine.display.Window;
import main.engine.structures.gameObject.Dimensions;
import main.engine.structures.gameObject.GameObject;
import main.engine.structures.Scene;
import main.engine.structures.drawable.Rectangle;
import main.engine.structures.gameObject.Position;
import main.game.buttons.*;
import main.game.table.bestMovesTable.BestMovesTable;
import main.game.table.solver.Solver;

import static main.game.GameConstants.*;

@Getter
public class Table extends GameObject implements Scene
{
    private GameManager gameManager;
    private Solver solver;
    private TableButtonManager buttonManager;
    private TableTextManager textManager;
    private BestMovesTable bestMovesTable;

    public Table() {
        super(new Position(), new Dimensions(Window.WIDTH, Window.HEIGHT), null);
        initializeTextManager();
        initializeSpriteList();
        initializeBestMovesTable();
        initializeGameManager();
        initializeSolver();
        initializeButtonManager();
        startGame();
    }

    public void clearBestMovesTable(){
        bestMovesTable.clearCardSignatureFields();
    }

    public int getCardAmount(){
        return gameManager.getCardAmount();
    }

    public PlayerSide getCurrentPlayer(){
        return gameManager.getCurrentPlayer();
    }

    public PlayerSide getLastWinner(){
        return gameManager.getLastWinner();
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

    public String getName() {
        return "Table";
    }

    private void startGame(){
        textManager.loadTexts();
        gameManager.initializeGame();
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
        solver = new Solver(this, bestMovesTable);
    }

    private void initializeButtonManager() {
        buttonManager = new TableButtonManager(this);
        children.add(buttonManager);
    }

    private void initializeTextManager() {
        textManager = new TableTextManager(this);
        children.add(textManager);
    }

    private void initializeBestMovesTable() {
        bestMovesTable = new BestMovesTable(this);
        children.add(bestMovesTable);
    }
}
