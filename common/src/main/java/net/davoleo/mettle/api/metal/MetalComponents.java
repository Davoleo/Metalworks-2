package net.davoleo.mettle.api.metal;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.davoleo.mettle.api.block.OreVariant;
import net.davoleo.mettle.api.registry.RegistryEntry;
import net.davoleo.mettle.block.MettleOreBlock;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Consumer;

public record MetalComponents(
        @Nullable RegistryEntry<Item> raw,
        ImmutableMap<OreVariant, RegistryEntry<MettleOreBlock>> ores
        ) {

    public void forEachBlock(Consumer<RegistryEntry<? extends Block>> blockConsumer) {
        this.ores.values().forEach(blockConsumer);
    }

    public void forEachItem(Consumer<RegistryEntry<? extends Item>> itemConsumer) {
        if (this.raw != null)
            itemConsumer.accept(this.raw);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private RegistryEntry<Item> raw;
        private final Map<OreVariant, RegistryEntry<MettleOreBlock>> ores = Maps.newEnumMap(OreVariant.class);

        private Builder() {
        }

        public Builder setRaw(RegistryEntry<Item> raw) {
            this.raw = raw;
            return this;
        }

        public Builder addOre(OreVariant variant, RegistryEntry<MettleOreBlock> ore) {
            this.ores.put(variant, ore);
            return this;
        }

        public MetalComponents build() {
            return new MetalComponents(raw, Maps.immutableEnumMap(ores));
        }
    }
}
