package main.game;

public enum PlayerSide {
    N,E,S,W;

    public PlayerSide nextPlayer(PlayerSide curent) {
        switch (curent) {
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
