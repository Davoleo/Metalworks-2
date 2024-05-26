package net.davoleo.mettle.api.metal;

/**
 * Components Generation Flags
 */
public final class MetalComponentFlags {

    private long flags;

    public MetalComponentFlags(long flags) {
        this.flags = flags;
    }

    public MetalComponentFlags(ComponentType... flags) {
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
