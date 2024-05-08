package net.davoleo.mettle.api.metal.attribute;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;

public enum MetalAttribute {
    //Generic Stats
    DURABILITY(null),
    ENCHANTABILITY(null),
    //Tool Stats
    HARVEST_LEVEL(null),
    EFFICIENCY(null),
    ATTACK_DAMAGE(Attributes.ATTACK_DAMAGE),
    ATTACK_SPEED(Attributes.ATTACK_SPEED),
    REACH_DISTANCE(null), //SEE ForgeMod.REACH_DISTANCE
    //Armor Stats
    DAMAGE_REDUCTION(Attributes.ARMOR),
    TOUGHNESS(Attributes.ARMOR_TOUGHNESS),
    KNOCKBACK_RESISTANCE(Attributes.KNOCKBACK_RESISTANCE),
    MAX_HEALTH(Attributes.MAX_HEALTH),
    MOVEMENT_SPEED(Attributes.MOVEMENT_SPEED),
    ;

    private final Attribute attribute;

    MetalAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public Attribute getVanillaAttribute() {
        return attribute;
    }
}
