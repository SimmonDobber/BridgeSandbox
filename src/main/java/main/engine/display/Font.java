package main.engine.display;

import lombok.Getter;

public class Font
{
    public static final int DEFAULT_WIDTH = 2410;
    public static final int DEFAULT_HEIGHT = 40;
    public static final int SIZE_COUNT = 50;
    public static final int CHARACTERS_COUNT = 97;
    public static final int LETTER_BEGINNING_COLOR = 0xFF0000FF;
    public static final int LETTER_END_COLOR = 0xFFFFFF00;
    @Getter
    private Image fontImage;
    @Getter
    private Image[][] letters;
    @Getter
    private int[] offsets;
    @Getter
    private int[] widths;

    public Font(String path)
    {
        fontImage = new Image(path, DEFAULT_WIDTH, DEFAULT_HEIGHT, 1);
        letters = new Image[SIZE_COUNT][CHARACTERS_COUNT];
        offsets = new int[CHARACTERS_COUNT];
        widths = new int[CHARACTERS_COUNT];
        loadFontImages();
    }

    private void loadFontImages()
    {
        int unicodeId = 0;
        for(int i = 0; i < fontImage.getW(); i++)
        {
            setLetterOffset(i, unicodeId);
            if(fontImage.getP()[i] == LETTER_END_COLOR)
            {
               loadLetters(i, unicodeId);
               unicodeId++;
            }
        }
    }

    private void loadLetters(int currentPixel, int unicodeId)
    {
        setLetterWidth(currentPixel, unicodeId);
        for(int size = 0; size < SIZE_COUNT; size++)
        {
            loadLetter(size, currentPixel, unicodeId, fontImage.getW(), fontImage.getH());
        }
    }

    private void loadLetter(int size, int currentPixel, int unicodeId, int imageWidth, int imageHeight)
    {
        letters[size][unicodeId] = new Image(widths[unicodeId], imageHeight, 1);
        insertLetterIntoImage(size, unicodeId, offsets[unicodeId], widths[unicodeId], imageWidth, imageHeight);
        rescaleLetter(size, unicodeId);
    }

    private void setLetterOffset(int i, int unicode)
    {
        if(fontImage.getP()[i] == LETTER_BEGINNING_COLOR)
            offsets[unicode] = i;
    }

    private void setLetterWidth(int currentPixel, int unicode)
    {
        widths[unicode] = currentPixel - offsets[unicode] + 1;
    }

    private void insertLetterIntoImage(int size, int unicodeId, int letterOffset, int letterWidth, int imageWidth, int imageHeight)
    {
        for(int j = letterOffset; j <= letterOffset + letterWidth - 1; j++)
        {
            for(int k = 0; k < imageHeight; k++)
            {
                letters[size][unicodeId].getP()[j - letterOffset + k * letterWidth] = fontImage.getP()[j + k * imageWidth];
            }
        }
    }

    private void rescaleLetter(int size, int unicodeId)
    {
        letters[size][unicodeId].rescale((int)((double)letters[size][unicodeId].getW() * ((double)(size * 2) / (double)letters[size][unicodeId].getH())), size * 2);
    }
}
