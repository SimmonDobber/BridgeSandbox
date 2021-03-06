package main.game.table;

public enum PlayerSide
{
    N('N'),
    E('E'),
    S('S'),
    W('W');

    private final char ascii;

    PlayerSide(char ascii)
    {
        this.ascii = ascii;
    }

    public String getAsciiString()
    {
        return Character.toString(ascii);
    }

    public PlayerSide nextPlayer() {
        switch (this) {
            case N -> {
                return E;
            }
            case E -> {
                return S;
            }
            case S -> {
                return W;
            }
            case W -> {
                return N;
            }
        }
        return N;
    }
}
