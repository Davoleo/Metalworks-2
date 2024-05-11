package net.davoleo.mettle.api.metal;

public enum ComponentType {
    BLOCK,
    INGOT,
    NUGGET,
    ORE,
    ROD,
    PLATE,
    GEAR,
    ;

    private final long flag;

    ComponentType() {
        flag = 1 << this.ordinal();
    }

    public long getFlag() {
        return flag;
    }
}
