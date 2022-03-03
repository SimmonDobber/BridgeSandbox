package main.game.table;

import lombok.Getter;
import lombok.Setter;
import main.engine.structures.IntPair;

public class GameManager
{
    @Getter
    private static int cardAmount;
    @Setter
    private int contractId;
    @Setter
    private PlayerSide currentPlayer;
    private PlayerSide lastWinner;
    private IntPair taken;
    private Hand[] hand;
    private TableCard[] chosenTableCards;

}
