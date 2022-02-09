package main.engine.display;

public class Font
{
    private Image fontImage;
    private Image[][] letters;
    private int[] offsets;
    private int[] widths;

    public Font(String path, int width, int height)
    {
        fontImage = new Image(path, width, height, 1);
        letters = new Image[50][97];
        offsets = new int[97];
        widths = new int[97];

        int unicode = 0;

        for(int i = 0; i < fontImage.getW(); i++)
        {
            if(fontImage.getP()[i] == 0xFF0000FF)
            {
                offsets[unicode] = i;
            }
            if(fontImage.getP()[i] == 0xFFFFFF00)
            {
                widths[unicode] = i - offsets[unicode] + 1;
                for(int l = 0; l < 50; l++)
                {
                    letters[l][unicode] = new Image(widths[unicode], height, 1);
                    for(int j = offsets[unicode]; j <= i; j++)
                    {
                        for(int k = 0; k < height; k++)
                        {
                            letters[l][unicode].getP()[j - offsets[unicode] + k * widths[unicode]] = fontImage.getP()[j + k * width];
                        }
                    }
                    letters[l][unicode].rescale((int)((double)letters[l][unicode].getW() * ((double)(l * 2) / (double)letters[l][unicode].getH())), l * 2);
                }
                unicode++;
            }
        }
    }

    public Image getFontImage() {
        return fontImage;
    }

    public int[] getOffsets() {
        return offsets;
    }

    public int[] getWidths() {
        return widths;
    }

    public Image[][] getLetters() {
        return letters;
    }
}
