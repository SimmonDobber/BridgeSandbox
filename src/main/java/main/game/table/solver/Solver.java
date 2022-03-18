package main.game.table.solver;

import main.engine.structures.observer.Observable;
import main.engine.structures.observer.Observer;
import main.game.table.bestMovesTable.BestMovesTable;
import main.game.table.Table;


public class Solver implements Observer
{
    private final Table table;
    private BestMovesTable bestMovesTable;
    public Solver(Table table, BestMovesTable bestMovesTable) {
        this.table = table;
        this.bestMovesTable = bestMovesTable;
    }
    @Override
    public void update(Observable o, Object arg) {

    }

}
