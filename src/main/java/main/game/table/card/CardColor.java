package main.game.table.card;

import lombok.Getter;

import static main.game.GameConstants.BLACK;
import static main.game.GameConstants.RED;

@Getter
public enum CardColor
{
    CLUB('[', BLACK),
    DIAMOND('\\', RED),
    HEART(']', RED),
    SPADE('^', BLACK),
    NO_ATU('_', BLACK);

    private final char ascii;
    private final int cardColor;

    CardColor(char ascii, int cardColor)
    {
        this.ascii = ascii;
        this.cardColor = cardColor;
    }

    public String getAsciiString()
    {
        return Character.toString(ascii);
    }
}
