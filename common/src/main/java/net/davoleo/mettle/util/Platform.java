package net.davoleo.mettle.util;

public enum Platform {
    FORGE,
    FABRIC,
    ;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
