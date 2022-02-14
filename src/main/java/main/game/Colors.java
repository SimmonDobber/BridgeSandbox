package main.game;

public enum Colors {
    SPADE('['),
    DIAMOND('\\'),
    CLUB('t'),
    HEART('t'),
    NO_ATU('u');

    private final char ascii;

    private Colors(char ascii) {
        this.ascii = ascii;
    }

}
