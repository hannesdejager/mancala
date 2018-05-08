package com.cloudinvoke.mancala.dto;


/**
 * There are only 2 players, the north player and the south player.
 *
 * @author Hannes de Jager
 * @since 2018-05-08
 */
public enum PlayerId {

    NORTH_PLAYER(0, "North Player"),
    SOUTH_PLAYER(1, "South Player");

    private int intValue;
    private String defaultName;

    private PlayerId(int number, String defaultName) {
        this.intValue = number;
        this.defaultName = defaultName;
    }

    public int getIntValue() {
        return intValue;
    }

    public String getDefaultName() {
        return defaultName;
    }

    public PlayerId otherPlayer() {
        return valueOf((this.intValue + 1) % 2);
    }

    public static PlayerId valueOf(int intValue) {
        if (intValue == PlayerId.NORTH_PLAYER.intValue)
            return NORTH_PLAYER;
        if (intValue == PlayerId.SOUTH_PLAYER.intValue)
            return SOUTH_PLAYER;
        throw new IllegalArgumentException(String.format("Invalid player number %d", intValue));
    }
}
