package main.engine.display.font;

import lombok.Getter;
import main.engine.structures.drawable.Image;
import main.engine.structures.gameObject.Dimensions;
import main.engine.structures.gameObject.Position;

public class FontImage extends Image {
    public static final int SYMBOL_BEGINNING_MARK_COLOR = 0xFF0000FF;
    public static final int SYMBOL_END_MARK_COLOR = 0xFFFFFF00;
    @Getter private int currentPixelLocationPointer;
    private final int symbolAmount;

    public FontImage(String path, Dimensions dim, int symbolAmount) {
        super(path, new Position(), dim, 1);
        this.symbolAmount = symbolAmount;
        this.currentPixelLocationPointer = 0;
    }

    public Image[] getSymbolsImageArray() {
        Image[] symbols = new Image[symbolAmount];
        for (int i = 0; i < symbolAmount; i++) {
            symbols[i] = getSymbolImage();
            movePixelLocationPointer();
        }
        return symbols;
    }

    private Image getSymbolImage() {
        int symbolOffset = currentPixelLocationPointer;
        movePixelLocationPointer();
        return extractSymbolImage(symbolOffset, getCurrentSymbolWidth(symbolOffset));
    }

    private Image extractSymbolImage(int symbolOffset, int symbolWidth) {
        Image symbol = new Image(new Position(), new Dimensions(symbolWidth, dim.h), 1);
        for(int x = 0; x < symbolWidth; x++) {
            for(int y = 0; y < dim.h; y++) {
                symbol.setP(Position.getPositionId(x, y, symbolWidth), extractSymbolPixelValue(new Position(x, y), symbolOffset));
            }
        }
        return symbol;
    }

    private int extractSymbolPixelValue(Position pos, int symbolOffset) {
        return p[pos.getPositionId(dim.w) + symbolOffset];
    }

    private int getCurrentSymbolWidth(int symbolOffset) {
        return currentPixelLocationPointer - symbolOffset - 1;
    }

    private void movePixelLocationPointer() {
        do {
            currentPixelLocationPointer++;
        } while(!isCurrentPixelSpecial() && currentPixelLocationPointer < dim.w);
    }

    private boolean isCurrentPixelSpecial() {
        return p[currentPixelLocationPointer] == SYMBOL_BEGINNING_MARK_COLOR ||
                p[currentPixelLocationPointer] == SYMBOL_END_MARK_COLOR;
    }
}
