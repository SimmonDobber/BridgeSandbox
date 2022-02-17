package main.game;

public enum CardFigure
{
    _2('2'),
    _3('3'),
    _4('4'),
    _5('5'),
    _6('6'),
    _7('7'),
    _8('8'),
    _9('9'),
    _10(':'),
    J('J'),
    Q('Q'),
    K('K'),
    A('A');

    private final char ascii;

    private CardFigure(char ascii)
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
