package main.engine.display.font;

import lombok.Getter;
import main.engine.structures.drawable.Image;
import main.engine.structures.gameObject.Dimensions;

@Getter
public class Font
{
    private static final int SYMBOL_SIZE_VARIETY = 50;
    private static final int SYMBOL_COUNT = 97;
    private final FontImage fontImage;
    private final Image[][] symbols;

    public Font(String path, Dimensions dim)
    {
        fontImage = new FontImage(path, dim, SYMBOL_COUNT);
        symbols = new Image[SYMBOL_SIZE_VARIETY][SYMBOL_COUNT];
        loadAllSymbols();
    }

    private void loadAllSymbols()
    {
        Image[] originalSymbols = fontImage.getSymbolsImageArray();
        for(int i = 0; i < SYMBOL_SIZE_VARIETY; i++) {
            for(int j = 0; j < SYMBOL_COUNT; j++){
                symbols[i][j] = rescaleSymbol(originalSymbols[j], 2 * i);
            }
        }
    }

    private Image rescaleSymbol(Image source, int size)
    {
        Image symbol = new Image(source);
        symbol.rescale(getScaledImageDimensions(symbol.getDim(), getSymbolToOriginalRatio(size)));
        return symbol;
    }

    private double getSymbolToOriginalRatio(int size)
    {
        return size / (double)fontImage.getDim().getH();
    }

    private Dimensions getScaledImageDimensions(Dimensions dim, double ratio)
    {
        return new Dimensions((int)(dim.getW() * ratio), (int)(dim.getH() * ratio));
    }
}
