package net.davoleo.mettle.api.metal;

import net.minecraft.sounds.SoundEvent;

public record ArmorStats(
        int[] damageReduction,
        SoundEvent wearSound,
        float toughness,
        float knockbackResistance,

        double maxHealth,
        double movementSpeed
) {
}
