package net.davoleo.mettle.api.metal;

/**
 * If methods' implementation return null,
 * items and blocks won't be added by Mettle,
 * otherwise the mod's features will be used
 */
public final class MetalComponents {

    private long flags;

    public MetalComponents(long flags) {
        this.flags = flags;
    }

    public MetalComponents(ComponentType... flags) {
        for (ComponentType flag : flags) {
            this.set(flag);
        }
    }

    protected void set(ComponentType type) {
        flags |= type.getFlag();
    }

    public boolean get(ComponentType type) {
        return (type.getFlag() & flags) != 0;
    }
}
