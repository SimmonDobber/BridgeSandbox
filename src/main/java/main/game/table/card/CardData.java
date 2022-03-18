package main.game.table.card;

import static main.game.GameConstants.PLAYER_COUNT;

public class CardData
{
    public CardFigure figure;
    public CardColor color;

    public CardData(CardFigure figure, CardColor color){
        this.figure = figure;
        this.color = color;
    }

    public CardData(CardData cardData){
        this.figure = cardData.figure;
        this.color = cardData.color;
    }

    public CardData(int cardId){
        this.figure = CardFigure.values()[cardId / PLAYER_COUNT];
        this.color = CardColor.values()[cardId % PLAYER_COUNT];
    }

    public int getCardId(){
        int figureId = figure.ordinal();
        int colorId = color.ordinal();
        return figureId * PLAYER_COUNT + colorId;
    }

    public static int getCardId(CardFigure figure, CardColor color){
        int figureId = figure.ordinal();
        int colorId = color.ordinal();
        return figureId * PLAYER_COUNT + colorId;
    }

    public int getCardColor(){
        return color.getCardColor();
    }

    public String getFigureAsciiString(){
        return figure.getAsciiString();
    }

    public String getColorAsciiString(){
        return color.getAsciiString();
    }
}
