package net.davoleo.mettle.api.metal;

import org.jetbrains.annotations.Nullable;

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

    @Override
    public String toString() {
        return name().toLowerCase();
    }

    @Nullable
    public static ComponentType fromString(String name) {
        for (ComponentType type : ComponentType.values()) {
            if (name.toUpperCase().equals(type.name())) {
                return type;
            }
        }
        return null;
    }
}
