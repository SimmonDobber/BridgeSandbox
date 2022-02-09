package main;

public class Card extends Button
{
    public static final int DEFAULT_WIDTH = 85;
    public static final int DEFAULT_HEIGHT = 120;
    private static final int DEFAULT_STATE_COUNT = 2;
    private static final int BORDER_THICKNESS = 2;
    private int figure;
    private int color;
    private int owner;

    public Card(Card card)
    {
        super(card.x, card.y, card.w, card.h, card.stateCount, 1);
        this.figure = card.figure;
        this.color = card.color;
        this.owner = card.owner;
    }

    public Card(int x, int y, int figure, int color, int owner) {
        super(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_STATE_COUNT, 1);
        this.figure = figure;
        this.color = color;
        this.owner = owner;
    }
    public Card(int x, int y, int w, int h, int stateCount, int figure, int color, int owner) {
        super(x, y, w, h, stateCount, 1);
        this.figure = figure;
        this.color = color;
        this.owner = owner;
    }

    public Card(int x, int y, int w, int h, int stateCount, int fixed) {
        super(x, y, w, h, stateCount, fixed);
    }

    @Override
    public void onClick(State table) {
        select(((Table)(table)));
        ((Table)(table)).nextTurn();
    }
    @Override
    public void onDoubleClick(State table) {

    }
    @Override
    public void onRelease(State table) {

    }
    @Override
    public void onHold(State table) {

    }
    @Override
    public void onHover(State table) {
            highlighted = true;
    }
    @Override
    public void nonHover(State table) {
        highlighted = false;
    }
    private void select(Table table)
    {
        x = Hand.OWNER_CENTER_X[owner];
        y = Hand.OWNER_CENTER_Y[owner];
        table.getChoosenCards()[owner] = new Card(this);
    }
    private void hoveredRender(Renderer r)
    {
        if(highlighted)
            r.drawRectangle(x, y, w, h, 0x220000FF, img.getFixed(), buttonId);
    }
    private void inactiveRender(Renderer r)
    {
        if(!active)
            r.drawRectangle(x, y, w, h, 0x77333333, img.getFixed(), buttonId);
    }
    public void render(Renderer r) {
        r.drawRectangle(x - BORDER_THICKNESS, y - BORDER_THICKNESS, w + 2 + BORDER_THICKNESS, h + 2 * BORDER_THICKNESS, getColorValue(), img.getFixed(), buttonId);
        r.drawRectangle(x, y, w, h, Table.SILVER, img.getFixed(), buttonId);
        r.drawText(Character.toString(Table.FIGURES[figure]), x + 3, y + 2, getColorValue(), Table.DEFAULT_FONT_SIZE, img.getFixed(), buttonId);
        r.drawText(Character.toString(Table.COLORS[color]), x - 1, y + Table.DEFAULT_FONT_SIZE - 3, ((color == 0 || color == 3) ? Table.BLACK : Table.RED), Table.DEFAULT_FONT_SIZE, img.getFixed(), buttonId);
        r.drawText(Character.toString(Table.FIGURES[figure]), x + w - Table.DEFAULT_FONT_SIZE / 2 - 8, y + h - Table.DEFAULT_FONT_SIZE, getColorValue(), Table.DEFAULT_FONT_SIZE, img.getFixed(), buttonId);
        r.drawText(Character.toString(Table.COLORS[color]), x + w - Table.DEFAULT_FONT_SIZE / 2 - 11, y + h - Table.DEFAULT_FONT_SIZE * 2 + 6, getColorValue(), Table.DEFAULT_FONT_SIZE, img.getFixed(), buttonId);
        hoveredRender(r);
        inactiveRender(r);

    }
    public int getColorValue()
    {
        return ((color == 0 || color == 3 || color == 4) ? Table.BLACK : Table.RED);
    }
    public static int getColorValue(int c)
    {
        return ((c == 0 || c == 3 || c == 4) ? Table.BLACK : Table.RED);
    }

    public int getFigure() {
        return figure;
    }

    public int getColor() {
        return color;
    }

    public int getOwner() {
        return owner;
    }
    public int getId()
    {
        return color * Table.FIGURE_COUNT + figure;
    }
}
