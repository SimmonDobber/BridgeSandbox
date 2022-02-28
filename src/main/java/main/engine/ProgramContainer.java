package main.engine;

import lombok.Getter;
import main.engine.structures.Scene;
import main.engine.structures.gameObject.Position;
import main.game.cardChoosePanel.CardChoosePanel;
import main.game.contractChoosePanel.ContractChoosePanel;
import main.game.table.Table;

public class ProgramContainer
{
    private static ProgramContainer PROGRAM_CONTAINER = null;
    private MainLoop mainLoop;
    @Getter
    private Scene table;
    @Getter
    private Scene contractChoosePanel;
    @Getter
    private Scene cardChoosePanel;
    private ProgramContainer() {
        mainLoop = MainLoop.getMainLoop();
    }

    public void initializeScenes() {
        table = new Table();
        contractChoosePanel = new ContractChoosePanel(new Position(409, 164));
        cardChoosePanel = new CardChoosePanel();
        switchSceneToTable();
    }

    public void switchSceneToTable() {
        mainLoop.setCurrentScene(table);
    }

    public void switchSceneToContractChoosePanel() {
        mainLoop.setCurrentScene(contractChoosePanel);
    }

    public void switchSceneToCardChoosePanel() {
        mainLoop.setCurrentScene(cardChoosePanel);
    }

    public static ProgramContainer getProgramContainer()
    {
        if(PROGRAM_CONTAINER == null)
            PROGRAM_CONTAINER = new ProgramContainer();
        return PROGRAM_CONTAINER;
    }
}
