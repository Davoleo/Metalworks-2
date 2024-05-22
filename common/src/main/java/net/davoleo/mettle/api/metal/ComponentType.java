package net.davoleo.mettle.api.metal;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.Nullable;

public enum ComponentType {
    @SerializedName("block")
    BLOCK,
    @SerializedName("ingot")
    INGOT,
    @SerializedName("nugget")
    NUGGET,
    @SerializedName("ore")
    ORE,
    @SerializedName("rod")
    ROD,
    @SerializedName("plate")
    PLATE,
    @SerializedName("gear")
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
