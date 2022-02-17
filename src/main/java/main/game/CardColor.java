package main.game;

public enum CardColor
{
    CLUB('['),
    DIAMOND('\\'),
    HEART(']'),
    SPADE('^'),
    NO_ATU('_');

    private final char ascii;

    private CardColor(char ascii)
    {
        this.ascii = ascii;
    }

    public char getAscii()
    {
        return ascii;
    }

    public String getAsciiString()
    {
        return Character.toString(ascii);
    }
}
