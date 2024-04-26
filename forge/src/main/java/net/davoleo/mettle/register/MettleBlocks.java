package net.davoleo.mettle.register;

import com.google.common.collect.Lists;
import com.tterrag.registrate.providers.loot.RegistrateBlockLootTables;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.davoleo.mettle.Mettle;
import net.davoleo.mettle.block.MettleOreBlock;
import net.davoleo.mettle.util.DataGenConstants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.LinkedList;

import static net.davoleo.mettle.Mettle.REGISTRATE;

public class MettleBlocks {

    public static final DeferredRegister<Block> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, Mettle.MODID);
    public static LinkedList<BlockEntry<? extends Block>> blocks = Lists.newLinkedList();

    public static void init() {
        MettleBlocks.REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
        Ores.init();
    }

    public static final class Ores {

        public static final com.tterrag.registrate.util.entry.BlockEntry<MettleOreBlock> TIN_ORE
                 = REGISTRATE.get().object("tin_ore")
                .block(p -> new MettleOreBlock(BlockBehaviour.Properties.of(Material.STONE), CoreMetals.TIN))
                .loot(Ores::lootTable)
                .simpleItem()
                .register();

        public static final com.tterrag.registrate.util.entry.BlockEntry<MettleOreBlock> ALUMINIUM_ORE
                = REGISTRATE.get().object("aluminium_ore")
                .block(p -> new MettleOreBlock(BlockBehaviour.Properties.of(Material.STONE), CoreMetals.ALUMINIUM))
                .loot(Ores::lootTable)
                .simpleItem()
                .register();

        public static void init() {}

        private static void lootTable(RegistrateBlockLootTables registrateBlockLootTables, MettleOreBlock oreBlock)
        {
            String oreName = oreBlock.getRegistryName().getPath();
            ResourceLocation metalId = new ResourceLocation(Mettle.MODID, oreName.substring(0, oreName.lastIndexOf('_')));

            registrateBlockLootTables
                    .add(oreBlock, LootTable.lootTable()
                            .withPool(
                                    LootPool.lootPool()
                                            .setRolls(ConstantValue.exactly(1F))
                                            .add(LootItem.lootTableItem(oreBlock.asItem())
                                                    .when(DataGenConstants.SILK_TOUCH_CONDITION)
                                                    .otherwise(LootItem.lootTableItem(MettleItems.ORE_CHUNKS.get(metalId).get())
                                                            .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))
                                                            .apply(ApplyExplosionDecay.explosionDecay())
                                                    )
                                            )
                            )
                            .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))
                    );
        }
    }

}
