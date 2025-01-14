package net.davoleo.mettle.api.metal;

import com.google.common.collect.Sets;
import net.davoleo.mettle.api.block.OreVariant;
import net.davoleo.mettle.api.metal.attribute.MetalModifier;
import net.minecraft.sounds.SoundEvent;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MetalBuilder {

    private String name;

    private int color = 0xCCCCCC;
    private int durability = 120;
    private int enchantability = 5;
    private int meltingTemperature = 100;

    @Nullable
    private ToolStatsBuilder toolStats = null;

    @Nullable
    private ArmorStatsBuilder armorStats = null;

    private final List<MetalModifier> modifiers = new ArrayList<>();

    private final MetalComponentFlags components = new MetalComponentFlags();

    private Set<OreVariant> oreVariants = Set.of(OreVariant.STONE);

    public MetalBuilder(String name) {
        this.name = name;
    }

    public IMetal build() {
        ToolStats tool = toolStats != null ? toolStats.build() : null;
        ArmorStats armor = armorStats != null ? armorStats.build() : null;
        return new SimpleMetal(name, color, durability, enchantability, meltingTemperature, tool, armor, modifiers, components, oreVariants);
    }

    public MetalBuilder color(int color) {
        this.color = color;
        return this;
    }

    public MetalBuilder durability(int durability) {
        this.durability = durability;
        return this;
    }

    public MetalBuilder enchantability(int enchantability) {
        this.enchantability = enchantability;
        return this;
    }

    public MetalBuilder meltingTemperature(int meltingTemperature) {
        this.meltingTemperature = meltingTemperature;
        return this;
    }

    public MetalBuilder addModifier(MetalModifier modifier) {
        for (MetalModifier m : modifiers) {
            if (m.attribute() == modifier.attribute()) {
                throw new IllegalArgumentException("Duplicate modifier attribute: " + modifier.attribute());
            }
        }

        modifiers.add(modifier);
        return this;
    }

    public MetalBuilder toolStats(SmolConsumer<ToolStatsBuilder> consumer) {
        this.toolStats = new ToolStatsBuilder();
        consumer.accept(toolStats);
        return this;
    }

    public MetalBuilder armorStats(SmolConsumer<ArmorStatsBuilder> consumer) {
        this.armorStats = new ArmorStatsBuilder();
        consumer.accept(armorStats);
        return this;
    }

    public MetalBuilder component(ComponentType type) {
        this.components.set(type);
        return this;
    }

    public MetalBuilder oreVariants(OreVariant... variants) {
        oreVariants = Sets.immutableEnumSet(variants[0], variants);
        return this;
    }

    public MetalBuilder components(ComponentType... types) {
        for (ComponentType type : types)
            component(type);
        return this;
    }

    public class ToolStatsBuilder {
        //Required
        private int harvestLevel = -1;
        private float efficiency = -1;
        private float damage = -1;

        //Optional
        private double attackSpeed;
        private double reachDistance;

        protected ToolStats build() {
            if (harvestLevel == -1 || efficiency == -1 || damage == -1) {
                throw new IllegalStateException(String.format("%s metal build failed: Invalid Tool Stats!", name));
            }
            return new ToolStats(harvestLevel, efficiency, damage, attackSpeed, reachDistance);
        }

        public ToolStatsBuilder harvestLevel(int harvestLevel) {
            this.harvestLevel = harvestLevel;
            return this;
        }

        public ToolStatsBuilder efficiency(float efficiency) {
            this.efficiency = efficiency;
            return this;
        }

        public ToolStatsBuilder damage(float damage) {
            this.damage = damage;
            return this;
        }

        public ToolStatsBuilder attackSpeed(double attackSpeed) {
            this.attackSpeed = attackSpeed;
            return this;
        }

        public ToolStatsBuilder reachDistance(double reachDistance) {
            this.reachDistance = reachDistance;
            return this;
        }
    }

    public class ArmorStatsBuilder {
        //Required
        private int[] damageReduction = null;

        //Optional
        private float toughness = 0;
        private float knockbackResistance = 0;
        @Nullable
        private SoundEvent wearSound = null;
        //Attribute Modifiers
        private double maxHealth;
        private double movementSpeed;

        protected ArmorStats build() {
            if (damageReduction == null) {
                throw new IllegalStateException(String.format("%s metal build failed: Invalid Armor Stats!", name));
            }
            return new ArmorStats(damageReduction, wearSound, toughness, knockbackResistance, maxHealth, movementSpeed);
        }

        public ArmorStatsBuilder damageReduction(int helmet, int chestplate, int leggings, int boots) {
            this.damageReduction = new int[]{helmet, chestplate, leggings, boots};
            return this;
        }

        public ArmorStatsBuilder toughness(float toughness) {
            this.toughness = toughness;
            return this;
        }

        public ArmorStatsBuilder knockbackResistance(float knockbackResistance) {
            this.knockbackResistance = knockbackResistance;
            return this;
        }

        public ArmorStatsBuilder wearSound(SoundEvent wearSound) {
            this.wearSound = wearSound;
            return this;
        }

        public ArmorStatsBuilder maxHealth(double maxHealth) {
            this.maxHealth = maxHealth;
            return this;
        }

        public ArmorStatsBuilder movementSpeed(double movementSpeed) {
            this.movementSpeed = movementSpeed;
            return this;
        }
    }

    @FunctionalInterface
    public interface SmolConsumer<T> {
        void accept(T b);
    }
}
