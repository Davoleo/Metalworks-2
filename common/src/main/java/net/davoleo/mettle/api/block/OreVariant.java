package net.davoleo.mettle.api.block;

import net.davoleo.mettle.api.tool.ToolType;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

public enum OreVariant {
    STONE("stone", OreFeatures.STONE_ORE_REPLACEABLES),
    DEEPSLATE("deepslate", OreFeatures.DEEPSLATE_ORE_REPLACEABLES),
    NETHERRACK("netherrack", OreFeatures.NETHER_ORE_REPLACEABLES),
    END_STONE("end_stone", new BlockMatchTest(Blocks.END_STONE)),
    ;

    private final String name;
    private final ResourceLocation textureLocation;
    private final ToolType miningTool;
    private final RuleTest replaceRule;

    OreVariant(String name, ResourceLocation textureLocation, ToolType tool, RuleTest replaceRule) {
        this.name = name;
        this.textureLocation = textureLocation;
        this.miningTool = tool;
        this.replaceRule = replaceRule;
    }

    OreVariant(String name, ToolType tool, RuleTest replaceRule) {
        this(name, new ResourceLocation("minecraft", "block/" + name), tool, replaceRule);
    }

    OreVariant(String name, RuleTest replaceRule) {
        this(name, ToolType.PICKAXE, replaceRule);
    }

    @Override
    public String toString() {
        return this.name.toLowerCase();
    }

    public ToolType getTool() {
        return miningTool;
    }

    public ResourceLocation getTextureLocation() {
        return textureLocation;
    }

    public RuleTest getTargetReplaceRuleTest() {
        return replaceRule;
    }
}
