package main.engine;

import lombok.Getter;
import main.engine.structures.Scene;
import main.game.contractChoosePanel.ContractChoosePanel;
import main.game.table.Table;

public class ProgramContainer
{
    private static ProgramContainer PROGRAM_CONTAINER = null;

    public static ProgramContainer getProgramContainer()
    {
        if(PROGRAM_CONTAINER == null)
            PROGRAM_CONTAINER = new ProgramContainer();
        return PROGRAM_CONTAINER;
    }

    private MainLoop mainLoop;
    @Getter
    private Scene table;
    @Getter
    private Scene contractChoosePanel;
    private ProgramContainer()
    {
        mainLoop = MainLoop.getMainLoop();
    }
    public void initializeScenes()
    {
        table = new Table();
        contractChoosePanel = new ContractChoosePanel();
        switchSceneToTable();
    }
    public void switchSceneToTable()
    {
        mainLoop.setCurrentScene(table);
    }
    public void switchSceneToContractChoosePanel()
    {
        mainLoop.setCurrentScene(contractChoosePanel);
    }
}
