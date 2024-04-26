package net.davoleo.mettle.util;

import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;

public class DataGenConstants {

    public static final LootItemCondition.Builder SILK_TOUCH_CONDITION =
            MatchTool.toolMatches(
                    ItemPredicate.Builder.item()
                            .hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1)))
            );

}
